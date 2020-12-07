package ru.arkaleks.telptracker.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import ru.arkaleks.telptracker.model.Employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {

    public CustomLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpresponse)
            throws AuthenticationException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = mapper.readValue(httpRequest.getReader().lines()
                .collect(Collectors.joining()), Employee.class);
        Authentication auth = new UsernamePasswordAuthenticationToken(employee.getUsername(), employee.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        log.warn("Username is " + auth.getPrincipal());
        log.warn("password is " + auth.getCredentials());
        log.warn("Filter: URL called: " + httpRequest.getRequestURL().toString());
        return getAuthenticationManager().authenticate(auth);
    }
}


