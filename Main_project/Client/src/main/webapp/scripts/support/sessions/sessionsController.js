/**
 * Created by vlad on 20.05.15.
 */
(function () {
	'use strict';

	angular
		.module('app')
		.controller('SupportSessionsController', ['$scope', 'config', '$sce', '$location', SupportSessions]);

	function SupportSessions($scope, config, $sce, $location) {
		var search = $location.$$search;
		var complex = search['complex'];
		var system = search['system'];
		var range = search['range'];
		var date = search['date'];
		var bpInterface = search['interface'];

		this.supportSessions = config.urlDashboard + 'biplane/support/sessions/?complex=' + complex + '&system=' + system + '&range=' + range + '&date=' + date + '&interface=' + bpInterface;
		$scope.url = $sce.trustAsResourceUrl(this.supportSessions);
	}
})();