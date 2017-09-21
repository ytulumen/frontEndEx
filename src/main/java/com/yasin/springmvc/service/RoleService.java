package com.yasin.springmvc.service;

import java.util.List;

import com.yasin.model.Roles;


public interface RoleService {

    Roles findById(long id) ;

    Roles findByName(String name);

    void saveRoles(Roles roles);

    void updateRoles(Roles roles);

    void deleteRolesById(long id);

    List<Roles> findAllRoles();

    void deleteAllRoles();

    public boolean isRolesExist(Roles roles);

    public void findAll();
}