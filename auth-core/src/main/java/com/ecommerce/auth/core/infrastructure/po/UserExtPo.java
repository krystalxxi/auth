package com.ecommerce.auth.core.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_ext")
public class UserExtPo {
    private long id;

    @TableField("user_id")
    private long userId;

    private Byte sex;

    private Date birth;
}
