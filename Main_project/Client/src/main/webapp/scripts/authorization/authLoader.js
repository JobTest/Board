/**
 * Created by vlad on 18.03.15.
 */
(function () {
	angular
		.module('app')
		.factory('AuthLoader', ['$http', 'config', AuthLoader]);

	function AuthLoader($http, config) {

		this.getAuthPays = function (success) {
			$http.get(
				config.urlServer + 'masspay/auth-pays'
			).success(success)
				.error(this.errorInLog);
		};

		this.getSmsPays = function (success) {
			$http.get(
				config.urlServer + 'masspay/sms-pays'
			).success(success)
				.error(this.errorInLog);
		};

		return this;
	}

})();