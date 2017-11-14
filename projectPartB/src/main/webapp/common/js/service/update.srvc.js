(function() {
	var module = angular.module("couponAdmin");

	module.service("updaterService", updaterServiceCtor);

	function updaterServiceCtor($http, typeService, getterService) {

		this.update = function(item) {
			if (item == "") {
				return;
			}
			var self = this;
			this.dataArray = [];

			this.update_company = function(promise) {
				for (var i = 0; i < promise.data.length; i++) {
					self.dataArray[i] = {};
					self.dataArray[i].email = promise.data[i].email;
				}
			}

			this.update_coupon = function(promise) {
				item.end_date = new Date(promise.data.end_date);
				item.price = promise.data.price;
			}
			
			this.update_profile = function(promise) {
				getterService.setData(promise);
			}

			if (typeService.type.targetType == "coupon") {
				if (item.price == null) {
					item.price = 0
				}
			}
			$http.put(
					"http://localhost:8080/couponService/webapi/"
							+ typeService.type.clientType + "/"
							+ typeService.type.targetType, item).then(
					function(promise) {
						if (promise.status == '205') {
							getterService.getDataArray();
						} else {
							eval("self.update_" + typeService.type.targetType
									+ "(promise)")
						}
					}, function() {
						getterService.getDataArray();
					})
		}
	}
})()