(function () {
  'use strict';

  angular
    .module('app')
    .directive('tableRecipients', TableRecipients);

  function TableRecipients() {
    return {
      restrict: 'E',
      templateUrl: 'views/statistic/table-recipients.html',
      controller: ['$scope', 'ngTableParams', '$location', '$anchorScroll', function ($scope, ngTableParams, $location, $anchorScroll) {
        var that = this;
        var model = $scope.model;

        $scope.$parent.redrawRecipients = function () {
          that.params.reload();
          that.showAndHideLabelUp();
        };

        that.showAndHideLabelUp = function () {
          var label = $('#goto-up');
          var body = $(document.body);
          body.on('appear', '#goto-up', function (e, $affected) {
            label.addClass("appeared");
          });
          label.appear({force_process: true});

          body.on('disappear', '#goto-up', function (e, $affected) {
            label.removeClass("appeared");
          });
        };

        that.params = new ngTableParams({
          page: 1,
          count: 20
        }, {
          counts: [20, 50],
          getData: function ($defer, params) {
            DUtils.getDataFilter($defer, params, model.recipients);
          }
        });

        that.changeSelection = function (recipient) {
          var recipients = model.recipients;
          for (var i = 0; i < recipients.length; i++) {
            if (recipients[i] != recipient) {
              recipients[i].$selected = false;
            } else {
              if (recipient.$selected) {
                $scope.selectedRecipient(recipient);
              } else {
                recipient.$selected = true;
              }
            }
          }
        };
        that.gotoUp = function () {
          $location.hash('top');
          $anchorScroll();
        };
      }],
      controllerAs: 'controller'
    };
  }
})();
