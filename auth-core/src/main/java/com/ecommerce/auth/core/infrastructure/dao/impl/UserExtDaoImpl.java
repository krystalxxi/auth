package com.ecommerce.auth.core.infrastructure.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecommerce.auth.core.infrastructure.dao.UserExtDao;
import com.ecommerce.auth.core.infrastructure.mapper.UserExtMapper;
import com.ecommerce.auth.core.infrastructure.po.UserExtPo;
import org.springframework.stereotype.Repository;

@Repository
public class UserExtDaoImpl extends ServiceImpl<UserExtMapper, UserExtPo> implements UserExtDao {
}
