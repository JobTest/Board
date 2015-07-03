(function () {
	angular
		.module('app')

		.factory('DataLoader', ['$http', 'config', DataLoader]);

	function DataLoader($http, config) {

		var DATE_FORMAT = "YYYY-MM-DD";
		var DATE_TIME_FORMAT = "YYYY-MM-DD:HH-mm";

		this.errorInLog = function (data, status, headers, config) {
			console.log("date=" + data + ",status=" + status + ",headers=" + headers + ",config=" + config);
		};

		this.getInterfaces = function (countryId, complexId, success, error) {
			$http.get(
				config.urlServer + 'main/interfaces', {
					params: {
						'country_id': countryId,
						'complex_id': complexId
					}
				}
			).success(success)
				.error(error ? error : this.errorInLog);
		};

		this.getRecipientsSla = function (intName, date, top, success, error) {
			var dateMoment = moment(date);
			$http.get(
				config.urlServer + 'monitoring/recipient-sla'
				, {
					params: {
						'interface': intName,
						'date': dateMoment.format(DATE_FORMAT),
						'top': top
					}
				}
			).success(success)
				.error(error ? error : this.errorInLog);
		};

		this.getCountsForRecipient = function (intName, date, recipientId, success, error) {
			var dateMoment = moment(date);
			$http.get(
				config.urlServer + 'monitoring/company-items'
				, {
					params: {
						'interface': intName,
						'date': dateMoment.format(DATE_FORMAT),
						'company_id': recipientId
					}
				}
			).success(success)
				.error(error ? error : this.errorInLog);
		};

		this.getDescriptionCompany = function (companyId, success, error) {
			$http.get(
				config.urlServer + 'monitoring/company-description'
				, {
					params: {
						'company_id': companyId
					}
				}
			).success(success)
				.error(error ? error : this.errorInLog);
		};

		this.getErrorCountsByFilter = function (filterId, complexId, dateMoment, startDate, endDate, interfaceName, success, error) {
			var params = {
				'type_id': filterId,
				'complex_id': complexId,
				'interface': interfaceName
			};
			if (dateMoment) {
				params['date'] = dateMoment.format(DATE_FORMAT);
			}
			if (startDate) {
				params['start_date'] = startDate.format(DATE_FORMAT);
			}
			if (endDate) {
				params['end_date'] = endDate.format(DATE_FORMAT);
			}

			$http.get(
				config.urlServer + 'support/err-count-by-filter', {
					params: params
				})
				.success(success)
				.error(error ? error : this.errorInLog);
		};

		this.getSupportFilters = function (success, error) {
			$http.get(
				config.urlServer + 'support/filters')
				.success(success)
				.error(error ? error : this.errorInLog);
		};

		this.getInterfaceErrorCounts = function (complexId, isSystem, dateMoment, hour, minute, success, error) {
			$http.get(
				config.urlServer + 'support/interface-error-counts', {
					params: {
						complex_id: complexId,
						system_error: isSystem,
						date: dateMoment.format(DATE_FORMAT),
						hour: hour,
						minute: minute
					}
				})
				.success(success)
				.error(error ? error : this.errorInLog);
		};

		return this;
	}
})();
