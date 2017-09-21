'use strict';

angular.module('myApp').controller('UserRoleController', ['$scope', 'UserRoleService', function($scope, UserRoleService) {
    var self = this;
    //self.userrole={id:null,name:'',create:'',update:'', username:'',rolename:''};
    self.name = '';
    self.username = '';
    self.rolename = '';

    self.userroles=[];

    self.submit = submit;
    self.remove = remove;
    self.reset = reset;


    fetchAllUserRoles();

    function fetchAllUserRoles(){
        UserRoleService.fetchAllUserRoles()
            .then(
                function(d) {
                    self.userroles = d;
                },
                function(errResponse){
                    console.error('Error while fetching UserRoles');
                }
            );
    }

    function createUserRole(name, username, rolename){
        console.log(name, username, rolename);
        UserRoleService.createUserRole(name, username, rolename)
            .then(
                fetchAllUserRoles,
                function(errResponse){
                    console.error('Error while creating UserRole');
                }
            );
    }

    function deleteUserRole(id){
        UserRoleService.deleteUserRole(id)
            .then(
                fetchAllUserRoles,
                function(errResponse){
                    console.error('Error while deleting UserRole');
                }
            );
    }

    function submit() {

        console.log('Saving New UserRole', self.userrole);
        createUserRole(self.name, self.username, self.rolename);

        reset();
    }

    function remove(id){
        console.log('id to be deleted', id);

        deleteUserRole(id);
    }

    function reset(){
        self.userrole={id:null,name:'',create:'',update:'', user:'',role:''};
        $scope.myForm.$setPristine(); //reset Form
    }

}]);