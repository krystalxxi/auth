package com.ecommerce.auth.core.infrastructure.dao.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ecommerce.auth.core.infrastructure.mapper.PermissionMapper;
import com.ecommerce.auth.core.infrastructure.po.PermissionPo;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionDaoImpl extends ServiceImpl<PermissionMapper, PermissionPo> {
}
