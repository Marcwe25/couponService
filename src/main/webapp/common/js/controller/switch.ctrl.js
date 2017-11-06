(function()
{
var module = angular.module("couponAdmin");
module.controller("switchCtrl", switchCtrlCtor)

function switchCtrlCtor(switchService,
						couponFilterService) {
	
	this.cfSwitch		=switchService.switcher;
	
	this.likeDate 		= function(input){
		return angular.isDate(input)
	}

	this.couponFilter	=couponFilterService.cpFilter;

	this.switchIt 		= function(myBol){
		switchService.doSwitch(myBol);
	}
}


})();
