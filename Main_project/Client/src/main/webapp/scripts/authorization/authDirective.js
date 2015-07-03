/**
 * Created by vlad on 03.03.15.
 */
(function () {
	'use strict';

	angular
		.module('app')
		.directive('authTable', function () {
			return {
				restrict: "E",
				templateUrl: "views/authorization/auth-table.html",
				scope: {
					items: '=list'
				},
				controller: ['$scope', 'ngTableParams', function ($scope, ngTableParams) {
					var that = this;

					$scope.$watch('items', function () {
						if ($scope.items.length) {
							that.updateTotal();
							that.params.reload();
						}
					});

					that.updateTotal = function () {
						var items = $scope.items;
						var columns = ['date', 'tco', 'qrCode', 'noAuth', 'persNoAuth', 'card', 'phone', 'persPhone'];

						var total = new Total(columns);

						for (var i = 0; i < items.length; i++) {
							var item = items[i];
							for (var j = 1; j < columns.length; j++) {
								var column = columns[j];
								total[column] += item[column];
							}
						}
						total[columns[4]] = (total[columns[4]] / items.length).toFixed(2);
						total[columns[7]] = (total[columns[7]] / items.length).toFixed(2);
						that.total = total;
					};

					function Total(columns) {
						if (!Array.isArray(columns)) {
							return;
						}
						this['date'] = 'Всего';
						for (var i = 1; i < columns.length; i++) {
							var column = columns[i];
							this[column] = 0;
						}
						return this;
					}

					that.params = new ngTableParams({
						page: 1,
						count: 20
					}, {
						counts: [20, 50],
						getData: function ($defer, params) {
							DUtils.getDataFilter($defer, params, $scope.items, that.total);
						}
					});
				}],
				controllerAs: 'ctr'
			};
		})
		.directive('smsTable', function () {
			return {
				restrict: "E",
				templateUrl: "views/authorization/sms-table.html",
				scope: {
					items: '=list'
				},
				controller: ['$scope', 'ngTableParams', function ($scope, ngTableParams) {
					var that = this;

					$scope.$watch('items', function () {
						if ($scope.items.length > 0) {
							that.params.reload();
						}
					});

					that.params = new ngTableParams({
						page: 1,
						count: 20
					}, {
						counts: [20, 50],
						getData: function ($defer, params) {
							DUtils.getDataFilter($defer, params, $scope.items);
						}
					});
				}],
				controllerAs: 'smsTable'
			};
		})
	;
})();
