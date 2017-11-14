(function(){
var module = angular.module("couponAdmin");
module.directive("couponModal",
		[function(){
	return {
		scope: {
		      myObject: '='
		    },
		    transclude: true,

		templateUrl:'../common/template/couponModal.html'
	}
	
	
}]);
})()