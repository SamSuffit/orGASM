'use strict';

/* Services */
if(baseUrl == undefined) {
    var baseUrl = '';
}

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
    .value('version', '0.1')
    .factory('Jacket', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/jacket/:itemId', {itemId:'@id'});
        }])
    .factory('Regulator', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/regulator/:itemId', {itemId:'@id'});
        }])
    .factory('Suit', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/suit/:itemId', {itemId:'@id'});
        }])
    .factory('Tank', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/tank/:itemId', {itemId:'@id'});
        }])
    .factory('Equipment', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/equipment/:itemId/',{itemId:'@id'} , {
                findAllRented: { method: 'GET', isArray:true, url: baseUrl+'/api/equipment/findAllRented/' }
            });
        }])
    // ------------------ Enums starts -----------------------
    .factory('Capacity', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/capacity/:itemId', {itemId:'@id'}, {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    .factory('Size', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/size/:itemId', {itemId:'@id'}, {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    .factory('Brand', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/brand/:itemId', {itemId:'@id'}, {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    .factory('BillingType', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/billing/billingType/:itemId');
        }])
    .factory('Payment', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/payment/:itemId', {} , {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    .factory('SuitPart', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/suitPart/:itemId', {} , {
                query: {method:'GET', isArray:true, transformResponse : enumsTransformResponse}
            });
        }])
    // -----------------Enums ends-----------------------------------
    .factory('Adherent', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/adherent/:itemId');
        }])
    .factory('DivingEvent', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/divingEvent/:itemId/');
        }])
    .factory('RentalRecord', ['$resource',
        function($resource){
            return $resource(baseUrl+'/api/rentalRecord/:itemId/',{itemId:'@id'} , {
                addToDivingEvent: { method: 'PUT', url: baseUrl+'/api/rentalRecord/addToDivingEvent/:dEventId?jacketId=:jacketId&regulatorId=:regulatorId&tankId=:tankId' }
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
        };

        return adminHelperService;
    }])
;