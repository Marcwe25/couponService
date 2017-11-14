(function()
{
var module = angular.module("couponAdmin");
module.controller("loginCtrl", loginCtrlCtor)

//controler used on login page
function loginCtrlCtor(	
						/*DEPDENCY INJECTION*/
						typeService,
						loginService) {
	
	
//	------------------------
//	---CONTROLLER FUNCTION--
//	------------------------
//	check cookies for previous user
	this.checkUser = function(){
		loginService.checkUser()
	}

//	--------------------------
//	--- INITIALIZATION TASK --
//	--------------------------
	this.checkUser();

}

})();
