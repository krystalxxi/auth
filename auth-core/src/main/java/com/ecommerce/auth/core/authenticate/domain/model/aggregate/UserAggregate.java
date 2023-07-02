package com.ecommerce.auth.core.authenticate.domain.model.aggregate;

import com.ecommerce.auth.core.authenticate.domain.model.entity.User;
import com.ecommerce.auth.core.authenticate.domain.model.entity.UserRole;
import com.ecommerce.auth.core.authenticate.domain.model.valobj.UserExt;
import lombok.Data;

import java.util.List;

@Data
public class UserAggregate {
    /**
     * 聚合根id
     */
    private Long id;
    /**
     * 实体对象
     */
    private User user;
    /**
     * 值对象
     */
    private UserExt userExt;
    /**
     * 用户角色
     */
    private List<UserRole> userRoles;
}
