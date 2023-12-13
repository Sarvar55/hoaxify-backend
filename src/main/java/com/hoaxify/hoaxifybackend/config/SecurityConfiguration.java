package com.hoaxify.hoaxifybackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserAuthService userAuthService;

    public SecurityConfiguration(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(new CustomAutenticatedEntryPoint());
        http.headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/api/1.0/users/{userName}").authenticated()
                .antMatchers(HttpMethod.POST, "/api/1.0/hoaxes").authenticated()
                .antMatchers(HttpMethod.POST, "/api/1.0/hoax-attacments").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/1.0/users/{userName}").authenticated()//burasi principalda userin olub olmadigini kontrol eder
                .and()
                .authorizeRequests().anyRequest().permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CustomTokenFilter tokenFilter() {
        return new CustomTokenFilter();
    }
}
