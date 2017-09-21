package com.yasin.springmvc.controller;

import java.util.List;

import com.yasin.springmvc.service.RoleService;
import com.yasin.springmvc.service.UserRolesService;
import com.yasin.model.Roles;
import com.yasin.model.User;
import com.yasin.model.UserRoles;
import com.yasin.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class HelloWorldRestController {

    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work

    @Autowired
    RoleService roleService;

    @Autowired
    UserRolesService userroleService;

    //-------------------Retrieve All Users--------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() throws Exception{
        userService.findAll();
        List<User> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }



    //-------------------Retrieve Single User--------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }



    //-------------------Create a User--------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.POST, consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody User user,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getName());

        if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }



    //------------------- Update a User --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("Updating User " + id);

        User currentUser = userService.findById(id);

        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        currentUser.setName(user.getName());
        currentUser.setCreate(null);
        currentUser.setUpdate(null);
        currentUser.setPassword(user.getPassword());
        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }



    //------------------- Delete a User --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);

        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }



    //------------------- Delete All Users --------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting All Users");

        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }




    //-------------------Retrieve All Roless--------------------------------------------------------

    @RequestMapping(value = "/role/", method = RequestMethod.GET)
    public ResponseEntity<List<Roles>> listAllRoles() throws Exception{
        roleService.findAll();
        List<Roles> roles = roleService.findAllRoles();
        if(roles.isEmpty()){
            return new ResponseEntity<List<Roles>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Roles>>(roles, HttpStatus.OK);
    }



    //-------------------Retrieve Single Roles--------------------------------------------------------

    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Roles> getRoles(@PathVariable("id") long id) {
        System.out.println("Fetching Roles with id " + id);
        Roles role = roleService.findById(id);
        if (role == null) {
            System.out.println("Roles with id " + id + " not found");
            return new ResponseEntity<Roles>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Roles>(role, HttpStatus.OK);
    }



    //-------------------Create a Roles--------------------------------------------------------

    @RequestMapping(value = "/role/", method = RequestMethod.POST, consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createRoles(@RequestBody Roles role, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Roles " + role.getName());

        if (roleService.isRolesExist(role)) {
            System.out.println("A Roles with name " + role.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        roleService.saveRoles(role);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/role/{id}").buildAndExpand(role.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }



    //------------------- Update a Roles --------------------------------------------------------

    @RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Roles> updateRoles(@PathVariable("id") long id, @RequestBody Roles role) {
        System.out.println("Updating Roles " + id);

        Roles currentRoles = roleService.findById(id);

        if (currentRoles==null) {
            System.out.println("Roles with id " + id + " not found");
            return new ResponseEntity<Roles>(HttpStatus.NOT_FOUND);
        }

        currentRoles.setName(role.getName());
        currentRoles.setCreate(null);
        currentRoles.setUpdate(null);
        currentRoles.setPassword(role.getPassword());

        roleService.updateRoles(currentRoles);
        return new ResponseEntity<Roles>(currentRoles, HttpStatus.OK);
    }



    //------------------- Delete a Roles --------------------------------------------------------

    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Roles> deleteRoles(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Roles with id " + id);

        Roles role = roleService.findById(id);
        if (role == null) {
            System.out.println("Unable to delete. Roles with id " + id + " not found");
            return new ResponseEntity<Roles>(HttpStatus.NOT_FOUND);
        }

        roleService.deleteRolesById(id);
        return new ResponseEntity<Roles>(HttpStatus.NO_CONTENT);
    }



    //------------------- Delete All Roless --------------------------------------------------------

    @RequestMapping(value = "/role/", method = RequestMethod.DELETE)
    public ResponseEntity<Roles> deleteAllRoles() {
        System.out.println("Deleting All Roless");

        roleService.deleteAllRoles();
        return new ResponseEntity<Roles>(HttpStatus.NO_CONTENT);
    }











    @RequestMapping(value = "/userrole/", method = RequestMethod.GET)
    public ResponseEntity<List<UserRoles>> listAllUserRoles() throws Exception{
        userroleService.findAll();
        List<UserRoles> userRoles = userroleService.findAllUserRoles();
        if(userRoles.isEmpty()){
            return new ResponseEntity<List<UserRoles>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UserRoles>>(userRoles, HttpStatus.OK);
    }



    //-------------------Retrieve Single UserRoles--------------------------------------------------------

    @RequestMapping(value = "/userrole/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRoles> getUserRoles(@PathVariable("id") long id) {
        System.out.println("Fetching UserRoles with id " + id);
        UserRoles userrole = userroleService.findById(id);
        if (userrole == null) {
            System.out.println("UserRoles with id " + id + " not found");
            return new ResponseEntity<UserRoles>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserRoles>(userrole, HttpStatus.OK);
    }



    //-------------------Create a UserRoles--------------------------------------------------------

    @RequestMapping(value = "/userrole/{name}/{username}/{rolename}/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUserRoles(@PathVariable("name") String name, @PathVariable("username") String username,
                                                @PathVariable("rolename") String rolename, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating UserRoles " + name + " " + username + " " + rolename);

        UserRoles userRoles = new UserRoles();
        userRoles.setPassword("123456");
        userRoles.setUser(userService.findByName(username));
        userRoles.setRole(roleService.findByName(rolename));
        userRoles.setName(name);
        userRoles.getUser().setCreate(null);
        userRoles.getUser().setUpdate(null);
        userRoles.getRole().setCreate(null);
        userRoles.getRole().setUpdate(null);

        /*        if (userroleService.isUserRolesExist(userrole)) {
            System.out.println("A UserRoles with name " + userrole.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }*/

        userroleService.saveUserRoles(userRoles);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/userrole/{id}").buildAndExpand(userroleService.findByName(name).getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }



    //------------------- Delete a UserRoles --------------------------------------------------------

    @RequestMapping(value = "/userrole/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UserRoles> deleteUserRoles(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting UserRoles with id " + id);

        UserRoles userrole = userroleService.findById(id);
        if (userrole == null) {
            System.out.println("Unable to delete. UserRoles with id " + id + " not found");
            return new ResponseEntity<UserRoles>(HttpStatus.NOT_FOUND);
        }

        userroleService.deleteUserRolesById(id);
        return new ResponseEntity<UserRoles>(HttpStatus.NO_CONTENT);
    }



    //------------------- Delete All UserRoless --------------------------------------------------------

    @RequestMapping(value = "/userrole/", method = RequestMethod.DELETE)
    public ResponseEntity<UserRoles> deleteAllUserRoles() {
        System.out.println("Deleting All UserRoless");

        userroleService.deleteAllUserRoles();
        return new ResponseEntity<UserRoles>(HttpStatus.NO_CONTENT);
    }
}