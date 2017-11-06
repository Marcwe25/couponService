(function(){

var module = angular.module("couponAdmin");

module.service("loginService", loginServiceCtor);

function loginServiceCtor($http,$httpParamSerializerJQLike,$cookies,typeService) {
	
	this.user 		= {username:""  ,password:"" , userType:"", remember:""};
	this.userToCheck= {username:"" , password:"" , userType:"" , remember:""};
	var self = this;
	

	
	this.checkUser = function(){
	    this.userToCheck.username = $cookies.get('username')
	    if(!angular.isUndefined(this.userToCheck.username)){
	    if (this.userToCheck.username.length>0) {
	    	self.user.username = this.userToCheck.username;
	    	self.user.password = $cookies.get("password");
	    	self.user.userType = $cookies.get("userType");
	    	document.getElementById("username").value = self.user.username;
	    	document.getElementById("password").value = self.user.password;
	    	document.getElementById("userType").value = self.user.userType;
	        document.getElementById("form_id").submit();
	    }
	    }
	}
	
	this.logout = function(){
		$cookies.remove("username",{ path: '/' });
		$cookies.remove("password",{ path: '/' });
		$cookies.remove("userType",{ path: '/' });
		this.reaplyForLogin();
	}
	
	this.deleteFacade = function (){
		$http.delete("http://localhost:8080/couponService/Login").then(
				function(){
					
		},
				function(){
			
		})
		}
	
	this.reaplyForLogin = function(){
		this.deleteFacade();
	    window.location = "http://localhost:8080/couponService";
	}

	
	this.check = function(){
		$http.get("http://localhost:8080/couponService/webapi/"+typeService.type.clientType).then(
				function(promise){
					if(promise.data != "yes"){}
					if(promise.data=="need to login"){
						self.reaplyForLogin();}},
					
				function(promise,error, status){
			if(promise.data=="need to login") {self.reaplyForLogin();}
				})}
}}	)()