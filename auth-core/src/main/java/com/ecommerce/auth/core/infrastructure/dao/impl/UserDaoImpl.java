package com.ecommerce.auth.core.infrastructure.dao.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ecommerce.auth.core.infrastructure.dao.UserDao;
import com.ecommerce.auth.core.infrastructure.mapper.UserMapper;
import com.ecommerce.auth.core.infrastructure.po.UserPo;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends ServiceImpl<UserMapper, UserPo> implements UserDao {
}
