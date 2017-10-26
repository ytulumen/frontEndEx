package com.yasin.springmvc.service.impl;

import com.yasin.model.*;
import com.yasin.springmvc.service.RoleServiceView;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository(value="roleServiceView")
public class RoleServiceViewImpl extends AbstractServiceImpl<Roles> implements RoleServiceView {

    public RoleServiceViewImpl() {
        super.setUrl("/rest/user");
    }
}