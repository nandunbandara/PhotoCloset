angular.module('photocloset', [])

.controller('formController', function($scope, FileUploaderService){

    $scope.uploadFile = function(){
        $scope.uploading = true;
        var file = $scope.file;
        var promise = FileUploaderService.upload(file);

        promise.then(function(response) {

            $scope.success = response.data.success;
            $scope.message = response.data.message;
            $scope.completed = true;
            $scope.uploading = false;

            if($scope.success){
                $scope.fileUrl = response.data.fileUrl;
            }

        }, function() {
            console.log('ERROR!');
        })
    };

    $scope.goBack = function(){
        $scope.uploading = false;
        $scope.completed = false;
        $scope.file = null;
    }
})

.service('FileUploaderService', function($http, $q){
    return {
        upload: function(file){

            var fileFormatData = new FormData();
            fileFormatData.append('file', file);

            var deferred = $q.defer();
            $http.post('http://localhost:8080/storage/upload', fileFormatData, {
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            }).then(function(response){
                deferred.resolve(response);
            }).catch(function(response){
                deferred.resolve(response);
            });

            return deferred.promise;
        }
    }
})

.directive('fileUploaderModel', function($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileUploaderModel),
                modelSetter = model.assign;

            element.bind('change', function() {
                scope.$apply(function() {
                    modelSetter(scope, element[0].files[0]);
                })
            })
        }
    }
})