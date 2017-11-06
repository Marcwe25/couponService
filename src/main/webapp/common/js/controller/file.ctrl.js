(function()
{
var module = angular.module("couponAdmin");
module.controller("fileCtrl", fileCtrlCtor)

//controller used for uploading
//image with fileInput directive
function fileCtrlCtor(uploadService,$scope) {

	$scope.uploadFile = function(item){
		uploadService.doPost(item);
	}

}


})();

