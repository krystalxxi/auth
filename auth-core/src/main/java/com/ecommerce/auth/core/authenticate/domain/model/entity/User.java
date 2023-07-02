package com.ecommerce.auth.core.authenticate.domain.model.entity;

import lombok.Data;

@Data
public class User {
    private long id;
    private String username;
    private String password;
}
