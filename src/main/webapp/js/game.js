var _localhostPath = "";
var _likeGameName = null;
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
    var url = _localhostPath + "/rest/game/count?name=" + _likeGameName;

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
}

var initPageData = function() {
    var informationString = "";
    var url = _localhostPath + "/rest/game/info?offset=" + _dataOffset + "&length=" + _pageDataCount + "&name=" + _likeGameName;

    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            console.log(data)
            $.each(data, function(gameIndex, game) {
                var picUrl = $("<div>" + game.game + "</div>").find(".pic").attr("src");
                informationString += '<div class="col-lg-4 game"><img class="img-circle" src="' + picUrl + '" alt="Generic placeholder image" style="width: 140px; height: 140px;"><h2 class="name">' + game.name + '</h2><p class="introduce">' + game.introduction + '</p><p><a class="btn btn-default shows" data-toggle="modal"  data-target="#gameBody" rel="' + game.id + '" role="button">View details</a></p></div>'
            });
            $("#games").append(informationString);

            $(".shows").on("click", function() {
                var gameId = $(this).attr("rel");
                console.log(gameId + " gameId")
                $.ajax({
                    url: _localhostPath + '/rest/game/info/' + gameId,
                    type: 'GET',
                    data:data,
                    contentType:'application/json;charset=UTF-8',
                    success: function(data){
                        console.log(data)
                        $("#nam").html("");
                        $("#gam").html("");
                        $("#nam").append(data.name);
                        $("#gam").append(data.game);
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown) {
                        console.log(errorThrown);
                        console.log(textStatus);
                        console.log(XMLHttpRequest);
                    }
                });
            });
        }
    })
}

$("#add").on("click", function() {
    $.ajax({
        url: _localhostPath + '/rest/game/info',
        type: 'POST',
        contentType:'application/json;charset=UTF-8',
        success: function(data){
            console.log(data);
            $("#gameId").attr("rel",data.id);
            $("#game").contents().find("#Filedata").attr("rel",data.id);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
            console.log(textStatus);
            console.log(XMLHttpRequest);
        }
    });
})

$("#close").on("click", function() {
    var id = $("#gameId").attr("rel");
    deleteGame(id);
    deleteImg();
    window.location.reload();
})

var submit = function() {
    var id = $("#gameId").attr("rel");
    var name = $("#name").val();
    var introduction = $("#game").contents().find("#editor").text().replace(/(\n)+|(\r\n)+/g, "");
    var game = encodeURIComponent($("#game").contents().find("#editor").html());
    var data = '{"id":' + id + ',"name":"' + name + '", "introduction":"' + introduction + '", "game":"' + game + '"}';
    console.log(data)

    $.ajax({
        url: _localhostPath + '/rest/game/info',
        type: 'PUT',
        data:data,
        contentType:'application/json;charset=UTF-8',
        success: function(data){
            console.log(data)
            window.location.reload();
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
            console.log(textStatus);
            console.log(XMLHttpRequest);
            deleteGame(id);
            deleteImg();

            window.location.reload();

        }
    });

}

var deleteGame = function(id) {
    console.log("delete" + id)
    $.ajax({
        url: _localhostPath + '/rest/game/info/' + id,
        type: 'DELETE',
        contentType:'application/json;charset=UTF-8',
        success: function(data){
            console.log(data)
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
            console.log(textStatus);
            console.log(XMLHttpRequest);
        }
    });
}

var deleteImg = function() {
    var img = $("#game").contents().find(".pic");

    for(var i = 0; i < img.length; i ++) {
        var imgUrl = img[i].src
        var imgName = imgUrl.split("/");
        var name = imgName[imgName.length - 1];
        $.ajax({
            url: _localhostPath + '/rest/picture/' + name,
            type: 'DELETE',
            contentType:'application/json;charset=UTF-8',
            success: function(data){
                console.log(data)

            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
                console.log(textStatus);
                console.log(XMLHttpRequest);
            }
        });
    }
}