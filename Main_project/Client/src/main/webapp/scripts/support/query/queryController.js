/**
 * Created by vlad on 15.05.15.
 */

(function () {
	'use strict';

	angular
		.module('app')
		.controller('SupportQueryController', ['$scope', 'config', '$sce', Query]);

	function Query($scope, config, $sce) {
		$scope.url = $sce.trustAsResourceUrl(config.urlDashboard + "biplane/support/queries");
	}
})();