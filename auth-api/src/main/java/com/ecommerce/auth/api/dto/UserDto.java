package com.ecommerce.auth.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {
    private Long userId;
    private String username;
    private String password;
    private String sex;
    private String birth;
}
