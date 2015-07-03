/**
 * Created by vlad on 18.05.15.
 */

(function () {
	'use strict';

	angular
		.module('app')
		.directive('dAlert', AlertDirective);

	function AlertDirective() {
		return {
			restrict: "E",
			scope: {
				control: "="
			},
			templateUrl: "views/components/d-alert.html",
			controller: ['$scope', '$timeout', function ($scope, $timeout) {
				var that = this;

				that.alerts = [];
				that.timeoutForError = 10000;
				that.timeoutForOther = 3000;

				var control = $scope.control;

				that.validTypeAlert = function (type) {
					return type.constructor === AlertTypeConstructor;
				};

				that.timeoutOn = function (alert, type) {
					var time = type == AlertType.DANGER ? that.timeoutForError : that.timeoutForOther;
					$timeout(function () {
						$scope.removeAlert(alert);
					}, time);
				};

				that.isSameError = function (alert, type) {
					if (type != AlertType.DANGER) {
						return false;
					}
					for (var i = 0; i < that.alerts.length; i++) {
						if (that.alerts[i].msg == alert.msg) {
							return true;
						}
					}
					return false;
				};

				$scope.addAlert = function (type, msg, timeoutOff) {
					if (!that.validTypeAlert(type) || !msg) {
						return;
					}
					var alert = {type: type.getName(), msg: msg};
					if (that.isSameError(alert, type)) {
						return;
					}
					that.alerts.push(alert);
					if (timeoutOff != true) {
						that.timeoutOn(alert, type);
					}
					return alert;
				};
				control.addAlert = $scope.addAlert;

				$scope.removeAlert = function (alert) {
					var index = that.alerts.indexOf(alert);
					if (index != -1) {
						that.alerts.splice(index, 1);
					}
				};
				control.removeAlert = $scope.removeAlert;

				$scope.closeAlert = function (index) {
					that.alerts.splice(index, 1);
				};
			}],
			controllerAs: 'ctr'
		};
	}
})();
