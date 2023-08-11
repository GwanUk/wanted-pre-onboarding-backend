package com.example.wantedpreonboardingbackend.common.interceptor;

import com.example.wantedpreonboardingbackend.common.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authenticationToken = request.getHeader("Authentication");
        if (true) {
            return true;
        }

        throw new UnauthorizedException();
    }
}
