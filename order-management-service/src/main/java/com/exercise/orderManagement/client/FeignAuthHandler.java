package com.exercise.orderManagement.client;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignAuthHandler implements RequestInterceptor{

	@Override
    public void apply(RequestTemplate template) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Object credentials = auth.getCredentials();

        String token = credentials.toString();

        template.header("Authorization", "Bearer " + token);
    }

}
