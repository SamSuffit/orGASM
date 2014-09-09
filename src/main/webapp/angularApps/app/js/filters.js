'use strict';

/* Filters */

angular.module('myApp.filters', [])
  .filter('interpolate', ['version', function(version) {
    return function(text) {
      return String(text).replace(/\%VERSION\%/mg, version);
    }
  }])
  .filter('notInRental', [function() {
      return function(input,_array,newItem) {
        if(input == undefined ) return undefined;
        if(_array == undefined ) return input;

        var _ret = new Array();

        input.forEach(function(entry) {
            var present=false;
            for(var i=0 ; i < _array.length; i++) {
               if(entry.id == _array[i].renter.id ) {
                   //If present, check that we are not editing its rent
                   //
                   if(!(newItem!=undefined && newItem.renter != undefined && newItem.renter.id == entry.id)) {
                       present=true;
                   }
                   break;
               }
            }
            if(!present) {
                _ret.push(entry);
            }
        });
        return _ret;
      }
  }])
  .filter('itemNotRented', [function() {
     return function(input,editItem) {
		if(input == undefined ) return undefined;
        var _ret = new Array();
        input.forEach(function(entry) {
            if(!entry.rented || (editItem != undefined && editItem.reference && entry.reference)) {
              _ret.push(entry);
            }
        });
        return _ret;
     }
  }])
  .filter('isSuitPart', [function() {
     return function(input,part) {
		if(input == undefined ) return undefined;
        var _ret = new Array();
        input.forEach(function(entry) {
            if(entry.suitPart == part) {
              _ret.push(entry);
            }
        });
        return _ret;
     }
  }])
  ;
