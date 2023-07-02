package com.ecommerce.auth.core.authenticate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1000,redisNamespace = "ecommerce_namespace")
public class SessionConfig {

}
