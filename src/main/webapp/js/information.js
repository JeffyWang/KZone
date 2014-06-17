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
            _pageCount = Math.ceil(_totalDataCount / _pageDataCount);

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
                var picUrl = $("<div>" + information.article + "</div>").find(".pic").attr("src");
                informationString += '<div class="well well-lg show" data-toggle="modal" data-target="#article" rel="' + information.id + '"><div class="media"><a class="pull-left" href="#"><img class="media-object" src="' + picUrl + '" alt="..."></a><div class="media-body"><h4 class="media-heading">'+ information.title + '</h4>'+ information.introduction + '</div></div></div>';
            });
            $("#information").append(informationString);

            $(".show").on("click", function() {
                var informationId = $(this).attr("rel");
                $.ajax({
                    url: _localhostPath + '/rest/information/info/' + informationId,
                    type: 'GET',
                    data:data,
                    contentType:'application/json;charset=UTF-8',
                    success: function(data){
                        console.log(data)
                        $("#tit").html("");
                        $("#art").html("");
                        $("#tit").append(data.title);
                        $("#art").append(data.article);
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
function parseDom(arg) {
    var objE = document.createElement("div");
    objE.innerHTML = arg;//赋值以后其实objE已经具有DOM的对象了
    return objE.childNodes;
};

$("#add").on("click", function() {
    $.ajax({
        url: _localhostPath + '/rest/information/info',
        type: 'POST',
        contentType:'application/json;charset=UTF-8',
        success: function(data){
           console.log(data);
            $("#infoId").attr("rel",data.id);
            $("#info").contents().find("#Filedata").attr("rel",data.id);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
            console.log(textStatus);
            console.log(XMLHttpRequest);
        }
    });
})

$("#close").on("click", function() {
    var id = $("#infoId").attr("rel");
    deleteInformation(id);
    deleteImg();
    window.location.reload();
})

var submit = function() {
    var id = $("#infoId").attr("rel");
    var title = $("#title").val();
    var introduction = $("#info").contents().find("#editor").text().replace(/(\n)+|(\r\n)+/g, "");
    var article = encodeURIComponent($("#info").contents().find("#editor").html());
    var data = '{"id":' + id + ',"title":"' + title + '", "introduction":"' + introduction + '", "article":"' + article + '"}';
    console.log(data)

    $.ajax({
        url: _localhostPath + '/rest/information/info',
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
            deleteInformation(id);
            deleteImg();

            window.location.reload();

        }
    });

}

var deleteInformation = function(id) {
    console.log("delete" + id)
    $.ajax({
        url: _localhostPath + '/rest/information/info/' + id,
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
    var img = $("#info").contents().find(".pic");

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