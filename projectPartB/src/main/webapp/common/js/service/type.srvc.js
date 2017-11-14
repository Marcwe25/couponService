(function(){

var module = angular.module("couponAdmin");

// service used to hold data from page
module.service("typeService", typeServiceCtor);

function typeServiceCtor(
							/*DEPDENCY INJECTION*/
							$http,
							$injector) {
	var self		= this;
	this.client		= {profile:{}};
	this.types		= ["coupon","customer","company","purchase","profile"];
	this.type		= {clientType:'admin' , targetType:""};
	this.couponType = ['store','bill','holidays','entertainment','giftCard'];
	this.newUser	= {	newCompany: new Company('','',''), 
						newCustomer: new Customer('','',''),
						newCoupon: new Coupon('','','','','','','','')};
	
	this.resetNewUser = function(){
						this.newUser.newCompany		= new Company('','','');
						this.newUser.newCustomer	= new Customer('','','');
						this.newUser.newCoupon		= new Coupon('','','','','','','','');
	}
	
	this.setType = function(clientStr,targetStr){
		this.type.clientType = clientStr;
		this.type.targetType = targetStr;
		this.checkerService = $injector.get("checkerService");
		this.checkerService.error.isBad = false;
		this.resetNewUser();
	}
	
	this.getProfile = function(){
		$http.get("http://localhost:8080/couponService/webapi/"+this.type.clientType+"/profile").then(
				function(promise){
					self.client.profile = promise.data;
		},
				function(promise,error, status){
			if(promise.data=="need to login"){
				this.loginService = $injector.get('loginService');
				loginService.reaplyForLogin();}
		})
		
	}
}
})()