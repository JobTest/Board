/**
 * Created by vlad on 15.05.15.
 */

(function () {
	'use strict';

	angular
		.module('app')
		.controller('SupportZabbixController', ['$scope', 'config', '$sce', Zabbix]);

	function Zabbix($scope, config, $sce) {
		$scope.url = $sce.trustAsResourceUrl(config.zabbixUrl);
	}
})();