package com.ecommerce.auth.core.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserPo {
    private long id;
    private String username;
    private String password;
}
