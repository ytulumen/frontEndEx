package com.yasin.springmvc.service;

import com.yasin.model.UserRoles;

import java.util.List;

public interface UserRolesService {


    UserRoles findById(long id) ;

    UserRoles findByName(String name);

    public void saveUserRoles(UserRoles userRoles);

    void deleteUserRolesById(long id);

    List<UserRoles> findAllUserRoles();

    void deleteAllUserRoles();

    public boolean isUserRolesExist(UserRoles userRoles);

    public void findAll();
}