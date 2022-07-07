package com.stepanov.springbootjs.config;

//import com.stepanov.springbootjs.service.MyUserDetailsService;
import com.stepanov.springbootjs.service.MyUserDetailsService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//TODO enable WebSecurity
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;

    private final MyUserDetailsService userDetailsService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, MyUserDetailsService userDetailsService) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

//    //TODO disable next security
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                    .cors()
//                .and()
//                    .csrf().disable()
//                    .authorizeRequests()
//                    .antMatchers("/")
//                    .permitAll();
//    }

//    TODO enable security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                     .cors()
                .and()
                     .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/api/users/**").hasAuthority("ADMIN")
                    .antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .usernameParameter("email")
                    .successHandler(successUserHandler)
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);

        return authenticationProvider;
    }
}