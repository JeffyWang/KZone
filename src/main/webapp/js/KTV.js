/**
 * Created by Administrator on 2014/5/23 0023.
 */
var totalCount = 0;

$(document).ready(function(){
    init();
});

var init = function() {
    var ktvString = "";
    $.ajax({
        url : "../../../../rest/ktv/info",
        type : "GET",
        dataType : "json",
        contentType : 'application/json;charset=utf-8',
        success : function(data) {
            $.each(data.data, function(ktvIndex, ktv){
                ktvString += "<tr><td>" +ktv['name'] +  "</td><td>" +ktv['phoneNumber'] + "</td><td>" +ktv['address'] + "</td><td>" +ktv['score'] +
                    "</td><td>" +ktv['createTime'] + "</td><td>" +ktv['updateTime'] + "</td></tr>";
            });
            $("#ktv").append(ktvString);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

$("#addKtv").on("click", function(){
    var name = $("#name").val();
    var phoneNumber = $("#phoneNumber").val();
    var address = $("#address").val();
    var averagePrice = $("#averagePrice").val();
    var data = "{'name':'" + name +"','phoneNumber':'" + phoneNumber + "','address':'" + address + "','averagePrice':'" + averagePrice + "'}";

    $.ajax({
        url: '../../../../rest/ktv/info',
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