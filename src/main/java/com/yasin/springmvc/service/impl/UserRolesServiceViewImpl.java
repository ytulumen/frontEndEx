package com.yasin.springmvc.service.impl;

import com.yasin.model.UserRoles;
import com.yasin.springmvc.service.UserRolesServiceView;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository(value="userRolesServiceView")

public class UserRolesServiceViewImpl extends AbstractServiceImpl<UserRoles> implements UserRolesServiceView {

    public UserRolesServiceViewImpl() {
        super.setUrl("/rest/userroles");
    }
}
