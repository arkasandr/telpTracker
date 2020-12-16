package ru.arkaleks.telptracker.security;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.*;
import ru.arkaleks.telptracker.controllers.impl.CurrentUserService;
import ru.arkaleks.telptracker.service.UserService;

import javax.sql.DataSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String KEY = "telptracker";

    private final UserService userService;

    private final TrackerAuthenticationProvider sagaProvider;

    private final DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(sagaProvider)
                .authenticationProvider(new RememberMeAuthenticationProvider(KEY));
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/**/*.css")
                .antMatchers("/**/*.js")
                .antMatchers("/**/*.png")
                .antMatchers("/**/*.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .addFilterBefore(customLoginFilter(), RememberMeAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "/api/test/**", "/api/auth/**", "/login*", "/home",
                        "/registrationstart.html", "/registrationstart/**")
                .permitAll()
                .antMatchers("/register", "/api/employee/**", "/tasks/status/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/tasks/admin/**")
                .hasRole("ADMIN")
                .antMatchers("/tasks/user/**")
                .hasRole("USER")
                .antMatchers("/resources/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/applogout")
                .logoutSuccessUrl("/index.html")
                .deleteCookies("remember-me-saga")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(myHttp403ForbiddenEntryPoint())
                .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices());
    }

    @Bean
    PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint myHttp403ForbiddenEntryPoint() {
        return new MyHttp403ForbiddenEntryPoint();
    }


    @Bean
    public AbstractRememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices =
                new TokenBasedRememberMeServices(KEY, userService);
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setCookieName("remember-me-saga");
        rememberMeServices.setTokenValiditySeconds(1209600);
        return rememberMeServices;
    }

    @Bean
    public CustomLoginFilter customLoginFilter() throws Exception {
        CustomLoginFilter filter = new CustomLoginFilter("/api/auth/signin");
        filter.setAuthenticationManager(authenticationManager());
        filter.setAllowSessionCreation(false);
        filter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler(rememberMeServices()));
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


