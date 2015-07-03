describe("statistic controller", function () {

  beforeEach(module('app'));

  var controller;
  var service;
  var $scope;
  var $location = {
    $$search: []
  };
  var config = {
    URL_INTERFACE: "interface",
    URL_YEAR: "year",
    URL_MONTH: "month",
    URL_DAY: "day"
  };

  beforeEach(inject(function ($rootScope, $controller) {
    service = {
      getRecipientsSla: function () {
      },
      getInterfaces: function () {
      }
    };

    $scope = $rootScope.$new();
    $scope.redrawRecipients = function () {
    };
    $scope.redrawRecipientInfo = function () {
    };

    controller = $controller('StatisticController', {
      $scope: $scope,
      $location: $location,
      //boardConst: config,
      DataLoader: service
    });
  }));

  describe('country ukraine', function () {
    it('check id', function () {
      expect(controller.country.id).toEqual(1);
    });
    it('check name', function () {
      expect(controller.country.name).toEqual('Украина');
    });
  });

  describe('complex debt', function () {
    it('check id', function () {
      expect(controller.complex.id).toEqual(2);
    });
    it('check name', function () {
      expect(controller.complex.name).toEqual('Задолженность');
    });
  });
  describe('across page', function () {

    it('init first page', function () {
      var model = $scope.model;

      expect(model.recipientsVisible).toBe(true);
      expect(model.recipientInfoVisible).toBe(false);
    });

    it('from first page to second', function () {
      var model = $scope.model;
      controller.showSecondPage();

      expect(model.recipientsVisible).toBe(false);
      expect(model.recipientInfoVisible).toBe(true);
    });

    it('from second page to first', function () {
      var model = $scope.model;
      controller.showSecondPage();
      controller.showFirstPage();

      expect(model.recipientsVisible).toBe(true);
      expect(model.recipientInfoVisible).toBe(false);
    });
  });

  describe('update data', function () {

    describe('recipients', function () {
      it('right value and call redraw', function () {
        var model = $scope.model;
        model.bpInterface = {id: 1, name: "nameInt"};
        var value = [{id: 11}, {id: 22}];

        service.getRecipientsSla = function (bpInterface, date, top, func) {
          func(value);
          expect(bpInterface).toEqual('nameInt');
          expect(date).toEqual(model.currentDate);
          expect(top).toEqual(model.top);
          expect(model.recipients).toEqual(value);
        };
        spyOn($scope, 'redrawRecipients');
        controller.updateRecipients();

        expect($scope.redrawRecipients).toHaveBeenCalled();
      });

      it('check interface null', function () {
        controller.updateRecipients();
      });
    });

    describe('recipient info', function () {

      it('selected recipient null and interface null', function () {
        service.getCountsForRecipient = function () {
        };
        controller.updateRecipientInfo();
      });

      it('right input data and call update redraw', function () {
        var model = $scope.model;
        model.bpInterface = {name: 'intName'};
        model.selectedRecipient = {id: 123};
        var value = [{id: 11}, {id: 22}];
        service.getCountsForRecipient = function (intName, date, recipientId, func) {
          func(value);
          expect(intName).toEqual('intName');
          expect(date).toEqual(model.currentDate);
          expect(recipientId).toEqual(123);
          expect(model.recipientInfoItems).toEqual(value);
        };
        spyOn($scope, 'redrawRecipientInfo');
        controller.updateRecipientInfo();

        expect($scope.redrawRecipientInfo).toHaveBeenCalled();
      });
    });

    describe('interfaces', function () {
      it('update', function () {
        var model = $scope.model;
        var value = [{pkey: 11}, {pkey: 22}];
        controller.selectInterface = function () {
        };
        service.getInterfaces = function (countryId, complexId, func) {
          func(value);
          expect(model.bpInterfaces).toEqual(value);
        };
        controller.updateInterfaces();
      });
      it('select interface(url null) ', function () {
        var value = [{pkey: 1}, {pkey: 2}, {pkey: 3}];
        controller.selectInterface = function (bpInterface) {
          expect(bpInterface.pkey).toEqual(1);
        };
        service.getInterfaces = function (countryId, complexId, func) {
          func(value);
        };
        controller.updateInterfaces();
      });
      it('select interface(url 999) ', function () {
        $location.$$search = {};
        $location.$$search[config.URL_INTERFACE] = 999;
        var value = [{pkey: 1}, {pkey: 2}, {pkey: 3}];
        controller.selectInterface = function (bpInterface) {
          expect(bpInterface.pkey).toEqual(1);
        };
        service.getInterfaces = function (countryId, complexId, func) {
          func(value);
        };
        controller.updateInterfaces();
      });
      it('select interface(url 2)', function () {
        $location.$$search = {};
        $location.$$search[config.URL_INTERFACE] = 2;
        var value = [{pkey: 1}, {pkey: 2}, {pkey: 3}];
        controller.selectInterface = function (bpInterface) {
          expect(bpInterface.pkey).toEqual(2);
        };
        service.getInterfaces = function (countryId, complexId, func) {
          func(value);
        };
        controller.updateInterfaces();
      });
      it('select interface(url 2, value empty)', function () {
        $location.$$search = {};
        $location.$$search[config.URL_INTERFACE] = 2;
        var value = [];
        controller.selectInterface = function (bpInterface) {
        };
        spyOn(controller, 'selectInterface');

        service.getInterfaces = function (countryId, complexId, func) {
          func(value);
        };
        controller.updateInterfaces();
        expect(controller.selectInterface).not.toHaveBeenCalled();
      });
    });
  });

  describe('update url', function () {
    it('all fill', function () {
      var model = $scope.model;
      model.currentDate = new Date(2014, 10, 15);
      model.bpInterface = {pkey: 111};

      $location.search = function (k, v) {
      };
      spyOn($location, 'search');
      controller.updateUrl();
      expect($location.search.calls.argsFor(0)).toEqual([config.URL_YEAR, 2014]);
      expect($location.search.calls.argsFor(1)).toEqual([config.URL_MONTH, 11]);
      expect($location.search.calls.argsFor(2)).toEqual([config.URL_DAY, 15]);
      expect($location.search.calls.argsFor(3)).toEqual([config.URL_INTERFACE, 111]);
    });

    it('current date null', function () {
      var model = $scope.model;
      model.bpInterface = {pkey: 111};
      model.currentDate = null;

      $location.search = function (k, v) {
      };
      spyOn($location, 'search');
      controller.updateUrl();
      expect($location.search.calls.argsFor(0)).toEqual([config.URL_INTERFACE, 111]);
    });

    it('bpInterface null', function () {
      var model = $scope.model;
      model.currentDate = new Date(2014, 10, 15);
      model.bpInterface = null;
      $location.search = function (k, v) {
      };
      spyOn($location, 'search');
      controller.updateUrl();
      expect($location.search.calls.argsFor(0)).toEqual([config.URL_YEAR, 2014]);
      expect($location.search.calls.argsFor(1)).toEqual([config.URL_MONTH, 11]);
      expect($location.search.calls.argsFor(2)).toEqual([config.URL_DAY, 15]);
    });
  });
  describe('selected item', function () {

    beforeEach(function () {
      controller.updateRecipientInfo = function () {
      };
      controller.showSecondPage = function () {
      };
      controller.updateRecipients = function () {
      };
      controller.showFirstPage = function () {
      };
      controller.updateUrl = function () {
      };
      spyOn(controller, 'updateRecipientInfo');
      spyOn(controller, 'showSecondPage');
      spyOn(controller, 'updateRecipients');
      spyOn(controller, 'showFirstPage');
      spyOn(controller, 'updateUrl');
    });

    it('recipient fill', function () {
      var model = $scope.model;
      var recipient = {id: 111, name: 'name'};
      $scope.selectedRecipient(recipient);
      expect(model.selectedRecipient).toEqual(recipient);
      expect(controller.updateRecipientInfo).toHaveBeenCalled();
      expect(controller.showSecondPage).toHaveBeenCalled();
    });
    it('recipient null', function () {
      var model = $scope.model;
      var recipient = {id: 111, name: 'name'};
      model.selectedRecipient = recipient;
      $scope.selectedRecipient(null);
      expect(model.selectedRecipient).toEqual(recipient);
      expect(controller.updateRecipientInfo).not.toHaveBeenCalled();
      expect(controller.showSecondPage).not.toHaveBeenCalled();
    });
    it('interface fill', function () {
      var model = $scope.model;
      var bpInterface = {id: 111, name: 'name'};
      controller.selectInterface(bpInterface);
      expect(model.bpInterface).toEqual(bpInterface);
      expect(controller.updateRecipients).toHaveBeenCalled();
      expect(controller.showFirstPage).toHaveBeenCalled();
      expect(controller.updateUrl).toHaveBeenCalled()
    });

    it('interface null', function () {
      var model = $scope.model;
      var bpInterface = {id: 111, name: 'name'};
      model.bpInterface = bpInterface;
      controller.selectInterface(null);
      expect(model.bpInterface).toEqual(bpInterface);
      expect(controller.updateRecipients).not.toHaveBeenCalled();
      expect(controller.showFirstPage).not.toHaveBeenCalled();
      expect(controller.updateUrl).not.toHaveBeenCalled();
    });
    it('date fill', function () {
      var model = $scope.model;
      model.date = new Date(2014, 5, 10);
      controller.apply();
      expect(controller.updateRecipients).toHaveBeenCalled();
      expect(controller.showFirstPage).toHaveBeenCalled();
      expect(controller.updateUrl).toHaveBeenCalled();
    });
    it('date null', function () {
      var model = $scope.model;
      model.date = null;
      controller.apply();
      expect(controller.updateRecipients).not.toHaveBeenCalled();
      expect(controller.showFirstPage).not.toHaveBeenCalled();
      expect(controller.updateUrl).not.toHaveBeenCalled();
    });
  });

  describe('openDatePopup', function () {
    it('call all methods', function () {
      var mockEvent = {};
      mockEvent.preventDefault = function () {
      };
      mockEvent.stopPropagation = function () {
      };
      spyOn(mockEvent, 'preventDefault');
      spyOn(mockEvent, 'stopPropagation');
      controller.openDatePopup(mockEvent);
      expect(mockEvent.preventDefault).toHaveBeenCalled();
      expect(mockEvent.stopPropagation).toHaveBeenCalled();
    });
  });

});
