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
import com.yasin.springmvc.util.NetworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import sun.nio.ch.Net;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider<T>
        implements AuthenticationProvider {
    private NetworkUtil networkUtil = new NetworkUtil();

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        String url = "/oauth/token?grant_type=password&username="+
                name + "&password=" + password;
        StringBuffer response = new StringBuffer(networkUtil.networkService(url,"POST", ""));
        System.out.println(response.toString());
        String res = response.substring(response.indexOf("access_token")+15, response.indexOf("\"", response.indexOf("access_token")+16));
        System.out.println("result " + res);

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


