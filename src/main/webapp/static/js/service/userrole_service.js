'use strict';

angular.module('myApp').factory('UserRoleService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8081/userrole/';

    var factory = {
        fetchAllUserRoles: fetchAllUserRoles,
        createUserRole: createUserRole,
        deleteUserRole:deleteUserRole
    };

    return factory;

    function fetchAllUserRoles() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching UserRoles');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function createUserRole(name, username, rolename) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + name + '/' + username + '/' + rolename + '/')
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while creating UserRole');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteUserRole(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI + id + '/')
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while deleting UserRole');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}]);