(function()
{
var module = angular.module("couponAdmin");
module.controller("typeCtrl", typeCtrlCtor)

function typeCtrlCtor(typeService,loginService) {
	
	this.type = {clientType:'' , targetType:""};
	typeService.type = this.type;
	
	this.setType = function(clientStr,targetStr){
		typeService.setType(clientStr,targetStr)
	}
	
	this.logout = function(){
		loginService.logout();
	}
}
})();
