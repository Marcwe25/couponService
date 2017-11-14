(function(){


var module = angular.module("couponAdmin");

module.service("updateAllService", updateAllServiceCtor);

function updateAllServiceCtor($http,getterService,typeService) {
    this.updateArray = function(dataArray){
    	var self = this;
		this.type = typeService.type;
	    
    	$http.put("http://localhost:8080/couponService/webapi/"+this.type.clientType+"/"+this.type.targetType+"s",dataArray).then(
				function(promise){
					getterService.setData(promise);
					dataArray.length = 0;
				},
				function(){
			alert("failure")
		})
    }
}})()