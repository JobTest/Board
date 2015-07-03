/**
 * Created by vlad on 15.04.15.
 */

/**
 * Created by vlad on 13.03.15.
 */

describe('search panel directive', function () {
  'use strict';
  var $scope;
  var controller;

  beforeEach(module('app'));
  beforeEach(module('views/statistic/search-panel.html'));

    beforeEach(inject(function ($rootScope, $compile) {

      $scope = $rootScope.$new();
      $scope.model = {
        recipientInfoItems: []
      };

      var recipientElement = angular.element("<search-panel></search-panel>");
      $compile(recipientElement)($scope);
      $scope.$digest();
      controller = recipientElement.controller('searchPanel');
    }));

    describe('get data filter', function () {
      var funcIsNumber = DUtils.isStringNumber;

      it('input empty', function () {
        var model = $scope.model;
        model.inputValue = '';
        DUtils.isStringNumber = function (input) {
        };
        spyOn(DUtils, 'isStringNumber');
        controller.applySearch();
        expect(DUtils.isStringNumber).not.toHaveBeenCalled();
      });
      it('input number', function () {
        var model = $scope.model;
        model.inputValue = '111';
        var call = false;
        var descCompany = {id: 111};
        DUtils.isStringNumber = funcIsNumber;
        $scope.getDescriptionCompany = function (companyId, func) {
          func(descCompany);
        };
        $scope.selectedRecipient = function (data) {
          expect(data).toEqual(descCompany);
          call = true;
        };
        controller.applySearch();
        expect(call).toEqual(true);
      });
      it('input string', function () {
        var model = $scope.model;
        model.inputValue = 'name';
        $scope.getDescriptionCompany = function (companyId, func) {
        };
        spyOn($scope, 'getDescriptionCompany');
        controller.applySearch();
        expect($scope.getDescriptionCompany).not.toHaveBeenCalled();
      });
    });

});

