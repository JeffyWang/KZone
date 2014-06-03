var _localhostPath = "";
var _likeInformationTitle = null;
var _totalDataCount = 0;
var _pageDataCount = 30;
var _pageCount = 0;
var _dataOffset = 0;
var _scroll = true;

$(document).ready(function(){
    var curWwwPath = window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    _localhostPath = curWwwPath.substring(0,pos);


    getDataCount();
});

$(window).scroll( function() {
    if(_scroll)
        loadData();
});

var loadData = function() {
    var totalHeight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());     //浏览器的高度加上滚动条的高度
    if ($(document).height() <= totalHeight)     //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
    {
        //加载数据
        console.log("`````````````````");
//        $("#information").append('<div class="well well-lg">new data</div><div class="well well-lg">...</div><div class="well well-lg">...</div><div class="well well-lg">...</div><div class="well well-lg">asdfasdf</div><div class="well well-lg">...</div><div class="well well-lg">...</div><div class="well well-lg">...</div>');

        _dataOffset = _dataOffset + _pageDataCount;
        initPageData();
        if(_dataOffset > _totalDataCount) {
            $("#information").append('<div class="well well-lg">已加载完所有内容</div>');
            _scroll = false;
        }
    }
}

var getDataCount = function() {
    var url = _localhostPath + "/rest/information/count?title=" + _likeInformationTitle;

    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            _totalDataCount = data.dataCount;
            console.log(_totalDataCount)
            _pageCount = Math.ceil(_totalDataCount / _pageDataCount);
            console.log(_pageCount)

            initPageData();
        }
    })
}

var initPageData = function() {
    var informationString = "";
    var url = _localhostPath + "/rest/information/info?offset=" + _dataOffset + "&length=" + _pageDataCount + "&title=" + _likeInformationTitle;

    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            $.each(data, function(informationIndex, information) {
//                informationString += '<div class="well well-lg"><div>' + information.id + '</div><div rel="' + information.id + '">' + information.title + '</div></div>';
                informationString += '<div class="well well-lg" data-toggle="modal" data-target="#article"><div class="media"><a class="pull-left" href="#"><img class="media-object" src="../img/Hydrangeas.jpg" alt="..."></a><div class="media-body"><h4 class="media-heading">'+ information.title + '</h4>'+ information.article + '</div></div></div>';
            });
            $("#information").append(informationString);
        }
    })
}
