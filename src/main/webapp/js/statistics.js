var _localhostPath = "";
var _ktvCount = 0;
var _commentCount = 0;
var _informationCount = 0;
var _gameCount = 0;

$(document).ready(function(){
    var curWwwPath = window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    _localhostPath = curWwwPath.substring(0,pos);

    countKTV();
    countComment();
    countInformation();
    countGame();
    console.log(_ktvCount)
    console.log(_commentCount)
    console.log(_informationCount)
    console.log(_gameCount)

    globalCharts();
    areaUser();
});

var globalCharts = function() {
    console.log(_ktvCount)
    console.log(_commentCount)
    console.log(_informationCount)
    console.log(_gameCount)

    $('#count').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: 'All data count'
        },
        credits: {
            text: 'keecent.com',
            href: 'http://www.keecent.com'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y}</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.y}'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: [
                ['KTV', _ktvCount],
                {
                    name: 'COMMENT',
                    y: _commentCount,
                    sliced: true,
                    selected: true
                },
                ['INFORMATION', _informationCount],
                ['GAME', _gameCount]
            ]
        }]
    });
};

var areaUser = function() {

    $('#areaUser').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80]
        },
        title: {
            text: 'Worlds largest cities per 2008'
        },
        xAxis: {
            categories: [
                'Tokyo',
                'Jakarta',
                'New York',
                'Seoul',
                'Manila',
                'Mumbai',
                'Sao Paulo',
                'Mexico City',
                'Dehli',
                'Osaka',
                'Cairo',
                'Kolkata',
                'Los Angeles',
                'Shanghai',
                'Moscow',
                'Beijing',
                'Buenos Aires',
                'Guangzhou',
                'Shenzhen',
                'Istanbul'
            ],
            labels: {
                rotation: -45,
                align: 'right',
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Population (millions)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: 'Population in 2008: <b>{point.y:.1f} millions</b>'
        },
        series: [{
            name: 'Population',
            data: [{y : 34.4,color: '#BF0B23'}, 21.8, 20.1, 20, 19.6, 19.5, 19.1, 18.4, 18,
                17.3, 16.8, 15, 14.7, 14.5, 13.3, 12.8, 12.4, 11.8,
                11.7, 11.2],
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
};

var countKTV = function() {
    var url = _localhostPath + "/rest/ktv/count?name=" + null + "&address=" + null + "&districtId=" + null;

    $.ajax({
        url: url,
        type: "GET",
        async:false,
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            _ktvCount = data.dataCount;
        }
    })
};

var countComment = function() {
    var url = _localhostPath + "/rest/information/count?comment=" + null;

    $.ajax({
        url: url,
        type: "GET",
        async:false,
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            _commentCount = data.dataCount;
        }
    })
};

var countInformation = function() {
    var url = _localhostPath + "/rest/information/count?title=" + null;

    $.ajax({
        url: url,
        type: "GET",
        async:false,
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            _informationCount = data.dataCount;
        }
    })
};

var countGame = function() {
    var url = _localhostPath + "/rest/game/count?title=" + null;

    $.ajax({
        url: url,
        type: "GET",
        async:false,
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            _gameCount = data.dataCount;
        }
    })
};