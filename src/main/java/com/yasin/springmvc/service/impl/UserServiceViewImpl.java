package com.yasin.springmvc.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yasin.model.*;
import com.yasin.springmvc.service.UserServiceView;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository(value="userServiceView")

public class UserServiceViewImpl extends AbstractServiceImpl<User> implements UserServiceView {

    public UserServiceViewImpl() {
        super.setUrl("/rest/user");
        super.setClazz(User.class);
        type = new TypeToken<ArrayList<User>>() {}.getType();

    }
    public User findByName(String name) {
        return new Gson().fromJson(networkUtil.networkService("/rest/user" + "/name=" + name ,"GET",""), type);

    }


}