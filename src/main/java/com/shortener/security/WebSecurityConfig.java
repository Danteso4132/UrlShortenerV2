package com.shortener.security;

import com.shortener.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

       /* http
                .authorizeRequests()
                .antMatchers("/", "/home","/main").permitAll().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll().defaultSuccessUrl("/main")
                .and()
                .logout()
                .permitAll();


        */
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/login", "/logout").permitAll();
        http.authorizeRequests().antMatchers("/registration").not().fullyAuthenticated();

        http.authorizeRequests().antMatchers("/userPage").access("hasRole('ROLE_USER')");
        http.authorizeRequests().antMatchers("/adminPage").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/allPage","/recommendation", "/main").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')");

        http.authorizeRequests()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/main") .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        http.authorizeRequests()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error=true");
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}