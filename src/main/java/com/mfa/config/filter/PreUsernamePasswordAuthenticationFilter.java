package com.mfa.config.filter;

import com.mfa.datas.dto.MfaDto;
import com.mfa.datas.entities.UserEntity;
import com.mfa.services.MfaService;
import com.mfa.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class PreUsernamePasswordAuthenticationFilter implements Filter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MfaService mfaService;

    public PreUsernamePasswordAuthenticationFilter(PasswordEncoder passwordEncoder, UserService userService, MfaService mfaService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.mfaService = mfaService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (httpServletRequest.getServletPath().equals("/prelogin") && httpServletRequest.getMethod().equals("POST")) {
            String username = httpServletRequest.getParameter("username");
            String password = httpServletRequest.getParameter("password");
            UserEntity userEntity = userService.getUser(username);

            if (Optional.ofNullable(userEntity).isPresent() && Optional.ofNullable(userEntity.getUsername()).isPresent()) {
                if (((BCryptPasswordEncoder)passwordEncoder).matches(password, userEntity.getPassword())) {
                    httpServletRequest.getSession().setAttribute("username", username);
                    httpServletRequest.getSession().setAttribute("password", password);
                    MfaDto mfaDto = mfaService.getMfa(username);
                    if (Optional.ofNullable(mfaDto).isPresent() && Optional.ofNullable(mfaDto.getSecretKey()).isPresent()) {
                        httpServletRequest.getSession().setAttribute("mfa", true);
                        httpServletResponse.sendRedirect("/mfator");
                    } else {
                        httpServletRequest.getSession().setAttribute("mfa", false);
                        httpServletResponse.sendRedirect("/purelogin");
                    }
                } else {
                    httpServletResponse.sendRedirect("/logout");
                }
            } else {
                httpServletResponse.sendRedirect("/logout");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

}
