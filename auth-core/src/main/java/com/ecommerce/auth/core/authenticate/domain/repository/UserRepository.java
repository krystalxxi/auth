package com.ecommerce.auth.core.authenticate.domain.repository;

import com.ecommerce.auth.core.authenticate.domain.model.aggregate.UserAggregate;

public interface UserRepository {
    /**
     * 根据聚合根id获取聚合
     *
     * @param id
     * @return
     */
    UserAggregate byId(Long id);

    /**
     * 保存
     *
     * @param aggregateUser
     */
    void save(UserAggregate aggregateUser);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id) ;
}
