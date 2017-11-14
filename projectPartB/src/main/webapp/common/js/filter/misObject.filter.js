(function(){
var module = angular.module("couponAdmin");
module.filter("misObject",function(){
		return function(input)
			{return angular.isObject(input)
	}
	
	
});

})()
