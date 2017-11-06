(function(){


var module = angular.module("couponAdmin");

module.service("removeService", removeServiceCtor);

function removeServiceCtor($http,getterService,typeService,loginService) {
	var self = this;
	
    this.remove = function(data){
    	
	this.type = typeService.type;

    	$http.delete("http://localhost:8080/couponService/webapi/"+this.type.clientType+"/"+this.type.targetType+"/"+data.id).then(
				function(promise){
					if(promise.status=='205'){loginService.reaplyForLogin()}
					if(promise.status=='200'){
					getterService.removeItem(data.id)}

				},
				function(){
					alert("fail to remove coupon id " + data.id);
					loginService.reaplyForLogin()
		})
    }
}})()