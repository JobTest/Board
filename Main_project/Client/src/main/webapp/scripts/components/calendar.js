/**
 * Created by vlad on 05.05.15.
 */
(function () {
	'use strict';

	angular
		.module('app')
		.directive('calendar', Calendar);

	function Calendar() {
		return {
			restrict: "E",
			scope: {
				currentDate: "=date"
			},
			templateUrl: "views/components/calendar.html",
			controller: ['$scope', function ($scope) {
				var that = this;

				that.open = false;
				that.format = "yyyy-MM-dd";
				that.maxDate = new Date();
				that.dateOptions = {
					formatYear: 'yy',
					startingDay: 1
				};
				if ($scope.currentDate && $scope.currentDate instanceof Date) {
					that.currentDate = $scope.currentDate.getTime();
				} else {
					that.currentDate = new Date().getTime();
				}
				that.change = function () {
					$scope.currentDate = new Date(that.currentDate);
				};

				that.openDatePopup = function ($event) {
					$event.preventDefault();
					$event.stopPropagation();

					that.open = !that.open;
				};
			}],
			controllerAs: 'ctr'
		};
	}
})();
