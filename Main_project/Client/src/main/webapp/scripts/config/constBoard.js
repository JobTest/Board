/**
 * Created by vlad on 06.03.15.
 */

(function () {
	'use strict';

	angular
		.module('app')
		.constant('boardConst', {
			COUNTRY: {id: 1, name: "Украина"},
			COMPLEX: {id: 2, name: "Задолженность"},
			URL_INTERFACE: "interface",
			URL_YEAR: "year",
			URL_MONTH: "month",
			URL_DAY: "day"
		})
		.constant('urlParams', {
			COMPLEX_ID: "complex_id",
			FILTER_RANGE: "filter_range",
			INTERFACE: "interface",
			INTERFACE1: "interface1",
			INTERFACE2: "interface2",
			DATE: "date",
			START_DATE: "start_date",
			END_DATE: "end_date"
		});
})();