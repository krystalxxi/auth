package com.ecommerce.auth.core.authenticate.domain.service;

import com.ecommerce.auth.core.authenticate.domain.model.entity.User;
import com.ecommerce.auth.core.authenticate.domain.model.valobj.UserExt;

/**
 * 用户领域服务
 */
public interface UserDomainService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * 更新用户信息
     * @param userExt
     * @return
     */
    boolean updateUser(UserExt userExt);

    /**
     * 注销用户
     * @param userId
     * @return
     */
    boolean logout(Long userId);
}
