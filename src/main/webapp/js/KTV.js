/**
 * Created by Administrator on 2014/5/23 0023.
 */
var _localhostPath = "";
var _totalDataCount = 0;
var _likeKTVName = null;
var _likeKTVAddress = null;
var _dataOffset = 0;
var _pageDataCount = 15;
var _pageCount = 0;
var _districtCode = null;
var _provinceCode = null;
var _cityCode = null;
var _areaCode = null;

$(document).ready(function(){
    var curWwwPath = window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    _localhostPath = curWwwPath.substring(0,pos);

    getDataCount();
});

/////////////////////////////////////////////////////-----init
var initPageData = function() {
    var ktvString  = "";
    var ktvId = 0;
    var url = _localhostPath + "/rest/ktv/info?offset=" + _dataOffset + "&length=" + _pageDataCount + "&name=" + _likeKTVName + "&address=" + _likeKTVAddress + "&districtId=" + _districtCode;

    $.ajax({
        url : url,
        type : "GET",
        dataType : "json",
        contentType : 'application/json;charset=utf-8',
        success : function(data) {
            $("#ktv").html("");
            $.each(data, function(ktvIndex, ktv) {
                var createTime = date(ktv.createTime);
                var updateTime = date(ktv.updateTime);
                ktvString += "<tr><td>" +ktv.name.substr(0,6) +  "</td><td>" +ktv.phoneNumber + "</td><td>" +ktv.address.substr(0,10) + "</td><td>" +ktv.score +
                    "</td><td>" +createTime + "</td><td>" +updateTime +
                    "</td><td><button type='button' class='btn btn-default btn-sm remove'rel='" + ktv.id + "'><span class='glyphicon glyphicon-remove'></span></button>" +
                    "<button type='button' class='btn btn-default btn-sm refresh'><span class='glyphicon glyphicon-refresh'></span></button>" +
                    "<button type='button' class='btn btn-default btn-sm picture'><span class='glyphicon glyphicon-picture'></span></button></td></tr>";
            });
            $("#ktv").append(ktvString);

            $(".remove").bind("click", function() {
                ktvId = $(this).attr("rel");
                removeKtv(ktvId);
            });

            $(".refresh").bind("click", function() {

            });

            $(".picture").bind("click", function() {

            })

            initDistrict();
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
};

var initDistrict = function() {
    var provinceString = "";

    $.ajax({
        url: _localhostPath + '/rest/district/province/info',
        type: 'GET',
        contentType:'application/json;charset=UTF-8',
        timeout: 1000,
        success: function(data){
            $(".province").html("");
            $.each(data, function(provinceIndex, province) {
                provinceString += "<li><a rel='" + province.provinceId + "' class='getCity'>" + province.provinceName + "</a></li>";
            });
            $(".province").append(provinceString);

            $(".getCity").on("click", function() {
                var provinceId = $(this).attr("rel");
                _districtCode = provinceId;
                _provinceCode = provinceId;
                $(".province").prev().text($(this).html()).append('&nbsp;<span class="caret"></span>');
                getCity(provinceId);
            });
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
};

var initPageCount = function() {
    var pageCount = '<li><a href="#" class="lastPage">&laquo;</a></li>';
    var startPage = 0;
    var endPage = 10;

    if(_pageCount < endPage) {
        for(var i = startPage; i < _pageCount; i ++) {
            if(i == 0) {
                pageCount += '<li><a rel="' + i + '" href="#" class="page visited">' + (i + 1) + '</a></li>';
            } else {
                pageCount += '<li><a rel="' + (i * _pageDataCount ) + '" href="#" class="page">' + (i + 1) + '</a></li>';
            }
        }
    } else if(_pageCount >= endPage) {
        for(var i = startPage; i < endPage; i ++) {
            if(i == 0) {
                pageCount += '<li><a rel="' + i + '" href="#" class="page visited">' + (i + 1) + '</a></li>';
            } else {
                pageCount += '<li><a rel="' + (i * _pageDataCount ) + '" href="#" class="page">' + (i + 1) + '</a></li>';
            }
        }
        pageCount += "<li><a>...</a></li>";
    }

    pageCount += '<li><a href="#" class="nextPage">&raquo;</a></li>';
    $("#pageCount").append(pageCount);

    $(".page").on("click", function(){
        _dataOffset = $(this).attr("rel");
        $(".page").removeClass("visited");
        $(this).addClass("visited");
        initPageData();
    })

    $(".lastPage").on("click", function(){
        if(_dataOffset > 0) {
            _dataOffset = parseInt(_dataOffset) - parseInt(_pageDataCount);
            var thisPage = $(".visited");
            thisPage.removeClass("visited");
            thisPage.parent("li").prev().children().addClass("visited");
        }

        initPageData();
    })

    $(".nextPage").on("click", function(){
        if(_dataOffset / _pageDataCount + 1 < _pageCount) {
            _dataOffset = parseInt(_dataOffset) + parseInt(_pageDataCount);
            var thisPage = $(".visited");
            thisPage.removeClass("visited");
            thisPage.parent("li").next().children().addClass("visited");
        }

        initPageData();
    })

    initPageData();
};

var getDataCount = function() {
    var url = _localhostPath + "/rest/ktv/count?name=" + _likeKTVName + "&address=" + _likeKTVAddress + "&districtId=" + _districtCode;

    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            _totalDataCount = data.dataCount;
            if(_totalDataCount % _pageDataCount == 0) {
                _pageCount = _totalDataCount / _pageDataCount;
            } else {
                _pageCount = Math.ceil(_totalDataCount / _pageDataCount);
            }

            initPageCount();
        }
    })
};

/////////////////////////////////////////////////////-----event
$("#addKtv").on("click", function(){
    var name = $("#name").val();
    var phoneNumber = $("#phoneNumber").val();
    var address = $("#address").val();
    var averagePrice = $("#averagePrice").val();
    var districtId = _provinceCode + "-" + _cityCode + "-" + _areaCode;
    var data = "{'name':'" + name +"','phoneNumber':'" + phoneNumber + "','address':'" + address + "','averagePrice':'" + averagePrice + "', 'districtId':'" + districtId + "'}";

    $.ajax({
        url: _localhostPath + '/rest/ktv/info',
        type: 'POST',
        data:data,
        contentType:'application/json;charset=UTF-8',
        timeout: 1000,
        success: function(data){
            window.location.reload();
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
});

$("#go").on("click", function() {
    $("#pageCount").html("");
    _likeKTVName = null;
    _likeKTVAddress = null;
    $("#searchInput").text("");
    getDataCount();
});

$("#ktvName").on("click", function(){
    var ktvName = $("#searchInput").val();

    if(ktvName != "") {
        $("#likeSearch").text("name").append('&nbsp;<span class="caret"></span>');
        _likeKTVName = ktvName;
        _likeKTVAddress = null;
        $("#pageCount").html("");
        getDataCount();
    }
});

$("#ktvAddress").on("click", function(){
    var ktvAddress =  $("#searchInput").val();

    if(ktvAddress != "") {
        $("#likeSearch").text("address").append('&nbsp;<span class="caret"></span>');
        _likeKTVAddress = ktvAddress;
        _likeKTVName = null;
        $("#pageCount").html("");
        getDataCount();
    }
});

/////////////////////////////////////////////////////-----function
var removeKtv = function(ktvId) {
    $.ajax({
        url: _localhostPath + '/rest/ktv/info/' + ktvId,
        type: 'DELETE',
        contentType:'application/json;charset=UTF-8',
        timeout: 1000,
        success: function(data){
            window.location.reload();
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
};

var getCity = function(privinceId) {
    var cityString = "";
    $.ajax({
        url: _localhostPath + '/rest/district/province/city/info/' + privinceId,
        type: 'GET',
        contentType:'application/json;charset=UTF-8',
        timeout: 1000,
        success: function(data){
            $(".city").html("");
            $.each(data, function(cityIndex, city) {
                cityString += "<li><a rel='" + city.cityId + "' class='getArea'>" + city.cityName + "</a></li>";
            });
            $(".city").append(cityString);

            $(".getArea").on("click", function() {
                var cityId = $(this).attr("rel");
                _districtCode = cityId;
                _cityCode = cityId;
                $(".city").prev().text($(this).html()).append('&nbsp;<span class="caret"></span>');
                getArea(cityId);
            });
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

var getArea = function(cityId) {
    var areaString = "";
    $.ajax({
        url: _localhostPath + '/rest/district/city/area/info/' + cityId,
        type: 'GET',
        contentType:'application/json;charset=UTF-8',
        timeout: 1000,
        success: function(data){
            $(".area").html("");
            $.each(data, function(areaIndex, area) {
                areaString += "<li><a rel='" + area.areaId + "' class='getArea'>" + area.areaName + "</a></li>";
            });
            $(".area").append(areaString);

            $(".getArea").on("click", function() {
                var areaId = $(this).attr("rel");
                _districtCode = areaId;
                _areaCode = areaId;
                $(".area").prev().text($(this).html()).append('&nbsp;<span class="caret"></span>');
            });
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
};