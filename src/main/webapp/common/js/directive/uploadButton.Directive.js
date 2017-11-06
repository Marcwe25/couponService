(function(){
var module = angular.module("couponAdmin");
module.directive('uploadIt', function () {
    return {
    	templateUrl: '../common/template/uploadButton.html',
        controller: "fileCtrl as fileCtrl",

    };
});;
})()

