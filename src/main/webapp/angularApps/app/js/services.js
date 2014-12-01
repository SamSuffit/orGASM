'use strict';

/* Services */

var enumsTransformResponse = function(data, headersGetter) {
    var ret = [];
    var mArray = eval(data);
    for(var i=0;i<mArray.length;i++) {
        ret.push({'label':mArray[i]});
    }
    return ret;
};

// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('myApp.services', ['ngResource'])
    .value('urlPrefix' , window.location.href.indexOf('localhost:8000') != -1 ? 'http://localhost:8080' : '')
    .factory('Jacket', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix) {
            console.log(urlPrefix);
            return $resource( urlPrefix  + '/api/jacket/:itemId?securityKey=' + localStorage.securityKey, {itemId:'@id'});
        }])
    .factory('Regulator', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix) {
            return $resource(urlPrefix + '/api/regulator/:itemId?securityKey=' + localStorage.securityKey, {itemId:'@id'});
        }])
    .factory('Suit', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/suit/:itemId?securityKey=' + localStorage.securityKey, {itemId:'@id'});
        }])
    .factory('Tank', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/tank/:itemId?securityKey=' + localStorage.securityKey, {itemId:'@id'});
        }])
    .factory('Equipment', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/equipment/:itemId/',{itemId:'@reference'} , {
                turnIn : {method: 'PUT', url: '/api/equipment/:itemId/turnIn/' },
                findAllRented: { method: 'GET', isArray:true, url: urlPrefix + '/api/equipment/findAllRented?securityKey=' + localStorage.securityKey }
            });
        }])
    // ------------------ Enums starts -----------------------
    .factory('Capacity', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/capacity/:itemId?securityKey=' + localStorage.securityKey, {itemId:'@id'}, {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    .factory('Size', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/size/:itemId?securityKey=' + localStorage.securityKey, {itemId:'@id'}, {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    .factory('Brand', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/brand/:itemId?securityKey=' + localStorage.securityKey, {itemId:'@id'}, {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    .factory('BillingType', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/billing/billingType/:itemId?securityKey=' + localStorage.securityKey);
        }])
    .factory('Payment', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/payment/:itemId?securityKey=' + localStorage.securityKey, {} , {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    .factory('SuitPart', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/suitPart/:itemId?securityKey=' + localStorage.securityKey, {} , {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    .factory('Gaz', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/gaz/:itemId?securityKey=' + localStorage.securityKey, {itemId:'@id'}, {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    .factory('Material', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/material/:itemId?securityKey=' + localStorage.securityKey, {itemId:'@id'}, {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    .factory('Screw', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/screw/:itemId?securityKey=' + localStorage.securityKey, {itemId:'@id'}, {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    // -----------------Enums ends-----------------------------------
    .factory('Adherent', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/adherent/:itemId?securityKey=' + localStorage.securityKey);
        }])
    .factory('DivingEvent', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/divingEvent/:itemId/');
        }])
    .factory('RentalRecord', ['$resource', 'urlPrefix' ,
        function($resource , urlPrefix){
            return $resource(urlPrefix + '/api/rentalRecord/:itemId/',{itemId:'@id'} , {
                addToDivingEvent: { method: 'PUT', url: '/api/rentalRecord/addToDivingEvent?dEventId=:dEventId&renterId=:renterId&equipmentId=:equipmentId' }
            });
        }])
    .factory('adminHelperService', [function(){
        var adminHelperService = new Object();

        adminHelperService.reload = function() {
            adminHelperService.$scope.items = adminHelperService._factory.query();
        };
        adminHelperService.init = function($scope,_factory,_createNewItemFunction) {

            adminHelperService.$scope = $scope;
            adminHelperService._factory = _factory;
            adminHelperService._createNewItemFunction = _createNewItemFunction;

            adminHelperService.reload();

            $scope.newItem = adminHelperService._createNewItemFunction();
            $scope.createButtonCaption = "Créer";

            $scope.hideEdit = true;
            $scope.edit = function(id) {
                $scope.newItem =  _factory.get({'itemId': id});
                $scope.createButtonCaption = "Modifier";
            }

            $scope.remove = function(id) {
                _factory.remove({'itemId': id}, function() {
                    adminHelperService.reload();
                });
            };

            $scope.cancel = function() {
                $scope.newItem = adminHelperService._createNewItemFunction();
                $scope.createButtonCaption = "Créer";
            }

            $scope.create = function() {
                $scope.newItem.$save(
                    function() {
                        adminHelperService.reload();
                        $scope.newItem = adminHelperService._createNewItemFunction();
                        $scope.createButtonCaption = "Créer";
                    }
                );
            };

            $scope.showRentHistory = function(id){
                $scope.histoItem =  _factory.get({'itemId': id});
            };
        };

        return adminHelperService;
    }])
    .factory('displayHelperService', ['$routeParams',function($routeParams){
        var displayHelperService = new Object();
        displayHelperService.init = function($scope,_factory, titlePrefix) {
            $scope.item = _factory.get({'itemId': $routeParams.id}, function () {
                $scope.historyList =  $scope.item.historyList;
                $scope.$parent.title = titlePrefix + $scope.item.reference;
                $scope.$parent.titleSmall = ''
            });
        };
        return displayHelperService;
    }])
;