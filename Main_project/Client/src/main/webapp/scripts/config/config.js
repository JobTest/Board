(function () {
	angular
		.module('app')

		.constant('config', {
			//urlServer: "http://10.13.71.14:8080/server/",
			//urlDashboard: "http://10.1.108.123:8181/dashboard/#!biplane/monitoring"
			urlServer: "http://10.13.71.14:8080/server/",
			urlDashboard: "http://10.13.71.14:8082/dashboard/#!",
			zabbixUrl: "http://10.13.98.170/dashboard.php?ddreset=1&sid=03db9e53deba4f94"
		});
})();
