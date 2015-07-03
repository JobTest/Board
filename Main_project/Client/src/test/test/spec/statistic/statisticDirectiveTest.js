/**
 * Created by vlad on 12.02.15.
 */

describe('table recipients directive', function () {
  'use strict';
  var $scope;
  var controller;
  var that = this;
  that.$location = {};
  that.$anchorScroll = function () {
  };

  beforeEach(module('app'));
  beforeEach(module('views/statistic/search-panel.html'));
  beforeEach(module('views/statistic/table-recipients.html', function ($provide) {
      $provide.value('$anchorScroll', that.$anchorScroll);
      $provide.value('$location', that.$location);
    }
  ));

  beforeEach(inject(function ($rootScope, $compile) {

    $scope = $rootScope.$new();
    $scope.model = {recipients: []};

    var recipientsTableElement = angular.element("<table-recipients></table-recipients>");
    $compile(recipientsTableElement)($scope);
    $scope.$digest();
    controller = recipientsTableElement.controller('tableRecipients');
  }));

  describe('change selection', function () {
    var recipients = [{$selected: false}, {$selected: false}, {$selected: false}];

    it('click one', function () {
      var model = $scope.model;
      model.recipients = recipients;
      controller.changeSelection(recipients[1]);
      expect(recipients[1].$selected).toEqual(true);
    });
    it('double click', function () {
      var call = false;
      $scope.selectedRecipient = function (recipient) {
        expect(recipient).toEqual(recipients[1]);
        call = true;
      };
      var model = $scope.model;
      model.recipients = recipients;
      controller.changeSelection(recipients[1]);
      expect(call).toBe(true);
    });
    it('click on first after second', function () {
      var model = $scope.model;
      model.recipients = recipients;
      controller.changeSelection(recipients[0]);
      controller.changeSelection(recipients[1]);
      expect(recipients[0].$selected).toEqual(false);
      expect(recipients[1].$selected).toEqual(true);
    });
  });
  describe('gotoUp', function () {
    it('click on first after second', function () {
      var call = false;
      that.$location.hash = function (data) {
        expect(data).toEqual('top');
        call = true;
      };
      controller.gotoUp();
      expect(call).toEqual(true);
    });
  });
});
