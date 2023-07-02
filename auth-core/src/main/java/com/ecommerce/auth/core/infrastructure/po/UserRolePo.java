package com.ecommerce.auth.core.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_role")
@Data
public class UserRolePo {
    private long id;

    @TableField(value = "user_id")
    private long userId;

    @TableField(value = "role_id")
    private long roleId;
}
