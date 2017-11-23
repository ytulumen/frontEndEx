package com.yasin.springmvc.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yasin.model.*;
import com.yasin.springmvc.service.RoleServiceView;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;


@Repository(value="roleServiceView")
public class RoleServiceViewImpl extends AbstractServiceImpl<Roles> implements RoleServiceView {

    public RoleServiceViewImpl() {
        super.setUrl("/rest/role");
        super.setClazz(Roles.class);
        type = new TypeToken<ArrayList<Roles>>() {}.getType();

    }
    public Roles findByName(String name) {
        return new Gson().fromJson(networkUtil.networkService("/rest/role" + "/name=" + name ,"GET",""), type);
    }
}