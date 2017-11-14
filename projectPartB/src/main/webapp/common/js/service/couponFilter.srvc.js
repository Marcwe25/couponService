(function(){
var module = angular.module("couponAdmin");

module.service("couponFilterService", couponFilterServiceCtor);

function couponFilterServiceCtor() {

	this.cpFilter = new Coupon("","","","","","","","");
}
})()