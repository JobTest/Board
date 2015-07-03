(function () {
    'use strict';

    angular
        .module('app')
        .directive('infoRecipient', InfoRecipient);

    function InfoRecipient() {
        return {
            restrict: "E",
            templateUrl: "views/statistic/info-recipient.html",
            controller: ['$scope', 'ngTableParams', function ($scope, ngTableParams) {
                var that = this;

                $scope.$parent.redrawRecipientInfo = function () {
                    that.tableParams.reload();
                    that.updateChart();
                };

                that.tableParams = new ngTableParams({
                    page: 1,
                    count: 10
                }, {
                    counts: [10, 25, 50],
                    getData: function ($defer, params) {
                        DUtils.getDataFilter($defer, params, $scope.model.recipientInfoItems);
                    }
                });

                that.chartParams = {
                    options: {chart: {type: 'line'}},
                    series: [{data: [], name: 'Кол-во запросов'}, {data: [], name: 'Кол-во ошибок'}],
                    title: {text: ''},
                    legend: {layout: 'vertical', align: 'right', verticalAlign: 'middle', borderWidth: 0},
                    loading: false,
                    yAxis: {min: 0, title: {text: "Количество"}},
                    xAxis: {tickInterval: 1, title: {text: "Время(час)"}}
                };

                that.COUNT_QUERY = 0;
                that.COUNT_ERRORS = 1;

                that.updateChart = function () {
                    var counts = $scope.model.recipientInfoItems;
                    var series = that.chartParams.series;
                    var queryData = series[that.COUNT_QUERY].data = [];
                    var errorsData = series[that.COUNT_ERRORS].data = [];
                    for (var i = 0; i < counts.length; i++) {
                        var hour = counts[i].hour;
                        queryData.push([hour, counts[i].count]);
                        errorsData.push([hour, counts[i].errorCount]);
                    }
                };
            }],
            controllerAs: 'recipient'
        };
    }
})();
