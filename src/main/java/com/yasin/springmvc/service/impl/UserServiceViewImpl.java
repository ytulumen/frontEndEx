package com.yasin.springmvc.service.impl;

import com.yasin.model.*;

import com.yasin.springmvc.service.UserServiceView;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Repository(value="userServiceView")

public class UserServiceViewImpl extends AbstractServiceImpl<User> implements UserServiceView {

    public UserServiceViewImpl() {
        super.setUrl("/rest/user");
    }
}