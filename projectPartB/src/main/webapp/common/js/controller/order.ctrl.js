(function()
{
var module = angular.module("couponAdmin");
module.controller("orderCtrl", orderCtrlCtor)

//	controller for order directive
function orderCtrlCtor(
						/*DEPDENCY INJECTION*/
						orderService) {

//	------------------------
//	---CONTROLLER FUNCTION--
//	------------------------
//	object with the name of the column to be ordered
//	and boolean for ascending or descending
	this.order		= orderService.order;

//	change the order values
	this.setOrder 	= function(orderName)
    	{orderService.setOrder(orderName)}
}


})();
