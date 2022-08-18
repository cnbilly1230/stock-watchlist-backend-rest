package com.bootcamp.java.watchlistexercise.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/signup").permitAll()
                .anyRequest().authenticated();


        http.oauth2ResourceServer().jwt();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**");
    }
}
