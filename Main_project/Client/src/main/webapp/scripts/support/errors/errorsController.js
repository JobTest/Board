/**
 * Created by vlad on 05.05.15.
 */

(function () {
	'use strict';

	angular
		.module('app')
		.controller('SupportErrorsController', ['$scope', '$location', 'urlParams', 'DataLoader', Errors]);

	function Errors($scope, $location, urlParams, DataLoader) {
		var that = this;
		var defaultInterface = {pkey: "null", name: "Все интерфейсы", description: "Все интерфейсы"};
		var complexes = [Complex.API, Complex.TEMPLATES];

		var search = $location.$$search;
		var bpInterfaces = [];
		bpInterfaces[complexes[0].getId()] = search[urlParams.INTERFACE1];
		bpInterfaces[complexes[1].getId()] = search[urlParams.INTERFACE2];
		var filterId = search[urlParams.FILTER_RANGE];
		var dateStr = search[urlParams.DATE];
		var startDateStr = search[urlParams.START_DATE];
		var endDateStr = search[urlParams.END_DATE];

		that.DATE_FORMAT = "YYYY-MM-DD";
		that.DATE_TIME_FORMAT = "YYYY-MM-DD_HH.mm";
		that.popupStyle = {visibility: 'hidden'};
		that.lastQuery = {};
		that.href = "#/support/sessions?";
		$scope.alertControl = {};

		var model = $scope.model = {
			filters: [],
			filterSelected: {},
			date: parseDate(dateStr),
			startDate: parseDate(startDateStr),
			endDate: parseDate(endDateStr),
			country: Country.UKRAINE,
			complexes: complexes,
			interfaces: {},
			interfaceSelected: {},
			errors: {},
			interfaceErrorCounts: []
		};

		that.addAlert = function (type, msg, timeoutOff) {
			if ($scope.alertControl.addAlert) {
				return $scope.alertControl.addAlert(type, msg, timeoutOff);
			}
		};

		that.removeAlert = function (alert) {
			if ($scope.alertControl.removeAlert) {
				$scope.alertControl.removeAlert(alert);
			}
		};

		that.errorHandler = function (data, status, headers, config) {
			console.log(new Object(data));
			that.addAlert(AlertType.DANGER, data.message);
		};

		that.updateInterfaces = function (complex, run, param) {
			model.interfaces[complex.getId()] = [];
			DataLoader.getInterfaces(model.country.getId(), complex.getId(), function (data) {
				data.splice(0, 0, defaultInterface);
				model.interfaces[complex.getId()] = data;
				run(param);
			}, that.errorHandler);
		};

		that.updateUrl = function () {
			$location.search(urlParams.FILTER_RANGE, model.filterSelected.id);
			$location.search(urlParams.DATE, convertDate(model.date));
			$location.search(urlParams.START_DATE, convertDate(model.startDate));
			$location.search(urlParams.END_DATE, convertDate(model.endDate));
			$location.search(urlParams.INTERFACE1, model.interfaceSelected[complexes[0].getId()].pkey);
			$location.search(urlParams.INTERFACE2, model.interfaceSelected[complexes[1].getId()].pkey);
		};

		that.updateErrorCounts = function (complex, bpInterface) {
			var interfaceName = (!bpInterface || bpInterface === defaultInterface) ? "null" : bpInterface.name;
			var msgLoad = "Загрузка " + complex.getName() + "...";
			var msgLoadDone = "Комплекс " + complex.getName() + " загружено.";
			var alert = that.addAlert(AlertType.INFO, msgLoad, true);
			var filterSelectedId = model.filterSelected.id;
			model.errors[complex.getId()] = [];
			var query = [filterSelectedId, complex.getId(), model.date, model.startDate, model.endDate, interfaceName];
			that.lastQuery[complex.getId()] = query;
			DataLoader.getErrorCountsByFilter(query[0], query[1], query[2], query[3], query[4], query[5], function (data) {
				that.removeAlert(alert);
				that.addAlert(AlertType.SUCCESS, msgLoadDone);
				if (that.lastQuery[complex.getId()] == query) {
					model.errors[complex.getId()] = data;
				}
				that.updateUrl();
			}, function (data) {
				that.removeAlert(alert);
				that.errorHandler(data);
			});
		};

		that.getBpInterface = function (complex, bpInterface) {
			var listInterfaces = model.interfaces[complex.getId()];
			for (var i = 0; i < listInterfaces.length; i++) {
				if (listInterfaces[i].pkey == bpInterface) {
					return listInterfaces[i];
				}
			}
			return defaultInterface;
		};

		that.init = function () {
			for (var i = 0; i < complexes.length; i++) {
				var complex = complexes[i];
				model.errors[complex.getId()] = [];
				model.interfaces[complex.getId()] = [];
				that.updateInterfaces(complex, function (complex) {
					var bpInterface = that.getBpInterface(complex, bpInterfaces[complex.getId()]);
					model.interfaceSelected[complex.getId()] = bpInterface;
					that.updateErrorCounts(complex, bpInterface);
				}, complex);
			}
		};
		that.initFilter = function () {
			var filters = model.filters;
			for (var i = 0; i < filters.length; i++) {
				if (filterId == filters[i].id) {
					model.filterSelected = filters[i];
					return;
				}
			}
			if (filters.length > 0) {
				model.filterSelected = filters[0];
			}
		};

		that.updateFilters = function () {
			DataLoader.getSupportFilters(function (data) {
				model.filters = data;
				that.initFilter();
				that.init();
			}, that.errorHandler);
		};
		that.updateFilters();

		that.updateInterfaceErrorCounts = function (complexId, isSystem, date, hour, minute, success) {
			DataLoader.getInterfaceErrorCounts(complexId, isSystem, date, hour, minute, function (data) {
				model.interfaceErrorCounts = data;
				success();
			}, that.errorHandler);
		};

		$scope.closePopup = function () {
			var style = that.popupStyle;
			style.visibility = 'hidden';
			style.top = 0;
			style.left = 0;
		};

		$scope.applyFilter = function (filter, date, startDate, endDate) {
			model.filterSelected = filter;
			model.date = date;
			model.startDate = startDate;
			model.endDate = endDate;
			for (var i = 0; i < complexes.length; i++) {
				var complex = complexes[i];
				that.updateErrorCounts(complex, model.interfaceSelected[complex.getId()]);
			}
		};

		$scope.selectedInterface = function (complex, bpInterface) {
			model.interfaceSelected[complex.getId()] = bpInterface;
			that.updateErrorCounts(complex, bpInterface);
		};

		$scope.clickItemChart = function (complex, isSystem, date, x, y) {
			var hour;
			var minute;
			switch (model.filterSelected.id) {
				case 0:
					hour = date.hours();
					minute = date.minutes();
					break;
				case 1:
					hour = date.hours();
			}
			that.updateInterfaceErrorCounts(complex.getId(), isSystem, date, hour, minute, function () {
				var list = model.interfaceErrorCounts;
				var name = model.interfaceSelected[complex.getId()].name;
				if (name != defaultInterface.name) {
					var j = list.length;
					while (j--) {
						if (name != list[j].interfaceName) {
							list.splice(j, 1);
						}
					}
				}
				var dateFormat = date.format(that.DATE_TIME_FORMAT);
				for (var i = 0; i < list.length; i++) {
					var interfaceName = list[i].interfaceName;
					list[i].href = that.href + 'complex=' + complex.getId() + '&system=' + isSystem + '&range=' + model.filterSelected.id + '&date=' + dateFormat + '&interface=' + interfaceName;
				}
				var style = that.popupStyle;
				style.visibility = 'visible';
				style.top = y - 10;
				style.left = x - 10;
			});
		};

		function convertDate(dateMoment) {
			return dateMoment.format(that.DATE_FORMAT)
		}

		function parseDate(dateStr) {
			var date = moment(dateStr, that.DATE_FORMAT);
			if (!date.isValid()) {
				date = moment();
			}
			return date;
		}
	}
})();