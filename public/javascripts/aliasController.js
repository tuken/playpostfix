var app = angular.module('myApp', []);
app.controller('aliasCtrl', function($scope, $http, $timeout) {
 $scope.aliases = [];
 $scope.newAlias = {};
 function getAllAlias() {
     $http({
         method: 'GET',
         url: '/alias/list'
     }).success(function(data, status) {
         if (status == 200) {
             $scope.aliases = data;
         }
     });
 }

 $scope.selectedAlias = {};
 $scope.editAlias = function(alias) {
     $scope.selectedAlias = angular.copy(alias);
 };

 $scope.updateAlias = function() {
     $http({
         method: 'POST',
         url: '/alias/update',
         data: $scope.selectedAlias,  // pass in form data as Json
     }).success(function(data, status) {
         $('.modal').modal('hide');
         showAlertMessage(data.status, data.msg);
         getAllAlias();
     });
 };

 $scope.removeAlias = function(aliasId){
     $http({
         method: 'GET',
         url: '/alias/delete',
         params: {aliasId: aliasId}
     }).success(function(data, status) {
         if(data.status == "success") {
             var newAliasList=[];
             angular.forEach($scope.aliases,function(alias) {
                 if(alias.id != aliasId) {
                     newAliasList.push(emp);
                 }
             });
             $scope.aliases = newAliasList;
         }
         showAlertMessage(data.status, data.msg);
     });
 }

 $scope.addAlias = function() {
     $http({
         method: 'POST',
         url: '/alias/create',
         data: $scope.newAlias,  // pass in form data as Json
     }).success(function(data, status) {
         $('.modal').modal('hide');
         if(data.status == "success") {
             var newId = data.data.id;
             $scope.newAlias["id"] = newId;
             $scope.alias.push($scope.newAlias);
             $scope.newAlias = {};
         }
         showAlertMessage(data.status, data.msg);
     });
 }

 getAllAlias();
 $scope.alerts = [];

 function showAlertMessage(status, message) {
     if(status == "success") {
         $scope.alerts.push({type: "alert-success", title: "SUCCESS", content: message});
     } else if(status == "error") {
         $scope.alerts.push({type: "alert-danger", title: "ERROR", content: message});
     }
 };
});

// Directive for alert notification. You can also use angular bootstrap-ui for better alert notifications
app.directive('notification', function($timeout) {
  return {
    restrict: 'E',
    replace: true,
    scope: {
      ngModel: '='
    },
    template: '<div ng-class="ngModel.type" class="alert alert-box">{{ngModel.content}}</div>',
    link: function(scope, element, attrs) {
      $timeout(function() {
        element.hide();
      }, 3000);
    }
  }
});
