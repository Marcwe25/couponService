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
            url: "/profileView",
            templateUrl: "companyView/profileView.html",
            controller: "profileCtrl as profileCtrl",
            

        })
        .state("coupon", {
        	url: "/couponsView",
        	templateUrl: "/couponService/admin/adminView/couponView.html",
            controller: "listCtrl as listCtrl",
        })
        .state("mainCompany", {
        	url: "/mainCompany",
        	templateUrl: "companyView/mainCompany.html",
        })


    $urlRouterProvider.when("", "mainCompany");
    $urlRouterProvider.otherwise('../common/error.html');
});
})()