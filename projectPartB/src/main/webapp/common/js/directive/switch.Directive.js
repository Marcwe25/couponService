(function(){
var module = angular.module("couponAdmin");
module.directive("switchit",function(){
	return {
		scope: {
		      title: '@',
		    },
			 controller: 'switchCtrl',
			 controllerAs: 'switchCtrl',

			 templateUrl: '../common/template/switch.html'
	}
});
})()