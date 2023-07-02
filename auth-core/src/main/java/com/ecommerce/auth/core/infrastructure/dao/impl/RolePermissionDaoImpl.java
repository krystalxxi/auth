package com.ecommerce.auth.core.infrastructure.dao.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ecommerce.auth.core.infrastructure.dao.RolePermissionDao;
import com.ecommerce.auth.core.infrastructure.mapper.RolePermissionMapper;
import com.ecommerce.auth.core.infrastructure.po.RolePermissionPo;
import org.springframework.stereotype.Repository;

@Repository
public class RolePermissionDaoImpl extends ServiceImpl<RolePermissionMapper, RolePermissionPo> implements RolePermissionDao {
}
