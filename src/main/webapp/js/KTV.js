/**
 * Created by Administrator on 2014/5/23 0023.
 */
var _localhostPath = "";
var _totalDataCount = 0;
var _likeKTVName = null;
var _likeKTVAddress = null;
var _pageIndex = 0;
var _pageDataCount = 5;
var _pageCount = 0;

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

    $.ajax({
        url : _localhostPath + "/rest/ktv/info/" + _pageIndex + "/" + _pageDataCount + "/" + _likeKTVName + "/" + _likeKTVAddress,
        type : "GET",
        dataType : "json",
        contentType : 'application/json;charset=utf-8',
        success : function(data) {
            $("#ktv").html("");
            $.each(data.data, function(ktvIndex, ktv) {
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
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

var getDataCount = function() {
    var pageCount = '<li><a href="#">&laquo;</a></li>';
    $.ajax({
        url: _localhostPath + "/rest/ktv/count/" + _likeKTVName + "/" + _likeKTVAddress,
        type: "GET",
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            _totalDataCount = data.data.dataCount;
            if(_totalDataCount % _pageDataCount == 0) {
                _pageCount = _totalDataCount / _pageDataCount;
            } else {
                _pageCount = Math.ceil(_totalDataCount / _pageDataCount);
            }

            initPageData();

            for(var i = 0; i < _pageCount; i ++) {
                if(i == 0) {
                    pageCount += '<li><a rel="' + i + '" href="#" class="page">' + (i + 1) + '</a></li>';
                } else {
                    pageCount += '<li><a rel="' + (i + 1 + _pageCount ) + '" href="#" class="page">' + (i + 1) + '</a></li>';
                }

            }
            pageCount += '<li><a href="#">&raquo;</a></li>';
            $("#pageCount").append(pageCount);

            $(".page").on("click", function(){
                _pageIndex = $(this).attr("rel");
                console.log(_pageIndex);
                initPageData();
            })
        }
    })
}

/////////////////////////////////////////////////////-----event
$("#addKtv").on("click", function(){
    var name = $("#name").val();
    var phoneNumber = $("#phoneNumber").val();
    var address = $("#address").val();
    var averagePrice = $("#averagePrice").val();
    var data = "{'name':'" + name +"','phoneNumber':'" + phoneNumber + "','address':'" + address + "','averagePrice':'" + averagePrice + "'}";

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
})

$("#ktvName").on("click", function(){
    $("#likeSearch").text("name").append('&nbsp;<span class="caret"></span>');
    _likeKTVName = $("#searchInput").val();
    _likeKTVAddress = null;
    getDataCount();
})

$("#ktvAddress").on("click", function(){
    $("#likeSearch").text("address").append('&nbsp;<span class="caret"></span>');
    _likeKTVAddress = $("#searchInput").val();
    _likeKTVName = null;
    getDataCount();
})

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
}