(function()
{
var module = angular.module("couponAdmin");
module.controller("listCtrl", listCtrlCtor)

// controller used to manage views with list in table
function listCtrlCtor(
							/*DEPDENCY INJECTION*/
							getterService,
							createService,
							updaterService,
							updateAllService,
							removeService,
							typeService,
							orderService,
							switchService,
							couponFilterService,
							loginService,
							checkerService,
							postService,
							$state) {
	
//	--------------------------
//	--- CONTROLER VARIABLE ---
//	--------------------------
	
	var self 			= this;
//	hold an array containing the data to be display on the selected view
	this.dataArray		= getterService.dataArray;
//	hold the data entered to create item
	this.newUser 		= typeService.newUser;
//	the name of the column to be ordered
	this.order			= orderService.order;
//	hold data on error message(message and show true/false)
	this.error			= checkerService.error;
//	hold data defining the page displayed
//	If logged as 1/administrator showing view 2/coupon's list 
//	-->the data will be (admin,coupon))
	this.type			= typeService.type;
//	hold an array containing the types of coupon used in filtering coupon list
	this.couponType		= typeService.couponType;
//	data to be used for filtering companies or customer list
	this.companyFilter	= {id:"", name:"",email:""};
//	data to be used for filtering coupon's list
	this.couponFilter	= couponFilterService.cpFilter;
//	an array holding boolean value to decide
//	if from or to are to be used in switch directive
	this.cfSwitch		= switchService.switcher;
//	hold data to update multiple items at the same time
	this.dataToUpdate	= {myArray: []};
	
//	------------------------
//	---CONTROLLER FUNCTION--
//	------------------------
	
//	change from/to state for the given column name
//	used in switch directive
	this.switchIt = function(myBol){
		switchService.doSwitch(myBol);
	}
	
//	when multiple item have been choose to edit, return true
	this.allMode = function(){
		return this.dataToUpdate.myArray.length>1
	}
	
//	when something is choosen for update return true
//	used to disable creation and update at the same time
//	to maintain interface
	this.createMode = function(){
		return  this.dataToUpdate.myArray.length<1
	}
	
//	return if value is a date or not
	this.likeDate = function(input){
		return angular.isDate(input)
	}
	
//	use create service to send data to be created on database
	this.create = function(data){
		createService.create(data);
	}
	
//	use update service to send data to be updated on database
	this.update = function(entry){
    	entry.edit = false;
		this.dataToUpdate.myArray = this.dataToUpdate.myArray
								.filter(function(x){
									return x !== entry;
								})
		updaterService.update(entry);
	}

//	use updateAll service to update multiple item at the same time
	this.updateArray = function(){
		updateAllService.updateArray(this.dataToUpdate.myArray);
	}
	
//	use for customer to purchase coupon from purchase list
	this.purchase = function(entry){
		postService.doPost(entry);
	}
	
//	use to get the data to be shown
	this.getData = function(){
		getterService.getDataArray();
	}
	
//	use to remove item from database
	this.remove = function(entry){
		removeService.remove(entry);
	}
	
//	choose column to order
    this.setOrder = function(orderName)
    {
    	orderService.setOrder(orderName);
    }
    
//    turn a record to be editable
    this.edit = function(entry){
    	entry.edit = true;
    	this.dataToUpdate.myArray.push(entry);
    	if(this.dataToUpdate.myArray.length>0)this.updateArray;
    	
    }
    
//	check with server if facade exist   
   this.checkLogin = function(){
	   loginService.check();
   }
   
// initialization task   
   this.checkLogin();
   this.getData();
   $state.go(typeService.type.targetType);
}
})();
