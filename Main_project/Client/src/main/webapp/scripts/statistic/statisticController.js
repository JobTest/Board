(function () {
  'use strict';

  angular
    .module('app')
    .controller('StatisticController', ['$scope', '$location', 'boardConst', 'DataLoader', Statistic]);

  function Statistic($scope, $location, boardConst, DataLoader) {
    var that = this;

    that.country = boardConst.COUNTRY;
    that.complex = boardConst.COMPLEX;

    var date = getDateFromUrl($location, boardConst);
    var model = $scope.model = {
      bpInterface: null,
      bpInterfaces: [],
      recipients: [],
      recipientInfoItems: [],
      recipientsVisible: true,
      recipientInfoVisible: false,
      top: 50,
      currentDate: date,
      date: date.getTime(),
      maxDate: new Date(),
      format: 'yyyy-MM-dd',
      dateOptions: {
        formatYear: 'yy',
        startingDay: 1
      }
    };

    function getDateFromUrl($location, boardConst) {
      var search = $location.$$search;
      var year = search[boardConst.URL_YEAR];
      var month = search[boardConst.URL_MONTH];
      var day = search[boardConst.URL_DAY];
      return DUtils.validDate(year, month, day) ? new Date(year, month - 1, day) : new Date();
    }

    that.updateRecipients = function () {
      var date = model.currentDate;
      var intName = model.bpInterface ? model.bpInterface.name : '';

      if (model.recipients.length > 0) {
        model.recipients = [];
        $scope.redrawRecipients();
      }
      DataLoader.getRecipientsSla(intName, date, model.top, function (data) {
        model.recipients = data;
        $scope.redrawRecipients();
      });
    };

    that.updateRecipientInfo = function () {
      var intName = model.bpInterface ? model.bpInterface.name : '';
      var date = model.currentDate;
      var recipientId = model.selectedRecipient ? model.selectedRecipient.id : '';
      if (model.recipientInfoItems.length > 0) {
        model.recipientInfoItems = [];
        $scope.redrawRecipientInfo();
      }
      DataLoader.getCountsForRecipient(intName, date, recipientId, function (data) {
        model.recipientInfoItems = data;
        $scope.redrawRecipientInfo();
      });
    };

    that.updateUrl = function () {
      var date = model.currentDate;
      var bpInterface = model.bpInterface;
      if (date) {
        $location.search(boardConst.URL_YEAR, date.getFullYear());
        $location.search(boardConst.URL_MONTH, date.getMonth() + 1);
        $location.search(boardConst.URL_DAY, date.getDate());
      }
      if (bpInterface) {
        $location.search(boardConst.URL_INTERFACE, bpInterface.pkey);
      }
    };

    $scope.selectedRecipient = function (recipient) {
      if (!recipient) {
        return;
      }
      model.selectedRecipient = recipient;
      that.updateRecipientInfo();
      that.showSecondPage();
    };

    that.showFirstPage = function () {
      model.recipientsVisible = true;
      model.recipientInfoVisible = false;
    };

    that.showSecondPage = function () {
      model.recipientsVisible = false;
      model.recipientInfoVisible = true;
    };

    that.updateInterfaces = function () {
      DataLoader.getInterfaces(that.country.id, that.complex.id, function (data) {
        model.bpInterfaces = data;
        var interfaceId = $location.$$search[boardConst.URL_INTERFACE];
        for (var i = 0; i < data.length; i++) {
          var bpInterface = data[i];
          if (bpInterface.pkey == interfaceId) {
            that.selectInterface(bpInterface);
            return;
          }
        }
        if (data.length > 0) {
          that.selectInterface(data[0]);
        }
      });
    };
    that.updateInterfaces();

    that.selectInterface = function (bpInterface) {
      if (!bpInterface) {
        return;
      }
      model.bpInterface = bpInterface;
      that.updateRecipients();
      that.showFirstPage();
      that.updateUrl();
    };

    that.apply = function () {
      if (model.date) {
        model.currentDate = new Date(model.date);
        that.updateRecipients();
        that.showFirstPage();
        that.updateUrl();
      }
    };

    that.openDatePopup = function ($event) {
      $event.preventDefault();
      $event.stopPropagation();

      model.openedDatePopup = true;
    };

    $scope.getDescriptionCompany = function (companyId, success) {
      DataLoader.getDescriptionCompany(companyId, success);
    }
  }
})();
