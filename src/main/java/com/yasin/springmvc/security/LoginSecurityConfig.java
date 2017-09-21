package com.yasin.springmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.inMemoryAuthentication()
                .withUser("journaldev")
                .password("jd@123")
                .authorities("ROLE_USER");
        authenticationMgr.inMemoryAuthentication()
                .withUser("yasin")
                .password("tlmn")
                .authorities("USERADMIN");
        authenticationMgr.inMemoryAuthentication()
                .withUser("role")
                .password("tlmn")
                .authorities("ROLEADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*        http.authorizeRequests()
                .antMatchers("/index").access("hasRole('ROLE_USER')")
                .and()
                .formLogin().loginPage("/login.html")
                .defaultSuccessUrl("/userpage")
                .failureUrl("/denied")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/logout");
         http.authorizeRequests().antMatchers("/index")
                 .access("hasRole('ROLE_USER')")
                 .and().formLogin().loginPage("/login.html")
                 .defaultSuccessUrl("/userpage")
                 .failureUrl("/denied")
                 .usernameParameter("username").passwordParameter("password")
                 .and().authorizeRequests().antMatchers(HttpMethod.POST, "/userpage")
                 .permitAll()
                 .and().logout().logoutSuccessUrl("/logout");*/
/*
        http.authorizeRequests()
                .antMatchers("/index").access("hasRole('ROLE_USER')")
                .and()
                .formLogin().loginPage("/login.html")
                .defaultSuccessUrl("/userpage")
                .failureUrl("/denied")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .authorizeRequests()
                .antMatchers("/userpage").access("hasRole('ROLE_USER')")
                .and()
                .formLogin().loginPage("/login.html")
                .defaultSuccessUrl("/userpage")
                .failureUrl("/denied")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .authorizeRequests()
                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/menu").hasRole("USER")
                .antMatchers("/board").hasRole("USER")
                .and()
                .authorizeRequests()
                .antMatchers("/rest/**").authenticated()
                .and()
                .logout().logoutSuccessUrl("/logout");
*/

        http.authorizeRequests()
                .antMatchers("/userpage").hasAuthority("USERADMIN");
        http.authorizeRequests()
                .antMatchers("/rolepage").hasAuthority("ROLEADMIN");
        http.authorizeRequests()
                .antMatchers("/index").hasAuthority("ROLE_USER")
                .and()
                .formLogin().loginPage("/login.html")
                .defaultSuccessUrl("/userpage")
                .failureUrl("/denied")
                .usernameParameter("username").passwordParameter("password");
        http.csrf().disable();
    }
}
