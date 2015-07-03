/**
 * Created by vlad on 20.05.15.
 */

describe("component alert", function () {

  var controller;
  var $rootScope;
  var $scope;
  var isoScope;
  var control;
  var $q;
  var $timeout;

  beforeEach(module('app'));
  beforeEach(module('views/components/d-alert.html'));

  beforeEach(inject(function (_$rootScope_, $compile, _$q_, _$timeout_) {
    $rootScope = _$rootScope_;
    $scope = _$rootScope_.$new();
    control = $scope.control = {};
    $q = _$q_;
    $timeout = _$timeout_;

    var recipientElement = angular.element("<d-alert control=\"control\"></d-alert>");
    $compile(recipientElement)($scope);
    $scope.$digest();
    controller = recipientElement.controller('dAlert');

    isoScope = recipientElement.isolateScope();
  }));

  it('init control', function () {
    expect(isoScope.addAlert).toEqual(control.addAlert);
    expect(isoScope.removeAlert).toEqual(control.removeAlert);
  });

  it('init alerts', function () {
    expect(controller.alerts).toEqual([]);
  });

  describe("validTypeAlert", function () {
    it('defined type', function () {
      var res = controller.validTypeAlert(AlertType.DANGER);
      expect(res).toBe(true);
    });
    it('empty type', function () {
      var res = controller.validTypeAlert({});
      expect(res).toBe(false);
    });
    it('another type', function () {
      var res = controller.validTypeAlert(Complex.API);
      expect(res).toBe(false);
    });
  });
  describe("timeoutOn", function () {
    it('remove error element', function () {
      var alert = {id: 1};
      var alerts = [{id: 0}, alert, {id: 2}];
      controller.alerts = alerts;
      controller.timeoutOn(alert, AlertType.DANGER);

      expect(alerts.length).toBe(3);
      $timeout.flush(controller.timeoutForError - 1);
      expect(alerts.length).toBe(3);
      $timeout.flush(1);
      expect(alerts[0].id).toEqual(0);
      expect(alerts[1].id).toEqual(2);
    });

    it('remove other element', function () {
      var alert = {id: 1};
      var alerts = [{id: 0}, alert, {id: 2}];
      controller.alerts = alerts;
      controller.timeoutOn(alert, AlertType.INFO);

      expect(alerts.length).toBe(3);
      $timeout.flush(controller.timeoutForOther - 1);
      expect(alerts.length).toBe(3);
      $timeout.flush(1);
      expect(alerts.length).toBe(2);
    });
  });

  describe("isSameError", function () {
    it('is same error', function () {
      var alert = {msg: 1};
      var exp = [{msg: 0}, alert, {msg: 2}];
      controller.alerts = exp.slice(0);
      var isSame = controller.isSameError(alert, AlertType.DANGER);
      expect(isSame).toBe(true);
    });
    it('is other error', function () {
      var alert = {msg: 1};
      var exp = [{msg: 0}, alert, {msg: 2}];
      controller.alerts = exp.slice(0);
      var isSame = controller.isSameError({msg: 4}, AlertType.DANGER);
      expect(isSame).toBe(false);
    });
    it('is other type', function () {
      var alert = {msg: 1};
      var exp = [{msg: 0}, alert, {msg: 2}];
      controller.alerts = exp.slice(0);
      var isSame = controller.isSameError(alert, AlertType.INFO);
      expect(isSame).toBe(false);
    });
  });

  describe("add alert", function () {
    it('defined element', function () {
      var msg = "msg";
      var alert = control.addAlert(AlertType.DANGER, msg);
      expect(controller.alerts.length).toEqual(1);
      expect(alert.type).toEqual(AlertType.DANGER.getName());
      expect(alert.msg).toEqual(msg);
    });
    it('type is wrong', function () {
      var msg = "msg";
      var alert = control.addAlert({}, msg);
      expect(controller.alerts.length).toEqual(0);
      expect(alert).not.toBeDefined();
    });

    it('msg is null/undefined', function () {
      var msg;
      var alert = control.addAlert(AlertType.DANGER, msg);
      expect(controller.alerts.length).toEqual(0);
      expect(alert).not.toBeDefined();
    });
    it('msg is empty', function () {
      var msg = "";
      var alert = control.addAlert(AlertType.DANGER, msg);
      expect(controller.alerts.length).toEqual(0);
      expect(alert).not.toBeDefined();
    });

    it('timeoutOff is true', function () {
      var msg = "msg";
      spyOn(controller, 'timeoutOn');
      control.addAlert(AlertType.DANGER, msg, true);
      expect(controller.timeoutOn).not.toHaveBeenCalled();
    });

    it('timeoutOff is false/undefined', function () {
      var msg = "msg";
      spyOn(controller, 'timeoutOn');
      control.addAlert(AlertType.DANGER, msg);
      expect(controller.timeoutOn).toHaveBeenCalled();
    });
    it('add is same', function () {
      controller.alerts = [{msg: 0}, {msg: 1}, {msg: 2}, {msg: 3}];
      control.addAlert(AlertType.DANGER, 1);
      expect(controller.alerts.length).toEqual(4);
    });
  });

  it('close alert', function () {
    controller.alerts = [{id: 0}, {id: 1}, {id: 2}, {id: 3}];
    isoScope.closeAlert(1);
    expect(controller.alerts).toEqual([{id: 0}, {id: 2}, {id: 3}]);
  });
});
