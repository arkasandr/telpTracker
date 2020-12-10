package ru.arkaleks.telptracker.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import ru.arkaleks.telptracker.controllers.impl.CurrentUserService;
import ru.arkaleks.telptracker.model.Employee;
import ru.arkaleks.telptracker.model.EmployeeRole;
import ru.arkaleks.telptracker.service.UserDetailsAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RememberMeServices rememberMeServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        rememberMeServices.loginSuccess(request, response, authentication);
        response.setContentType("application/json");
        String token  = response.getHeader("remember-me-saga");
        val writer = response.getWriter();

        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        List<EmployeeRole> list = new ArrayList<>();
        for(GrantedAuthority role : roles) {
            role.getAuthority();
            log.info("USER has role: " + role.getAuthority());
            list.add(new EmployeeRole(role.getAuthority()));
        }
        Employee employee = new Employee(authentication.getPrincipal().toString(), list);
      //  writer.write("{}");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(employee);
        log.info("Response after login: " + json);
        writer.write(json);
        writer.flush();
    }
}

