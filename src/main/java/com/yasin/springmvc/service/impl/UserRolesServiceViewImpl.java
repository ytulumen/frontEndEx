package com.yasin.springmvc.service.impl;

import com.google.gson.reflect.TypeToken;
import com.yasin.model.UserRoles;
import com.yasin.springmvc.service.UserRolesServiceView;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Repository(value="userRolesServiceView")

public class UserRolesServiceViewImpl extends AbstractServiceImpl<UserRoles> implements UserRolesServiceView {

    public UserRolesServiceViewImpl() {
        super.setUrl("/rest/userrole");
        super.setClazz(UserRoles.class);
        type = new TypeToken<ArrayList<UserRoles>>() {}.getType();
    }

}
