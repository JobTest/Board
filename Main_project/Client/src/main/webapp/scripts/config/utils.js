/**
 * Created by vlad on 25.02.15.
 */

var DUtils = {};

DUtils.isStringNumber = function (n) {
	return !isNaN(parseFloat(n)) && isFinite(n);
};

DUtils.getDataFilter = function ($defer, params, array, total) {
	var items = array.slice((params.page() - 1) * params.count(), params.page() * params.count());
	if (total) {
		items[items.length] = total;
	}
	$defer.resolve(items);
	params.total(array.length);
};

DUtils.validDate = function (year, month, day) {
	return !(isNaN(year) || isNaN(month) || isNaN(day));
};