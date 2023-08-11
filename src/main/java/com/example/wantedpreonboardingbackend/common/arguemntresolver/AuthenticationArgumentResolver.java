package com.example.wantedpreonboardingbackend.common.arguemntresolver;

import com.example.wantedpreonboardingbackend.common.annotation.Auth;
import com.example.wantedpreonboardingbackend.common.exception.UnauthorizedException;
import com.example.wantedpreonboardingbackend.common.utils.KeyHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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
        String jws = webRequest.getHeader("Authentication");
        if (!StringUtils.hasText(jws)) {
            throw new UnauthorizedException();
        }

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(KeyHolder.KEY)
                    .build()
                    .parseClaimsJws(jws);

            String sub = claims.getBody().getSubject();

            return Long.valueOf(sub);

        } catch (JwtException e) {
            throw new UnauthorizedException(e);
        }
    }
}
