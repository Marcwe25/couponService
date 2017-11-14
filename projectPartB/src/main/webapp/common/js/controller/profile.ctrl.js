(function()
{
var module = angular.module("couponAdmin");
module.controller("profileCtrl", profileCtrlCtor)

// controller used on profile page
function profileCtrlCtor
						(	/*DEPDENCY INJECTION*/
							typeService,
							updaterService,
							getterService,
							loginService,
							$state) {

	
//	--------------------------
//	--- CONTROLER VARIABLE ---
//	--------------------------
	var self				= this;
	this.nameError			= typeService.nameError;
	this.companyProfile		= getterService.dataArray;
	this.confirmPassword	= "";
	this.edit				= true;
	
//	------------------------
//	---CONTROLLER FUNCTION--
//	------------------------

//	function controling the boolean
//	who allow to edit the profile
	this.makeEdit = function(){
		this.edit = !this.edit;
	}
	
//	return item or "-none"
//	if there is no string data in item
	this.display = function(item){
		if(		angular.isString(item) && 
				item.length>0){
				return item;
			}
		else return '-none-';
	}
	
//	used to cancel update operation
	this.cancel = function(){
		this.edit = true;
		this.getProfile();
	}
	
//	used to get the profile
	this.getProfile = function(){
		getterService.getDataArray();
	}
	
//	used to update data in profile
	this.updateProfile = function(){
		this.edit = true;
		updaterService.update(this.companyProfile[0]);
	}
	
//	use to check if there is a facade
//	associate with the user
	this.checkLogin = function(){
		   loginService.check();
	}
	
//	--------------------------
//	--- INITIALIZATION TASK --
//	--------------------------
	this.getProfile();
	this.checkLogin();
	$state.go(typeService.type.targetType);
   
}

})();
