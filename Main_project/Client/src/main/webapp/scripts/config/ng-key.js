/**
 * Created by vlad on 26.02.15.
 */

(function () {
    'use strict';

    angular
        .module('app')
        .directive('ngEnter', function () {
            return function (scope, element, attrs) {
                var ENTER_NUMBER_KEY = 13;
                element.bind("keydown keypress", function (event) {
                    if (event.which === ENTER_NUMBER_KEY) {
                        scope.$apply(function () {
                            scope.$eval(attrs.ngEnter);
                        });

                        event.preventDefault();
                    }
                });
            };
        });
})();