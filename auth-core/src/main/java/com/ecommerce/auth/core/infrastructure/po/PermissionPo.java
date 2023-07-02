package com.ecommerce.auth.core.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("permission")
@Data
public class PermissionPo {
    private long id;
    private String url;
    private String name;
    private String description;
    private long pid;
}
