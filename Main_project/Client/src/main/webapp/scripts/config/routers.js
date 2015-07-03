(function () {
	'use strict';

	angular
		.module('app')

		.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

			$urlRouterProvider.otherwise('/monitoring/statistic');
			$stateProvider
				.state('statistic', {
					url: '/monitoring/statistic',
					templateUrl: 'views/statistic/statistic-view.html'
				})
				.state('supportErrors', {
					url: '/support/errors',
					templateUrl: 'views/support/errors/errors-view.html'
				})
				.state('supportSessions', {
					url: '/support/sessions',
					templateUrl: 'views/support/sessions/sessions-view.html'
				})
				.state('supportQuery', {
					url: '/support/query',
					templateUrl: 'views/support/query/query-view.html'
				})
				.state('supportZabbix', {
					url: '/support/zabbix',
					templateUrl: 'views/support/zabbix/zabbix-view.html'
				})
				.state('authorization', {
					url: '/masspay/authorization',
					templateUrl: 'views/authorization/auth-view.html'
				});
		}]);
})();
