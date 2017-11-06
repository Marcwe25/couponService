(function(){
var module = angular.module("couponAdmin");
module.directive("imageModal",
		[function(){
	return {
		scope: {
		      myObject: '='
		    },
		    transclude: true,

		templateUrl:'../common/template/imageModal.html'
	}
	
	
}]);
})()