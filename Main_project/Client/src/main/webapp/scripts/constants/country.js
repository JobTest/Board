/**
 * Created by vlad on 08.05.15.
 */


var Country = {
	UKRAINE: new CountryConstructor(1, "Украина"),
	RUSSIA: new CountryConstructor(2, "Россия"),
	GEORGIA: new CountryConstructor(3, "Грузия"),
	values: function () {
		var values = [];
		for (var item in Country) {
			if (typeof Country[item] == "object") {
				values[values.length] = Country[item];
			}
		}
		return values;
	},
	getById: function (id) {
		var values = Country.values();
		for (var i = 0; i < values.length; i++) {
			var item = values[i];
			if (item.getId() == id) {
				return item;
			}
		}
	}
};

function CountryConstructor(id, name) {
	this.getId = function () {
		return id;
	};
	this.getName = function () {
		return name;
	};
}