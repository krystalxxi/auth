package com.ecommerce.auth.core.infrastructure.dao.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ecommerce.auth.core.infrastructure.dao.RoleDao;
import com.ecommerce.auth.core.infrastructure.mapper.RoleMapper;
import com.ecommerce.auth.core.infrastructure.po.RolePo;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends ServiceImpl<RoleMapper, RolePo> implements RoleDao {
}
