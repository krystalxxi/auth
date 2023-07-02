package com.ecommerce.auth.core.authenticate.domain.service.impl;

import com.ecommerce.auth.core.authenticate.domain.model.aggregate.UserAggregate;
import com.ecommerce.auth.core.authenticate.domain.model.entity.User;
import com.ecommerce.auth.core.authenticate.domain.model.valobj.UserExt;
import com.ecommerce.auth.core.authenticate.domain.repository.UserRepository;
import com.ecommerce.auth.core.authenticate.domain.service.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDomainServiceImpl implements UserDomainService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean register(User user) {
        UserAggregate userAggregate = new UserAggregate();
        userAggregate.setUser(user);
        userRepository.save(userAggregate);
        return true;
    }

    @Override
    public boolean updateUser(UserExt userExt) {
        UserAggregate userAggregate = new UserAggregate();
        userAggregate.setUserExt(userExt);
        userAggregate.setId(userExt.getUserId());
        userRepository.save(userAggregate);
        return true;
    }

    @Override
    public boolean logout(Long userId) {
        userRepository.delete(userId);
        return true;
    }
}
