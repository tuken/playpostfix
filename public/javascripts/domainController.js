var app = angular.module('myApp', []);
app.controller('domainCtrl', function($scope, $http, $timeout) {
 $scope.domains = [];
 $scope.newDomain = {};

 function getAllDomain() {
     $http({
         method: 'GET',
         url: '/domain'
     }).success(function(data, status) {
         if (status == 200) {
             $scope.domains = data;
         }
     });
 }

 $scope.selectedDomain = {};
 $scope.editDomain = function(domain) {
     $scope.selectedDomain = angular.copy(domain);
 };

 $scope.activeDomain = function(domain) {
     $http({
         method: 'PATCH',
         url: '/domain/' + domain.id,
         data: domain,
     }).success(function(data, status) {
         showAlertMessage(data.status, data.msg);
     })
 }

 $scope.updateDomain = function() {
     $http({
         method: 'PATCH',
         url: '/domain/' + $scope.selectedDomain.id,
         data: $scope.selectedDomain,  // pass in form data as Json
     }).success(function(data, status) {
         $('.modal').modal('hide');
         showAlertMessage(data.status, data.msg);
         getAllDomain();
     });
 };

 $scope.removeDomain = function(domainId) {
     $http({
         method: 'DELETE',
         url: '/domain/' + domainId
     }).success(function(data, status) {
         if (data.status == "success") {
             var newDomainList=[];
             angular.forEach($scope.domains, function(domain) {
                 if (domain.id != domainId) {
                     newDomainList.push(domain);
                 }
             });
             $scope.domains = newDomainList;
         }
         showAlertMessage(data.status, data.msg);
     });
 }

 $scope.addDomain = function() {
     $scope.newDomain.id = 0;
     $scope.newDomain.active = true;
     $http({
        method: 'POST',
        url: '/domain',
        data: $scope.newDomain,  // pass in form data as Json
     }).success(function(data, status) {
        $('.modal').modal('hide');
        if (data.status == "success") {
           var newId = data.data.id;
           $scope.newDomain["id"] = newId;
           $scope.domains.push($scope.newDomain);
           $scope.newDomain = {};
        }
        showAlertMessage(data.status, data.msg);
     });
 }

 getAllDomain();
 $scope.alerts = [];

 function showAlertMessage(status, message) {
     if (status == "success") {
         $scope.alerts.push({type: "alert-success", title: "SUCCESS", content: message});
     }
     else if (status == "error") {
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
