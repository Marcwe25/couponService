(function(){var module = angular.module("couponAdmin");

module.service("postService", postServiceCtor);

function postServiceCtor($http,typeService,getterService) {
	
    this.doPost = function(item){
 
    	  	$http.post("http://localhost:8080/couponService/webapi/"+typeService.type.clientType+"/"+typeService.type.targetType,item).then(
    				function(promise){
    					getterService.removeItem(item.id);
    				},
    				function(){
    			alert("failure on post")
    			return;
    		})
    	}

}})()