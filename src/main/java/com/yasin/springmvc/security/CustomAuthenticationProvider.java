package com.yasin.springmvc.security;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yasin.model.Roles;
import com.yasin.model.User;
import com.yasin.model.UserRoles;
import com.yasin.springmvc.service.AbstractService;
import com.yasin.springmvc.service.RoleServiceView;
import com.yasin.springmvc.service.UserRolesServiceView;
import com.yasin.springmvc.service.impl.AbstractServiceImpl;
import com.yasin.springmvc.service.impl.UserServiceViewImpl;
import com.yasin.springmvc.util.NetworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider<T>
        implements AuthenticationProvider {
    @Autowired
    private NetworkUtil networkUtil;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        String token = networkUtil.getByName(name, password);

        String rolesString = networkUtil.networkService("/rest/userrole", "GET","");
        List <UserRoles> items = new Gson().fromJson(rolesString.toString(), new TypeToken<ArrayList<UserRoles>>() {}.getType());

        List<GrantedAuthority> list = new ArrayList();
        for (int i = 0; i < items.size(); i++) {
            if(name.equals(items.get(i).getUser().getName()))
                list.add(new SimpleGrantedAuthority(items.get(i).getRole().getName()));
        }

        return new UsernamePasswordAuthenticationToken(name, password, list);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}


