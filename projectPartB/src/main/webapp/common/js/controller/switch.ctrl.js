(function()
{
var module = angular.module("couponAdmin");
module.controller("switchCtrl", switchCtrlCtor)

//controller used with switch directive
function switchCtrlCtor(
						/*DEPDENCY INJECTION*/
						switchService,
						couponFilterService) {
	
//	hold boolean value for each column
//	to choose between ascending and descending
	this.cfSwitch		=switchService.switcher;
	
	this.couponFilter	=couponFilterService.cpFilter;

//	check if a value is a date
	this.likeDate 		= function(input){
		return angular.isDate(input)
	}

//	used to switch boolean between true or false
	this.switchIt 		= function(myBol){
		switchService.doSwitch(myBol);
	}
}


})();
