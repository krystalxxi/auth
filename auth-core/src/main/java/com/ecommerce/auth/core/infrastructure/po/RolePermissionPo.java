package com.ecommerce.auth.core.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("role_permission")
@Data
public class RolePermissionPo {
    private long id;
    @TableField(value = "role_id")
    private long roleId;
    @TableField(value = "permission_id")
    private long permissionId;
}
