/**
 * Created by vlad on 07.05.15.
 */


var Complex = {
	API: new ComplexConstructor(1, "Прием платежей (API 2.x)"),
	DEBT: new ComplexConstructor(2, "Задолженность"),
	IRBIS: new ComplexConstructor(3, "ПК Ирбис"),
	OCTOPUS: new ComplexConstructor(4, "ПК Octopus"),
	TEMPLATES: new ComplexConstructor(5, "Шаблоны"),
	REPORTS: new ComplexConstructor(6, "Отчеты"),
	SERVER_AUTO_UPLOAD: new ComplexConstructor(7, "Cервер автоматической выгрузки"),
	values: function () {
		var values = [];
		for (var item in Complex) {
			if (typeof Complex[item] == "object") {
				values[values.length] = Complex[item];
			}
		}
		return values;
	},
	getById: function (id) {
		var values = Complex.values();
		for (var i = 0; i < values.length; i++) {
			var item = values[i];
			if (item.getId() == id) {
				return item;
			}
		}
	}
};

function ComplexConstructor(id, name) {
	this.getName = function () {
		return name;
	};
	this.getId = function () {
		return id;
	};
}