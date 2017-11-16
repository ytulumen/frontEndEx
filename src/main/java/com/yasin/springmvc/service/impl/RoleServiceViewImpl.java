package com.yasin.springmvc.service.impl;

import com.google.gson.reflect.TypeToken;
import com.yasin.model.*;
import com.yasin.springmvc.service.RoleServiceView;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Repository(value="roleServiceView")
public class RoleServiceViewImpl extends AbstractServiceImpl<Roles> implements RoleServiceView {

    public RoleServiceViewImpl() {
        super.setUrl("/rest/role");
        super.setClazz(Roles.class);
        type = new TypeToken<ArrayList<Roles>>() {}.getType();

    }
}