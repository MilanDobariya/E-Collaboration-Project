/**
 * 
 */
var app=angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider){
	$routeProvider
	.when('/registration',{
		controller:'UserCtrl',
		templateUrl:'views/registrationform.html'
	
	})
	.when('/home',{
		templateUrl:'views/home.html'
	})
	.when('/login',{
		controller:'UserCtrl',
		templateUrl:'views/login.html'
	})
	.when('/updateprofile',{
		controller:'UserCtrl',
		templateUrl:'views/updateprofile.html'//user object in updateprofile.html
	})
	.otherwise({
		templateUrl:'views/home.html'
	
	})
	
	app.run(function($rootScope,$cookieStore,UserService,$location){
		if($rootScope.loggedInUser==undefined)
			$rootScope.loggedInUser=$cookieStore.get('loggedInUser')
	alert('entering logout')
			$rootScope.logout=function(){
			alert('entering logout')
			UserService.logout().then(function(response){
			$rootScope.message="Loggedout successfully..."
				delete $rootScope.loggedInUser
				$cookieStore.remove('loggedInUser')
				$location.path('/login')
			},function(response){
				$rootScope.error=response.data// ErrorClazz object returned from middleware
				$location.path('/login')
			
			})
			}
	
	})
})