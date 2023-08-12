package com.example.wantedpreonboardingbackend.common.arguemntresolver;

import com.example.wantedpreonboardingbackend.common.annotation.Auth;
import com.example.wantedpreonboardingbackend.common.exception.UnauthorizedException;
import com.example.wantedpreonboardingbackend.common.utils.JwtContext;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String jwt = webRequest.getHeader("Authentication");
        if (!StringUtils.hasText(jwt)) {
            throw new UnauthorizedException();
        }

        String sub = JwtContext.getSubject(jwt);
        return Long.valueOf(sub);
    }
}
