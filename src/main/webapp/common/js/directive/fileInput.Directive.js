(function(){
var module = angular.module("couponAdmin");
module.directive('fileInput', function (uploadService,typeService) {
    return {
        restrict: 'A',
        scope: true,
        link: function (scope, element, attributes) {
        	element.bind('change', function () {
                var formData = new FormData();
                formData.append('file', element[0].files[0]);
                uploadService.doPost(formData, function (callback) {
                    console.log(callback);
                    alert(element[0].files[0].name);
                    typeService.newUser.newCoupon.image=callback.data;
                });
            });

        }
    };
});;
})()

