(function(){
var module = angular.module("couponAdmin");
module.filter("misDate",function(){
		return function(input)
			{return angular.isDate(input)
	}
	
	
});
})()