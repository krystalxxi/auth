package com.ecommerce.auth.core.infrastructure.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ecommerce.auth.core.infrastructure.dao.UserRoleDao;
import com.ecommerce.auth.core.infrastructure.mapper.UserRoleMapper;
import com.ecommerce.auth.core.infrastructure.po.UserRolePo;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleDaoImpl extends ServiceImpl<UserRoleMapper, UserRolePo> implements UserRoleDao {
}
