(function(){

var module = angular.module("couponAdmin");

module.service("loginService", loginServiceCtor);

// service holding data and function relevant to login
function loginServiceCtor($http,$httpParamSerializerJQLike,$cookies,typeService) {
	
	this.user 		= {username:""  ,password:"" , userType:"", remember:""};
	this.userToCheck= {username:"" , password:"" , userType:"" , remember:""};
	var self = this;
	

//	check if cookies hold user name
//	and try to login
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
	
//	used to logout
	this.logout = function(){
		this.deleteCookies();
		this.deleteFacade();
		this.reaplyForLogin();
	}
	
//	used to delete login info from cookies
	this.deleteCookies = function(){
		$cookies.remove("username",{ path: '/' });
		$cookies.remove("password",{ path: '/' });
		$cookies.remove("userType",{ path: '/' });
	}
	
//	used to delete facade on server
	this.deleteFacade = function (){
		$http.delete("http://localhost:8080/couponService/Login").then(
				function(){
					
		},
				function(){
			
		})
		}
	
//	used to redirect to login page
	this.reaplyForLogin = function(){
	    window.location = "http://localhost:8080/couponService";
	}

	
//	check if user have a facade on the server session
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