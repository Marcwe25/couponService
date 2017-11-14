(function()
		{

var module = angular.module("couponAdmin");

// http://stackoverflow.com/questions/41211875/angularjs-1-6-0-latest-now-routes-not-working
module.config(['$locationProvider', function($locationProvider) {
    $locationProvider.hashPrefix('');
}]);

//another example doing it using text/ng-template
module.config(function($stateProvider, $urlRouterProvider) {

    $stateProvider
        .state("profile", {
            url: "/profileView",
            templateUrl: "customerView/profileView.html",
            controller: "profileCtrl as profileCtrl",
            

        })
        .state("coupon", {
        	url: "/couponsView",
        	templateUrl: "customerView/couponView.html",
        	controller: "listCtrl as listCtrl",
        })
        .state("purchase", {
        	url: "/purchaseView",
        	templateUrl: "customerView/purchaseView.html",
        	controller: "listCtrl as listCtrl",
        })
        .state("test", {
        	url: "/test",
        	templateUrl: "customerView/test2.html",
        	controller: "fileCtrl as fileCtrl",
        	

        })
        .state("mainCustomer", {
        	url: "/mainCustomer",
        	templateUrl: "customerView/mainCustomer.html",
        })


    $urlRouterProvider.when("", "mainCustomer");
    $urlRouterProvider.otherwise('../common/error.html');
});
})()