(function()
		{
	
var module = angular.module("couponAdmin");

// service use to create an item on the database
module.service("createService", createServiceCtor);

function createServiceCtor(
							/*DEPDENCY INJECTION*/
							$http,
							getterService,
							typeService,
							checkerService,
							loginService) {
	var self		= this;
	this.type		= typeService.type;


    this.create		= function(item){
    	//data validation
    	checkerService.check(item);
    	if(checkerService.error.isBad){
    		return;
    	}
    	// send ajax request with data to be created
    	$http.post("http://localhost:8080/couponService/webapi/"+this.type.clientType+"/"+this.type.targetType,item).then(
				function(promise){
					if(promise.status=='201'){
						item.id=promise.data;
						getterService.dataArray.push(item);
						typeService.resetNewUser();
					 }
					else if(promise.status=='202'){
						if(promise.data==="duplicate"){
							checkerService.setDuplicateError();}
						else if(promise.data==="incomplete"){checkerService.setIncompleteError();}
					else if(promise.status=='205'){loginService.reaplyForLogin();}
					}
		},
				function(promise){
			loginService.reaplyForLogin()
		})
       }
    
}

})();