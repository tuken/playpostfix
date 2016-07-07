var app = angular.module('myApp', ['ui.router']);
app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/list');

    $stateProvider
        .state('aliases', {
            url: '/alias',
            templateUrl: 'index.html'
            /* controller: function($scope) {
                $scope.items = ["A", "List", "Of", "Items"];
            }*/
        })
});
