package com.yasin.springmvc.security;

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
    private UserRolesServiceView userRolesServiceView;

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

        List<GrantedAuthority> list = new ArrayList();
/*        list.add(new SimpleGrantedAuthority("USER_ADMIN"));
        list.add(new SimpleGrantedAuthority("USER"));
        list.add(new SimpleGrantedAuthority("ROLE"));
        list.add(new SimpleGrantedAuthority("ADMIN"));*/
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return new UsernamePasswordAuthenticationToken(name, password, list);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}


