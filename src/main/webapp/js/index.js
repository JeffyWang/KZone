//if ( $.fn.makisu.enabled ) {
//    var $maki = $( '.maki' );
//
//    $maki.makisu({
//        selector: 'dd',
//        overlap: 0.6,
//        speed: 0.85
//    });
//
//    $( '.list' ).makisu( 'open' );
//
//    // Toggle on click
//
//    $( '.toggle' ).on( 'click', function() {
//        $( '.list' ).makisu( 'toggle' );
//    });
//
//    // Disable all links
//
//    $( '.demo a' ).click( function( event ) {
//        event.preventDefault();
//    });
//
//} else {
//
//    $( '.warning' ).show();
//}

var _dataOffset = 0;
var _pageDataCount = 15;
var _pageCount = 0;
var _totalDataCount = 0;
var _localhostPath = "";
var _scroll = true;

$(document).ready(function(){
    var curWwwPath = window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    _localhostPath = curWwwPath.substring(0,pos);

    getKTVCount();
})

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
        getKTVInfo();
        if(_dataOffset > _totalDataCount) {
//            $("#information").append('<div class="well well-lg">已加载完所有内容</div>');
//            _scroll = false;
        }
    }
}

var getKTVInfo = function () {
    var url = _localhostPath + "/rest/ktv/info?offset=" + _dataOffset + "&length=" + _pageDataCount + "&name=null&address=null&districtId=null";
    var ktvString = "";
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            console.log(data)
            $.each(data, function(ktvIndex, ktv) {
                var imgSrc = JSON.parse(ktv.pictures).bigPictures.split(",")[0];
                console.log(imgSrc);
                ktvString += '<div class="col-sm-6 col-md-4 index"><div class="thumbnail"><img class="ktvImg" src="' + imgSrc + '" alt="..."><div class="caption"><h3>' + ktv.name.substr(0, 8) + '</h3><p>' + ktv.score + '</p><p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p></div></div></div>'
            })
            $(".row").append(ktvString);
        }
    })
}

var getKTVCount = function() {
    var url = _localhostPath + "/rest/ktv/count?name=null&address=null&districtId=nll";
    console.log(1111111111111)
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

            getKTVInfo();
        }
    })
}