/**
 * Created by vlad on 18.05.15.
 */

(function () {
	'use strict';

	angular
		.module('app')
		.directive('navigator', Navigator);

	function Navigator() {
		return {
			restrict: "E",
			templateUrl: "views/components/navigator.html",
			controller: ['config', function (boardConfig) {
				this.urlDashboard = boardConfig.urlDashboard;

				this.biplane = this.urlDashboard + 'biplane';
				this.biplanePredmine = this.urlDashboard + 'biplane/predmine';
				this.monitoring = this.urlDashboard + 'biplane/monitoring';
				this.monitoringTimings = this.urlDashboard + 'biplane/monitoring/timings';
				this.monitoringStatistic = '#/monitoring/statistic';
				this.monitoringErrors = this.urlDashboard + 'biplane/monitoring/errors';
				this.paymentsPay = this.urlDashboard + 'biplane/payments/pay';
				this.paymentsOperday = this.urlDashboard + 'biplane/payments/operday';
				this.paymentsSelection = this.urlDashboard + 'biplane/payments/selection';
				this.paymentsTimiline = this.urlDashboard + 'biplane/payments/timeline';
				this.paymentsTrends = this.urlDashboard + 'biplane/payments/trends';
				this.paymentsSla = this.urlDashboard + 'biplane/payments/page';
				this.loadQueue = this.urlDashboard + 'biplane/debt-load/queue';
				this.loadDownload = this.urlDashboard + 'biplane/debt-load/load';
				this.loadCharts = this.urlDashboard + 'biplane/debt-load/charts';
				this.testAll = this.urlDashboard + 'biplane/tests/alltests';
				this.testMiddleware = this.urlDashboard + 'biplane/tests/middleware';
				this.testMiddleware2 = this.urlDashboard + 'biplane/tests/middleware2';
				this.supportErrors = '#/support/errors';
				this.supportQuery = '#/support/query';
				this.supportZabbix = '#/support/zabbix';
				this.supportTiming = this.urlDashboard + 'biplane/monitoring/timings';
				this.supportPay = this.urlDashboard + 'biplane/payments/pay';
				this.supportDownload = this.urlDashboard + 'biplane/debt-load/queue';

				this.bms = this.urlDashboard + "templates";
				this.bmsPayments = this.urlDashboard + "templates/pay";
				this.bmsAuthorization = '#/masspay/authorization';

				this.tickets = this.urlDashboard + 'tickets';

				this.quality = this.urlDashboard + 'quality';
			}],
			controllerAs: 'index'
		};
	}
})();
