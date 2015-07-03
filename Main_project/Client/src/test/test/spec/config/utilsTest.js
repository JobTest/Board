/**
 * Created by vlad on 15.04.15.
 */

describe('test config/utils', function () {
  var $defer = {};
  var params = {};
  var page;
  var count;
  var array = [{id: 1}, {id: 2}, {id: 3}, {id: 4}, {id: 5}, {id: 6}, {id: 7}, {id: 8}, {id: 10}, {id: 11}];
  beforeEach(function () {
    params.page = function () {
      return page;
    };
    params.count = function () {
      return count;
    };
    params.total = function (length) {
    };
  });

  it('page 2, count 2', function () {
    page = 2;
    count = 2;
    $defer.resolve = function (data) {
      expect(data).toEqual([{id: 3}, {id: 4}]);
    };
    DUtils.getDataFilter($defer, params, array);
  });
  it('count more length', function () {
    page = 1;
    count = 20;
    $defer.resolve = function (data) {
      expect(data).toEqual(array);
    };
    DUtils.getDataFilter($defer, params, array);
  });
  it('page more length', function () {
    page = 20;
    count = 20;
    $defer.resolve = function (data) {
      expect(data).toEqual([]);
    };
    DUtils.getDataFilter($defer, params, array);
  });
  it('page is -1', function () {
    page = -1;
    count = 20;
    $defer.resolve = function (data) {
      expect(data).toEqual([]);
    };
    DUtils.getDataFilter($defer, params, array);
  });
  it('count is 0', function () {
    page = 1;
    count = 0;
    $defer.resolve = function (data) {
      expect(data).toEqual([]);
    };
    DUtils.getDataFilter($defer, params, array);
  });
  it('array is empty', function () {
    page = 1;
    count = 2;
    $defer.resolve = function (data) {
      expect(data).toEqual([]);
    };
    DUtils.getDataFilter($defer, params, []);
  });
  it('total', function () {
    page = 1;
    count = 2;
    $defer.resolve = function (data) {
      expect(data).toEqual([{id: 1}, {id: 2},{id: 7}]);
    };
    DUtils.getDataFilter($defer, params, array.slice(0,2), array[6]);
  });
});
