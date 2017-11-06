(function(){

var module = angular.module("couponAdmin");

module.service("orderService", orderServiceCtor);

function orderServiceCtor() {

	this.order = {by:'' , upper: true};
	this.setOrder = function(oby){
		this.order.by = oby;
	    this.order.upper = !this.order.upper;

	}
}

})()
