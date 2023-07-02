package com.ecommerce.auth.core.authenticate.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import java.io.ObjectInputFilter;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    public  SecurityInitializer(){
        super(SessionConfig.class, ObjectInputFilter.Config.class);
    }
}
