/**
 * Created by vlad on 19.05.15.
 */

(function () {
	'use strict';

	angular
		.module('app')
		.directive('mouseUpLeft', ['$document', MouseLeave]);

	function MouseLeave($document) {
		return {
			restrict: "A",
			link: function (scope, element, attrs) {
				$document.bind('mouseup', function (event) {
					var BUTTON_LEFT = 1;
					var button = event.which || event.button;
					if (button == BUTTON_LEFT) {
						scope.$apply(function () {
							scope.$eval(attrs.mouseUpLeft);
						});

						event.preventDefault();
					}
				});
			}
		};
	}
})();
