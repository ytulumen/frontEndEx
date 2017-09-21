'use strict';

angular.module('myApp').controller('RoleController', ['$scope', 'RoleService', function($scope, RoleService) {
    var self = this;
    self.role={id:null,name:'',create:'',update:'',password:''};
    self.roles=[];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;


    fetchAllRoles();

    function fetchAllRoles(){
        RoleService.fetchAllRoles()
            .then(
                function(d) {
                    self.roles = d;
                },
                function(errResponse){
                    console.error('Error while fetching Roles');
                }
            );
    }

    function createRole(role){
        RoleService.createRole(role)
            .then(
                fetchAllRoles,
                function(errResponse){
                    console.error('Error while creating Role');
                }
            );
    }

    function updateRole(role, id){
        RoleService.updateRole(role, id)
            .then(
                fetchAllRoles,
                function(errResponse){
                    console.error('Error while updating Role');
                }
            );
    }

    function deleteRole(id){
        RoleService.deleteRole(id)
            .then(
                fetchAllRoles,
                function(errResponse){
                    console.error('Error while deleting Role');
                }
            );
    }

    function submit() {

        if(self.role.id===null){
            console.log('Saving New Role', self.role);
            createRole(self.role);
        }else{
            updateRole(self.role, self.role.id);
            console.log('Role updated with id ', self.role.id);
        }
        reset();
    }

    function edit(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.roles.length; i++){
            if(self.roles[i].id === id) {
                self.role = angular.copy(self.roles[i]);
                break;
            }
        }
    }

    function remove(id){
        console.log('id to be deleted', id);
        if(self.role.id === id) {//clean form if the role to be deleted is shown there.
            reset();
        }
        deleteRole(id);
    }


    function reset(){
        self.role={id:null,name:'',create:'',update:'',password:''};
        $scope.myForm.$setPristine(); //reset Form
    }

}]);