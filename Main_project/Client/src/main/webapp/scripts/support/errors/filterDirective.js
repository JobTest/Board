/**
 * Created by vlad on 05.05.15.
 */

(function () {
	'use strict';

	angular
		.module('app')
		.directive('filterPanel', Navigator);

	function Navigator() {
		return {
			restrict: 'E',
			scope: {
				filters: "=",
				filterSelected: "=",
				date: "=",
				startDate: "=",
				endDate: "=",
				applyFilter: "=apply"
			},
			templateUrl: 'views/support/errors/filter-panel.html',
			controller: ['$scope', function ($scope) {
				var that = this;

				var model = $scope.model = {
					selected: $scope.filterSelected,
					date: $scope.date.toDate(),
					startDate: $scope.startDate.toDate(),
					endDate: $scope.endDate.toDate()
				};

				that.apply = function () {
					$scope.applyFilter(model.selected, moment(model.date), moment(model.startDate), moment(model.endDate));
				};
				$scope.$watch('filterSelected', function (newValue, oldValue) {
					model.selected = $scope.filterSelected;
				});

			}],
			controllerAs: 'ctr'
		};
	}
})();
