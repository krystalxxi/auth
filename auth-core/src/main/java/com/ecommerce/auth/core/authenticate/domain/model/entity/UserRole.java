package com.ecommerce.auth.core.authenticate.domain.model.entity;

import lombok.Data;

@Data
public class UserRole {
    private long id;

    private long userId;

    private long roleId;
}
