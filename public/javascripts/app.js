var myApp = angular.module('myApp', ['ui.router']);
myApp.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/list');

    $stateProvider.state('list', {
        url: '/list',
        templateUrl: 'index.html'
        /* controller: function($scope) {
            $scope.items = ["A", "List", "Of", "Items"];
        }*/
    })
});
