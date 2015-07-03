/**
 * Created by vlad on 03.03.15.
 */
(function () {
	'use strict';

	angular
		.module('app')

		.controller('AuthController', ['$scope', 'AuthLoader', function ($scope, AuthLoader) {
			var that = this;

			that.authPaysMonth = [];
			that.authPays = [];

			var model = $scope.model = {
				authInMonth: [],
				auth: [],
				smsPays: []
			};

			AuthLoader.getAuthPays(function (data) {
				var dateInMonth = new Date();
				dateInMonth.setDate(1);
				var dateInMill = dateInMonth.getTime();

				var authInThisMonth = [];
				var date;
				for (var i = 0; i < data.length; i++) {
					authInThisMonth[authInThisMonth.length] = data[i];
					date = new Date(data[i].date).getTime();
					if (date < dateInMill) {
						break;
					}
				}
				model.authInMonth = authInThisMonth;
				model.auth = data;
			});

			AuthLoader.getSmsPays(function (data) {
				model.smsPays = data;
			});
		}]);
})();