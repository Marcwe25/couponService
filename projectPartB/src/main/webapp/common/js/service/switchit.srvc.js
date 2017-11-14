(function(){

var module = angular.module("couponAdmin");

module.service("switchService", switchServiceCtor);

// service used for the switch directive
function switchServiceCtor() {

	this.switcher = new Coupon(true,true,true,true,true,true,true,true);
	this.doSwitch = function(upper){
	    this.switcher[upper] = !this.switcher[upper];

	}
}
})()