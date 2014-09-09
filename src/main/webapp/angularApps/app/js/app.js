'use strict';

function googleOnLoadCallback(){
	  var apisToLoad = 1; // must match number of calls to gapi.client.load()
	  var gCallback = function() {
	    if (--apisToLoad == 0) {
	  	    //Test if already log
	  	    signin(true, userAuthed);	
	    };
	  };
	  gapi.client.load('oauth2', 'v2', gCallback);

}

function signin(mode, callback) {
	  gapi.auth.authorize({client_id: '540472957809.apps.googleusercontent.com',
	    scope: ['https://www.googleapis.com/auth/userinfo.email'], immediate: mode},
	    callback);
	}

function userAuthed() {
	  var request =
	      gapi.client.oauth2.userinfo.get().execute(function(resp) {
	    	  console.debug(resp);
	    if (!resp.code) {
	      //Manual bootstraping of the application
	      var $injector = angular.bootstrap(document, ['myApp']);
	      console.log('Angular bootstrap complete');
	    }
	    else {
	    	console.log('Not logged');
	    	//Show logging popup
	    	signin(false, userAuthed);
	    }
	  });
	}

//Declare app level module which depends on filters, and services
angular.module('myApp', [
  'ngRoute',
  'myApp.filters',
  'myApp.services',
  'myApp.directives',
  'myApp.controllers',
  'ngAnimate'
  ,'$strap.directives'

])
.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: 'homeCtrl'});
    $routeProvider.when('/rentedEquipment', {templateUrl: 'partials/rentedEquipment.html', controller: 'rentedEquipmentCtrl'});
    $routeProvider.when('/stab', {templateUrl: 'partials/stabList.html', controller: 'stabListCtrl'});
    $routeProvider.when('/stab/:id', {templateUrl: 'partials/stabDisplay.html', controller: 'stabDisplayCtrl'});
    $routeProvider.when('/adherent', {templateUrl: 'partials/adherentList.html', controller: 'adherentListCtrl'});
    $routeProvider.when('/regulator', {templateUrl: 'partials/regulatorList.html', controller: 'regulatorListCtrl'});
    $routeProvider.when('/regulator/:id', {templateUrl: 'partials/regulatorDisplay.html', controller: 'regulatorDisplayCtrl'});
    $routeProvider.when('/tank', {templateUrl: 'partials/tankList.html', controller: 'tankListCtrl'});
    $routeProvider.when('/tank/:id', {templateUrl: 'partials/tankDisplay.html', controller: 'tankDisplayCtrl'});
    $routeProvider.when('/suit', {templateUrl: 'partials/suitList.html', controller: 'suitListCtrl'});
    $routeProvider.when('/suit/:id', {templateUrl: 'partials/suitDisplay.html', controller: 'suitDisplayCtrl'});
    $routeProvider.when('/divingEvent', {templateUrl: 'partials/divingEventList.html', controller: 'divingEventListCtrl'});
    $routeProvider.when('/divingEvent/:dEventId', {templateUrl: 'partials/divingEventEdit.html', controller: 'divingEventEditCtrl'});
    $routeProvider.otherwise({redirectTo: '/home'});
}]);
