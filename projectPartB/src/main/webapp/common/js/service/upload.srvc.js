(function(){var module = angular.module("couponAdmin");

module.service("uploadService", uploadServiceCtor);

function uploadServiceCtor($http) {
	
    this.doPost = function(item, callback){

    	  	$http({
                url: 'http://localhost:8080/couponService/webapi/file/upload',
                method: "POST",
                data: item,
                headers: {'Content-Type': undefined}
            }).then(
    				function(promise){
    					callback(promise);
    				},
    				function(){
    			alert("failure on upload")
    			return;
    		})
    	}

}})()
