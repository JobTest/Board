(function () {
    'use strict';

    angular
        .module('app')
        .directive('searchPanel', SearchPanel);

    function SearchPanel() {
        return {
            restrict: 'E',
            templateUrl: 'views/statistic/search-panel.html',
            controller: ['$scope', function ($scope) {
                var model = $scope.model;
                model.inputValue = '';

                this.applySearch = function () {
                    if (model.inputValue.length <= 0) {
                        return;
                    }
                    if (DUtils.isStringNumber(model.inputValue)) {
                        $scope.getDescriptionCompany(model.inputValue, function (data) {
                            $scope.selectedRecipient(data);
                        });
                    }
                };
            }],
            controllerAs: 'search'
        };
    }
})();
