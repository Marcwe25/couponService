(function()
{
var module = angular.module("couponAdmin");
module.controller("profileCtrl", profileCtrlCtor)

function profileCtrlCtor
						(	typeService,
							updaterService,
							getterService,
							loginService,
							$state) {
	
	var self				= this;
	this.nameError			= typeService.nameError;
	this.companyProfile		= getterService.dataArray;
	this.confirmPassword	= "";
	this.edit				= true;

	this.makeEdit = function(){
		this.edit = !this.edit;
	}
	
	this.display = function(item){
		if(		angular.isString(item) && 
				item.length>0){
				return item;
			}
		else return '-none-';
	}
	
	this.cancel = function(){
		this.edit = true;
		this.getProfile();
	}
	
	this.getProfile = function(){
		getterService.getDataArray();
	}
	
	this.updateProfile = function(){
		this.edit = true;
		updaterService.update(this.companyProfile[0]);
	}
	
	this.checkLogin = function(){
		   loginService.check();
	}
	
	this.getProfile();
	this.checkLogin();
	$state.go(typeService.type.targetType);
   
}

})();
