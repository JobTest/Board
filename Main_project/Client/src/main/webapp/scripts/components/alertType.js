/**
 * Created by vlad on 19.05.15.
 */

var AlertType = {
	SUCCESS: new AlertTypeConstructor(4, "success"),
	INFO: new AlertTypeConstructor(1, "info"),
	WARNING: new AlertTypeConstructor(2, "warning"),
	DANGER: new AlertTypeConstructor(0, "danger"),
	values: function () {
		var values = [];
		for (var item in this) {
			if (typeof this[item] == "object") {
				values[values.length] = this[item];
			}
		}
		return values;
	},
	getById: function (id) {
		var values = this.values();
		for (var i = 0; i < values.length; i++) {
			var item = values[i];
			if (item.getId() == id) {
				return item;
			}
		}
	}
};

function AlertTypeConstructor(id, name) {
	this.getId = function () {
		return id;
	};
	this.getName = function () {
		return name;
	};
}

