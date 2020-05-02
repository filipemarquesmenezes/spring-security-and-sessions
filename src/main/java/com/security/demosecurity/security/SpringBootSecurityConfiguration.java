package com.security.demosecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SpringBootSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] ADMIN_ROLE = {"admin", "user"};
    private static final String[] USER_ROLE = {"user"};

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles(ADMIN_ROLE)
                .and()
                .withUser("user").password(passwordEncoder().encode("user")).roles(USER_ROLE);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.formLogin();
//        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole(ADMIN_ROLE)
                .antMatchers("/user/**").hasAnyRole(USER_ROLE)
                .anyRequest().authenticated();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}