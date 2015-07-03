/**
 * Created by vlad on 06.05.15.
 */

(function () {
	'use strict';

	angular
		.module('app')
		.directive('complexPanel', Complex);

	function Complex() {
		return {
			restrict: 'E',
			scope: {complex: "=", interfaces: "=", selected: "=", errors: "="},
			templateUrl: 'views/support/errors/complex-panel.html',
			controller: ['$scope', function ($scope) {
				var that = this;

				var ALL_ERRORS = "все ошибки";
				var ERRORS_499 = "ошибки 499";
				var SYSTEM_ERRORS = "Системные ошибки";
				var BUSINESS_ERRORS = "Бизнес ошибки";

				that.errors = $scope.errors;
				that.list = $scope.interfaces;
				that.selected = $scope.selected;

				Highcharts.setOptions({global: {useUTC: false}});
				that.chartParamsSystem = new ChartParams(SYSTEM_ERRORS);
				that.chartParamsBusiness = new ChartParams(BUSINESS_ERRORS);

				that.callParentItemClick = function (dateInMillSec, title, x, y) {
					var isSystem = SYSTEM_ERRORS == title;
					var date = moment(dateInMillSec);
					$scope.$parent.clickItemChart($scope.complex, isSystem, date, x, y);
				};

				function ChartParams(title) {
					return {
						title: {text: title, style: {"color": "#197de1", "font-size": "26px"}},
						options: {
							chart: {type: 'column'},
							legend: {
								enabled: false
							},
							plotOptions: {
								column: {
									stacking: 'normal'
								},
								series: {
									point: {
										events: {
											click: function (event) {
												that.callParentItemClick(this.category, title, event.pageX, event.pageY);
											}
										}
									}
								}
							}
						},
						series: [
							{color: "#3090F0", data: [], name: ALL_ERRORS},
							{color: "#FF0000", data: [], name: ERRORS_499}
						],

						loading: false,
						yAxis: {
							type: "logarithmic",
							minorTickInterval: 0.1,
							title: {text: "Кол-во ошибок"},
							labels: {
								formatter: function () {
									if (this.value % 1000 == 0) {
										return this.value / 1000 + 'k';
									}
									return this.value.toFixed(0);
								}
							}
						},
						xAxis: {type: 'datetime', tickInterval: 1, title: {text: "Время"}}
					};
				}


				that.clickItem = function (item) {
					that.selected = item;
					$scope.$parent.selectedInterface($scope.complex, item);
				};

				that.updateChart = function () {
					if (!that.errors) {
						return;
					}
					var counts = that.errors;
					var system = that.chartParamsSystem.series[0].data = [];
					var business = that.chartParamsBusiness.series[0].data = [];
					var business499 = that.chartParamsBusiness.series[1].data = [];
					for (var i = 0; i < counts.length; i++) {
						var date = counts[i].date;
						system.push([date, counts[i].system]);
						business.push([date, counts[i].business]);
						business499.push([date, counts[i].code499]);
					}
				};

				$scope.$watch('errors', function (newValue, oldValue) {
					that.errors = $scope.errors;
					that.updateChart();
				});
				$scope.$watch('interfaces', function (newValue, oldValue) {
					if ($scope.interfaces) {
						that.list = $scope.interfaces;
					}
				});
				$scope.$watch('selected', function (newValue, oldValue) {
					if ($scope.selected) {
						that.selected = $scope.selected;
					}
				});
			}],
			controllerAs: 'ctr'
		};
	}
})();
