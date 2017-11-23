<%--
  Created by IntelliJ IDEA.
  User: yasintulumen
  Date: 02/08/2017
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AngularJS $http Example</title>
    <style>
        .name.ng-valid {
            background-color: lightgreen;
        }
        .name.ng-dirty.ng-invalid-required {
            background-color: red;
        }
        .name.ng-dirty.ng-invalid-minlength {
            background-color: yellow;
        }
        .name.ng-dirty.ng-invalid-maxlength {
            background-color: yellow;
        }
        .username.ng-valid {
            background-color: lightgreen;
        }
        .username.ng-dirty.ng-invalid-required {
            background-color: red;
        }
        .username.ng-dirty.ng-invalid-minlength {
            background-color: yellow;
        }
        .username.ng-dirty.ng-invalid-maxlength {
            background-color: yellow;
        }
        .rolename.ng-valid {
            background-color: lightgreen;
        }
        .rolename.ng-dirty.ng-invalid-required {
            background-color: red;
        }
        .rolename.ng-dirty.ng-invalid-minlength {
            background-color: yellow;
        }
        .rolename.ng-dirty.ng-invalid-maxlength {
            background-color: yellow;
        }
        /*        .email.ng-valid {
                    background-color: lightgreen;
                }
                .email.ng-dirty.ng-invalid-required {
                    background-color: red;
                }
                .email.ng-dirty.ng-invalid-email {
                    background-color: yellow;
                }*/

    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body ng-app="myApp" class="ng-cloak">
<div class="generic-container" ng-controller="UserRoleController as ctrl">
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">User Role Registration Form </span></div>
        <div class="formcontainer">
            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="name">name</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.name" id="name" class="name form-control input-sm" placeholder="Enter your name" required ng-minlength="3" required ng-maxlength="30"/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.name.$error.required">This is a required field</span>
                                <span ng-show="myForm.name.$error.minlength">Minimum length required is 3</span>
                                <span ng-show="myForm.name.$error.maxlength">Maximum length required is 30</span>
                                <span ng-show="myForm.name.$invalid">This field is invalid </span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="username">username</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.username" id="username" class="username form-control input-sm" placeholder="Enter your username" required ng-minlength="3" required ng-maxlength="30"/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.username.$error.required">This is a required field</span>
                                <span ng-show="myForm.username.$error.minlength">Minimum length required is 3</span>
                                <span ng-show="myForm.username.$error.maxlength">Maximum length required is 30</span>
                                <span ng-show="myForm.username.$invalid">This field is invalid </span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="rolename">rolename</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.rolename" id="rolename" class="rolename form-control input-sm" placeholder="Enter your rolename" required ng-minlength="3" required ng-maxlength="30"/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.rolename.$error.required">This is a required field</span>
                                <span ng-show="myForm.rolename.$error.minlength">Minimum length required is 3</span>
                                <span ng-show="myForm.rolename.$error.maxlength">Maximum length required is 30</span>
                                <span ng-show="myForm.rolename.$invalid">This field is invalid </span>
                            </div>
                        </div>
                    </div>
                </div>

                <%--                <div class="row">
                                    <div class="form-group col-md-12">
                                        <label class="col-md-2 control-lable" for="address">Address</label>
                                        <div class="col-md-7">
                                            <input type="text" ng-model="ctrl.user.address" id="address" class="form-control input-sm" placeholder="Enter your Address. [This field is validation free]"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <label class="col-md-2 control-lable" for="email">Email</label>
                                        <div class="col-md-7">
                                            <input type="email" ng-model="ctrl.user.email" id="email" class="email form-control input-sm" placeholder="Enter your Email" required/>
                                            <div class="has-error" ng-show="myForm.$dirty">
                                                <span ng-show="myForm.email.$error.required">This is a required field</span>
                                                <span ng-show="myForm.email.$invalid">This field is invalid </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                --%>
                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit" id="addButton"  value="{{'Add'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of User Roles</span></div>
        <div class="tablecontainer">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>ID.</th>
                    <th>Name</th>
                    <th>Create Date</th>
                    <th>Update Date</th>
                    <th>User Name</th>
                    <th>Role Name</th>
                    <th width="20%"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="u in ctrl.userroles">
                    <td><span ng-bind="u.id"></span></td>
                    <td><span ng-bind="u.name"></span></td>
                    <td><span>{{u.create | date: 'dd.MM.yyyy HH:mm'}}</span></td>
                    <td><span>{{u.update | date: 'dd.MM.yyyy HH:mm'}}</span></td>
                    <td><span ng-bind="u.user.name"></span></td>
                    <td><span ng-bind="u.role.name"></span></td>
                    <td>
                        <button type="button" ng-click="ctrl.remove(u.id)" class="btn btn-danger custom-width">Remove</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/static/js/app.js' />"></script>
<script src="<c:url value='/static/js/service/userrole_service.js' />"></script>
<script src="<c:url value='/static/js/controller/userrole_controller.js' />"></script>
</body>
</html>