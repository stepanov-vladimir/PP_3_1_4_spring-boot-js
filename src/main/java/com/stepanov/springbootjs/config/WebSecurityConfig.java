package com.stepanov.springbootjs.config;
import com.stepanov.springbootjs.service.MyUserDetailsService;
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
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private SuccessUserHandler successUserHandler;

    private MyUserDetailsService userDetailsService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, MyUserDetailsService userDetailsService) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

    //TODO disable next security
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll();
    }

    //TODO enable security
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                    .authorizeRequests()
//                    .antMatchers("/admin/**").hasAuthority("ADMIN")
//                    .antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
//                    .anyRequest().authenticated()
//                .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .usernameParameter("email")
//                    .successHandler(successUserHandler)
//                    .permitAll()
//                .and()
//                    .logout()
//                    .logoutSuccessUrl("/login")
//                    .permitAll();
//    }

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