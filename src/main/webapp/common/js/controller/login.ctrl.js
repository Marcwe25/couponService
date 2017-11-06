(function()
{
var module = angular.module("couponAdmin");
module.controller("loginCtrl", loginCtrlCtor)

//controler used on login page
function loginCtrlCtor(typeService,loginService) {
	
//	check cookies for previous user
	this.checkUser = function(){
		loginService.checkUser()
	}

	this.checkUser();

}

})();
