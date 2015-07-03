/**
 * Created by vlad on 13.03.15.
 */

describe('Info recipient directive', function () {
  'use strict';
  var $scope;
  var controller;

  beforeEach(module('app'));
  beforeEach(module('views/statistic/info-recipient.html'));

  beforeEach(inject(function ($rootScope, $compile) {

    $scope = $rootScope.$new();
    $scope.model = {
      recipientInfoItems: []
    };

    var recipientElement = angular.element("<info-recipient></info-recipient>");
    $compile(recipientElement)($scope);
    $scope.$digest();
    controller = recipientElement.controller('infoRecipient');
  }));

  describe('redraw recipient info', function () {
    beforeEach(function () {
      controller.tableParams.reload = function () {
      };
      controller.updateChart = function () {
      };
      spyOn(controller.tableParams, 'reload');
      spyOn(controller, 'updateChart');
    });

    it('call reload table', function () {
      $scope.redrawRecipientInfo();
      expect(controller.tableParams.reload).toHaveBeenCalled();
    });
    it('call reload chart', function () {
      $scope.redrawRecipientInfo();
      expect(controller.updateChart).toHaveBeenCalled();
    });
  });
  describe('update chart', function () {
    var infoItems = [{hour: 1, count: 11, errorCount: 111}, {hour: 2, count: 22, errorCount: 222},
      {hour: 3, count: 33, errorCount: 333}, {hour: 4, count: 44, errorCount: 444},
      {hour: 5, count: 55, errorCount: 555}, {hour: 6, count: 66, errorCount: 666}];

    it('count all and error', function () {
      $scope.model.recipientInfoItems = infoItems;
      controller.updateChart();
      var expCountValue = [[1, 11], [2, 22], [3, 33], [4, 44], [5, 55], [6, 66]];
      var expErrorValue = [[1, 111], [2, 222], [3, 333], [4, 444], [5, 555], [6, 666]];
      var series = controller.chartParams.series;
      expect(series[controller.COUNT_QUERY].data).toEqual(expCountValue);
      expect(series[controller.COUNT_ERRORS].data).toEqual(expErrorValue);
    });
  });

});

