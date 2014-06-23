var _localhostPath = "";
var _totalDataCount = 0;
var _pageDataCount = 30;
var _pageCount = 0;
var _dataOffset = 0;
var _scroll = true;
var _likeComment = null;
var _equalKtvId = null;
var _gtScore = null;
var _orderDesc = "createTime";
var _isClearData = false;

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
        _dataOffset = _dataOffset + _pageDataCount;
        _isClearData = false;
        initPageData();
        if(_dataOffset > _totalDataCount) {
            $("#comment").append('<div class="well well-lg">已加载完所有内容</div>');
            _scroll = false;
        }
    }
}

var getDataCount = function() {
    var url = _localhostPath + "/rest/comment/count?comment=" + _likeComment + "&KTVId=" + _equalKtvId;

    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            _totalDataCount = data.dataCount;
            _pageCount = Math.ceil(_totalDataCount / _pageDataCount);

            initPageData();
        }
    })
};

var initPageData = function() {
    var commentString = "";
    var url = _localhostPath + "/rest/comment/info?offset=" + _dataOffset + "&length=" + _pageDataCount + "&comment=" + _likeComment + "&KTVId=" + _equalKtvId + "&score=" + _gtScore + "&orderDesc=" + _orderDesc ;
    if(_isClearData)
        $("#comment").html("");

    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            $.each(data, function(commentIndex, comment) {
                var createTime = date(comment.createTime);
                commentString += '<div class="panel panel-default"><div class="panel-heading"><h4 class="userName">' + comment.userName + '</h4><h4 class="time">' + createTime + '</h4><input id="star' + comment.id +'" rel="' + comment.score + '" type="number" class="star rating" min=0 max=5 step=0.5 data-size="xs"></div><div class="panel-body">' + comment.comment + '</div></div>';

            });
            $("#comment").append(commentString);
            $('.star').rating('refresh', {disabled: true, showClear: false, showCaption: true});
            $.each(data, function(commentIndex, comment) {
                $('#star' + comment.id).rating('update',comment.score );
            });


        }
    })
};

$("#go").on("click", function() {
    _likeComment = $("#commentstr").val();
    _equalKtvId = $("#ktvId").val();
    _gtScore = $("#score").val();

    if(_likeComment == "" || _likeComment == undefined)
        _likeComment = null;
    if(_equalKtvId == "" || _equalKtvId == undefined)
        _equalKtvId = null;
    if(_gtScore == "" || _gtScore == undefined)
        _gtScore = null;
    if(_orderDesc == "" || _orderDesc == undefined)
        _orderDesc = "createTime";

    _dataOffset = 0;
    _pageDataCount = 30;
    _isClearData = true;
    getDataCount();

});

$(".glyphicon-remove").on("click", function() {
    $(this).prev("input").val("");
});

$(".order").on("click", function() {
    var orderDesc = $(this).attr("rel");
    $(".dropdown-menu").prev().text($(this).html()).append('&nbsp;<span class="caret"></span>');
    _orderDesc = orderDesc;
})

