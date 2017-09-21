'use strict';

angular.module('myApp').factory('RoleService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8081/role/';

    var factory = {
        fetchAllRoles: fetchAllRoles,
        createRole: createRole,
        updateRole:updateRole,
        deleteRole:deleteRole
    };

    return factory;

    function fetchAllRoles() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching Roles');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function createRole(role) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, role)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while creating Role');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }


    function updateRole(role, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+id, role)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while updating Role');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteRole(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while deleting Role');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}]);