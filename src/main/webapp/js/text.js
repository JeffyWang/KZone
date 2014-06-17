var _localhostPath = "";

$(function(){
    var curWwwPath = window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    _localhostPath = curWwwPath.substring(0,pos);

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

var uploadImg = function() {
    var id = $("#Filedata").attr("rel");
    $.ajaxFileUpload(
        {
            url: _localhostPath + "/rest/picture/information/" + id,
            fileElementId: 'Filedata',

            success: function(data) {
                data = jQuery.parseJSON(jQuery(data).text());
                var pictureUrl = data.pictureUrl;
                var img = '<img class="img pic" src="' + pictureUrl + '">'
                $('#editor').append(img);
            },
            error: function(data, status, e) {
                data = jQuery.parseJSON(jQuery(data).text());
                console.log(data)
            }
        });
}