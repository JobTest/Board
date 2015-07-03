/**
 * Created by vlad on 07.05.15.
 */

describe("support error controller", function () {

  beforeEach(module('app'));

  var controller;
  var service;
  var $scope;
  var $location = {
    $$search: [],
    search: function () {
    }
  };
  var urlParamsMock;
  var $controllerMock;
  var listFilter = [{id: 0}, {id: 1}, {id: 2}, {id: 3}];
  var listInterface = [{id: 0}, {id: 1}, {id: 2}, {id: 3}];
  var listErrors = [{id: 0}, {id: 1}, {id: 2}, {id: 3}];
  var $timeout;

  beforeEach(inject(function ($rootScope, $controller, urlParams, _$timeout_) {
      $controllerMock = $controller;
      urlParamsMock = urlParams;
      $timeout = _$timeout_;

      service = {
        getSupportFilters: function (success, error) {
          $timeout(function () {
            success(listFilter);
          }, 10);
        },
        getInterfaces: function (countryId, complexId, success, error) {
          success(listInterface);
        },
        getErrorCountsByFilter: function (filterId, complexId, date, startDate, endDate, interfaceName, success, error) {
          $timeout(function () {
            success(listErrors);
          }, 10);
        }
      };

      $scope = $rootScope.$new();

      controller = $controller('SupportErrorsController', {
        $scope: $scope,
        $location: $location,
        DataLoader: service
      });

      controller.getBpInterface = function () {
        return listErrors[0];
      };
      controller.addAlert = function () {
      };
      controller.removedAlert = function () {
      };
      $timeout.flush();
    })
  );

  describe('init model', function () {

    it('model is not null', function () {
      expect($scope.model).not.toBeNull();
    });

    it('init filterRange', function () {
      $location.$$search = {};
      $location.$$search[urlParamsMock.FILTER_RANGE] = 1;
      controller = $controllerMock('SupportErrorsController', {
        $scope: $scope,
        $location: $location,
        DataLoader: service
      });
      $timeout.flush();

      expect($scope.model.filterSelected).toEqual(listFilter[1]);
    });

    it('init date', function () {
      $location.$$search = {};
      $location.$$search[urlParamsMock.DATE] = "2014-10-11";
      controller = $controllerMock('SupportErrorsController', {
        $scope: $scope,
        $location: $location,
        DataLoader: service
      });

      var date = moment([2014, 9, 11]);
      expect($scope.model.date.unix()).toEqual(date.unix());
    });
    it('init date default', function () {
      $location.$$search = {};
      delete $location.$$search[urlParamsMock.DATE];
      controller = $controllerMock('SupportErrorsController', {
        $scope: $scope,
        $location: $location,
        DataLoader: service
      });

      var date = moment();
      var format = "YYYY-MM-DD";
      expect($scope.model.date.format(format)).toEqual(date.format(format));
    });
  });
});
