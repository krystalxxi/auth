package com.ecommerce.auth.core.authenticate.domain.model.valobj;

import lombok.Data;

import java.util.Date;

@Data
public class UserExt {
    private long id;
    private long userId;
    private Byte sex;
    private Date birth;
}
