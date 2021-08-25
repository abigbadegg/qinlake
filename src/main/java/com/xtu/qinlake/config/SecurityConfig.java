package com.xtu.qinlake.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.xtu.qinlake.service.AuthService;

import static com.xtu.qinlake.scheduler.AuthInfoSchduler.authInfo;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthService authService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //登录页
        http.formLogin().loginProcessingUrl("/user/login")
                .and()
                .csrf().disable()
                .cors().disable();

        //配置请求对应的允许访问角色
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        registry.antMatchers("/user/login").permitAll();
        authInfo.forEach(auth -> {
            //-1表示允许所有人访问,0表示登陆后访问
            if ("-1".equals(auth.getLevel())) {
                registry.antMatchers(auth.getMenu()).permitAll();
            } else if ("0".equals(auth.getLevel())) {
                registry.antMatchers(auth.getMenu()).fullyAuthenticated();
            } else {
                registry.antMatchers(auth.getMenu()).hasAnyAuthority(auth.getLevel());
            }

        });
        registry.anyRequest().authenticated();

    }

    @Bean
    public PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

}
