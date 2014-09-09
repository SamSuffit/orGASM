'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
    .controller('homeCtrl',  ['$scope' , function($scope) {
        $scope.$parent.location = 'Home';
        $scope.$parent.title = "Bienvenue dans l'application de gestion de location du GASM";
        $scope.$parent.titleSmall = '';
        $scope.true = true;
        $scope.false = false;
    }])
    .controller('rentedEquipmentCtrl',  ['$scope' ,'Equipment' , function($scope,Equipment) {
        $scope.$parent.location = 'Matos loué';
        $scope.$parent.title = 'Liste du matériel actuellement en location';
        $scope.$parent.titleSmall = '';
        $scope.equipments = Equipment.findAllRented();

        $scope.turnIn = function(item){
            item.$turnIn({},function() {
                $scope.equipments = Equipment.findAllRented();
            });
        }
    }])
    .controller('stabListCtrl', ['$scope' ,'Jacket', 'adminHelperService' ,
        'Brand', 'Size',
        function($scope,Jacket,adminHelperService,Brand, Size) {
            $scope.$parent.location = 'Liste des stabs';
            $scope.$parent.title = 'Administration stab';
            $scope.$parent.titleSmall = '';
            adminHelperService.init($scope, Jacket, function() { return new Jacket()});
            $scope.brands = Brand.query();
            $scope.sizes = Size.query();
        }])
    .controller('regulatorListCtrl', ['$scope' ,'Regulator', 'adminHelperService' ,
        'Brand',
        function($scope,Regulator,adminHelperService,Brand) {
            $scope.$parent.location = 'Liste des détenteurs';
            $scope.$parent.title = 'Administration des détendeurs';
            $scope.$parent.titleSmall = '';
            adminHelperService.init($scope, Regulator, function() { return new Regulator()});
            $scope.brands = Brand.query();
        }])
    .controller('tankListCtrl', ['$scope' ,'Tank', 'adminHelperService' ,
        'Brand','Capacity','Gaz','Material','Screw',
        function($scope,Tank,adminHelperService,Brand,Capacity, Gaz, Material, Screw) {
            $scope.$parent.location = 'Liste des blocs';
            $scope.$parent.title = 'Administration des blocs';
            $scope.$parent.titleSmall = '';
            adminHelperService.init($scope, Tank, function() { return new Tank()});
            $scope.brands = Brand.query();
            $scope.capacities = Capacity.query();
            $scope.gazes = Gaz.query();
            $scope.materials = Material.query();
            $scope.screws = Screw.query();

            $scope.$watch('newItem.lastDateOfTIV', function(v){ // using the example model from the datepicker docs
                if( v != undefined && !(v instanceof Date)) {
                    try {
                        $scope.newItem.lastDateOfTIV= new Date(v);
                    } catch (e) {}
                }
            });

            $scope.$watch('newItem.testDate', function(v){ // using the example model from the datepicker docs
                if( v != undefined && !(v instanceof Date)) {
                    try {
                        $scope.newItem.testDate= new Date(v);
                    } catch (e) {}
                }
            });

            $scope.$watch('newItem.buildDate', function(v){ // using the example model from the datepicker docs
                if( v != undefined && !(v instanceof Date)) {
                    try {
                        $scope.newItem.buildDate= new Date(v);
                    } catch (e) {}
                }
            });
        }])
    .controller('suitListCtrl', ['$scope' ,'Suit', 'adminHelperService' ,
        'Brand','Size','SuitPart',
        function($scope,Suit,adminHelperService,Brand,Size,SuitPart) {
            $scope.$parent.location = 'Liste des combinaisons';
            $scope.$parent.title = 'Administration des combinaisons';
            $scope.$parent.titleSmall = '';
            adminHelperService.init($scope, Suit, function() { return new Suit()});
            $scope.brands = Brand.query();
            $scope.sizes = Size.query();
            $scope.suitParts = SuitPart.query();
        }])
    .controller('adherentListCtrl', ['$scope' ,'Adherent', 'adminHelperService', function($scope, Adherent, adminHelperService) {
        $scope.$parent.location = 'Liste des adhérents';
        $scope.$parent.title = 'Administration des Adhérents';
        $scope.$parent.titleSmall = '';
        adminHelperService.init($scope, Adherent,function() { return new Adherent()});
    }])
    .controller('divingEventListCtrl', ['$scope' ,'DivingEvent', 'Adherent', 'BillingType', 'adminHelperService',
        function($scope, DivingEvent, Adherent, BillingType, adminHelperService) {
            $scope.$parent.location = 'Liste des sorties';
            $scope.$parent.title = 'Administration des sorties';
            $scope.$parent.titleSmall = '';
            adminHelperService.init($scope, DivingEvent, function() { return new DivingEvent()});

            $scope.adherents = Adherent.query();
            $scope.billingTypes = BillingType.query();

            $scope.$watch('newItem.date', function(v){ // using the example model from the datepicker docs
                if( v != undefined && !(v instanceof Date)) {
                    try {
                        $scope.newItem.date= new Date(v);
                    } catch (e) {}
                }
            });
        }])
    .controller('divingEventEditCtrl', ['$scope' ,'$routeParams' ,'$filter', 'DivingEvent', 'RentalRecord', 'Adherent', 'Jacket', 'Regulator',
        'Tank' , 'Payment', 'Suit', '$http','adminHelperService',
        function($scope, $routeParams, $filter, DivingEvent, RentalRecord, Adherent, Jacket, Regulator,
             Tank, Payment, Suit,$http, adminHelperService) {

            //Load item
            $scope.reload = function() {
                $scope.dEvent = DivingEvent.get({'itemId' : $scope.dEventId}, function() {

                    //Update location
                    $scope.$parent.location = 'Sortie >> ' + $scope.dEvent.place;
                    $scope.$parent.title = 'Sortie: ' + $scope.dEvent.place ;
                    $scope.$parent.titleSmall = 'le: ' + $filter('date')($scope.dEvent.date,'dd/MM/yyyy');

                    $scope.jackets =   Jacket.query();
                    $scope.regulators = Regulator.query();
                    $scope.tanks = Tank.query();
                    $scope.payments = Payment.query();
                    $scope.suits = Suit.query();

                    $scope.createButtonCaption = "Créer";
                    $scope.jacket = null;
                    $scope.tank = null;
                    $scope.regulator = null;
                    $scope.overalls = null;
                    $scope.coat = null;
                    $scope.full = null;
                    $scope.renterId = null;

                });
            };

            $scope.paid = function(payment,rentId) {
                $http({method: 'PUT', url: '/api/rentalRecord/paid/'+ rentId, params: {'payment' : payment}}).
                    success(function(data, status, headers, config) {
                        $scope.reload();
                    });
            };

            $scope.addEquipment = function (equipmentId) {
                $scope.newItem.$addToDivingEvent( {'dEventId':$scope.dEventId
                        ,'equipmentId': equipmentId , 'renterId' :  $scope.renterId},
                    function() {
                        $scope.waitingCalls--;
                        console.debug($scope.waitingCalls);
                        if( $scope.waitingCalls == 0) {
                            $scope.reload();
                        }
                    });
            };
            $scope.addRentalRecord = function() {

                var equipmentList = new Array();
                if ($scope.jacket != null) {
                    equipmentList.push($scope.jacket.reference);
                }
                if ($scope.regulator != null) {
                    equipmentList.push($scope.regulator.reference);
                }
                if ($scope.tank != null) {
                    equipmentList.push($scope.tank.reference);
                }
                if ($scope.overalls != null) {
                    equipmentList.push($scope.overalls.reference);
                }
                if ($scope.coat != null) {
                    equipmentList.push($scope.coat.reference);
                }
                if ($scope.full != null) {
                    equipmentList.push($scope.full.reference);
                }
                $scope.waitingCalls =  equipmentList.length;
                equipmentList.forEach(function (element,index) {
                    $scope.addEquipment(element);
                })  ;


            };

            $scope.remove = function(id) {
                RentalRecord.remove({'itemId': id}, function() {
                    $scope.reload();
                });
            };

            $scope.edit = function(id) {
                $scope.newItem =  RentalRecord.get({'itemId': id}, function() {
                    if($scope.newItem.equipments != undefined) {
                        for(var i=0;i<$scope.newItem.equipments.length;i++) {
                            var equip = $scope.newItem.equipments[i];
                            if(equip.type == 'Tank') {
                                $scope.tank = equip;
                            }
                            else if(equip.type == 'Jacket') {
                                $scope.jacket = equip;
                            }
                            else if(equip.type == 'Regulator') {
                                $scope.regulator = equip;
                            }
                            else if(equip.type == 'Suit') {
                                if(equip.suitPart == 'overalls') {
                                    $scope.overalls = equip;
                                }
                                else if(equip.suitPart == 'coat') {
                                    $scope.coat = equip;
                                }
                                else if(equip.suitPart == 'full') {
                                    $scope.full = equip;
                                }
                            }
                        }
                        $scope.newItem.equipments=null;
                    }
                });
                $scope.createButtonCaption = "Modifier";

            }

            //Store dEvent Id
            $scope.dEventId = $routeParams.dEventId;

            $scope.adherents = Adherent.query();

            $scope.newItem = new RentalRecord();
            $scope.reload();

        }])
    /*
     * Debut des ctrl pour le détail des équipements
     */
    .controller('stabDisplayCtrl', ['$scope' ,'displayHelperService' , 'Jacket',
        function($scope, displayHelperService, Jacket) {
            displayHelperService.init($scope, Jacket, "Détail de la stab ");
    }])
    .controller('regulatorDisplayCtrl', ['$scope' ,'displayHelperService' , 'Regulator',
        function($scope, displayHelperService, Regulator) {
            displayHelperService.init($scope, Regulator, "Détail du détendeur ");
    }])
;
