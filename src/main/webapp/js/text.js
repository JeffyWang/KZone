var _localhostPath = "";
var _pictureType = "";

$(function(){
    var curWwwPath = window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    _localhostPath = curWwwPath.substring(0,pos);
    _pictureType = $("#pictureType",parent.document).attr("rel");
    console.log(_pictureType)

    function initToolbarBootstrapBindings() {
        var fonts = ['Serif', 'Sans', 'Arial', 'Arial Black', 'Courier',
                'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact', 'Lucida Grande', 'Lucida Sans', 'Tahoma', 'Times',
                'Times New Roman', 'Verdana'],
            fontTarget = $('[title=Font]').siblings('.dropdown-menu');
        $.each(fonts, function (idx, fontName) {
            fontTarget.append($('<li><a data-edit="fontName ' + fontName +'" style="font-family:\''+ fontName +'\'">'+fontName + '</a></li>'));
        });
        $('a[title]').tooltip({container:'body'});
        $('.dropdown-menu input').click(function() {return false;})
            .change(function () {$(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');})
            .keydown('esc', function () {this.value='';$(this).change();});

        $('[data-role=magic-overlay]').each(function () {
            var overlay = $(this), target = $(overlay.data('target'));
            overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
        });
        $('#voiceBtn').hide();
    };
    initToolbarBootstrapBindings();
    $('#editor').wysiwyg();
    window.prettyPrint && prettyPrint();

});

$(".pictureBtn").on("click", function() {
    var article = $('#editor').html();
    console.log(article);
})

var tmpId = 0;
var uploadImg = function() {
    var id = $("#Filedata").attr("rel");

    var img = '<img id="pic' + tmpId + '" class="img pic" src="../img/load.gif">'
    $('#editor').append(img);

    $.ajaxFileUpload(
        {
            url: _localhostPath + "/rest/picture/" + _pictureType + "/" + id,
            fileElementId: 'Filedata',

            success: function(data) {
                data = jQuery.parseJSON(jQuery(data).text());
                var pictureUrl = data.picture_url;
                $('#pic' + tmpId).attr("src", pictureUrl);
                tmpId += 1;
            },
            error: function(data, status, e) {
                data = jQuery.parseJSON(jQuery(data).text());
                console.log(data)
            }
        });
}