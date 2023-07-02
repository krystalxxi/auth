package com.ecommerce.auth.api.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserVo implements Serializable {
    private String username;
    private Set<GrantedAuthority> authorities;
}
