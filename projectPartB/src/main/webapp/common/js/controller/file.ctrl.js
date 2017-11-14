(function()
{
var module = angular.module("couponAdmin");
module.controller("fileCtrl", fileCtrlCtor)

//controller used for uploading
//image with fileInput directive
function fileCtrlCtor(
						/*DEPDENCY INJECTION*/
						uploadService,$scope) {

//	used to upload file
	$scope.uploadFile = function(item){
		uploadService.doPost(item);
	}

}


})();

