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
        .state("company", {
            url: "/companiesView",
            templateUrl: "adminView/companiesView.html",
            controller: "listCtrl as listCtrl",
        })
        .state("customer", {
        	url: "/customersView",
        	templateUrl: "adminView/customerView.html",
        	controller: "listCtrl as listCtrl",
        })
        .state("coupon", {
        	url: "/couponsView",
        	templateUrl: "adminView/couponView.html",
        	controller: "listCtrl as listCtrl",
        })
        .state("mainAdmin", {
        	url: "/mainAdmin",
        	templateUrl: "adminView/mainAdmin.html",
        	controller: "listCtrl as listCtrl",
        })


    $urlRouterProvider.when("", "mainAdmin");
    $urlRouterProvider.otherwise('../common/error.html');
});
})()