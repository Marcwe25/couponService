(function(){
var module = angular.module("couponAdmin");
module.directive("order",
		['orderService',
		function(){
	return {
		scope: {
		      title: '@',
		    },
		    transclude: true,
			 controller: 'orderCtrl',
			 controllerAs: 'orderCtrl',

		templateUrl:'../common/template/order.html'
	}
	
	
}]);
})()