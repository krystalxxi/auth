package com.ecommerce.auth.core.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ecommerce.auth.core.authenticate.domain.model.aggregate.UserAggregate;
import com.ecommerce.auth.core.authenticate.domain.model.entity.User;
import com.ecommerce.auth.core.authenticate.domain.model.entity.UserRole;
import com.ecommerce.auth.core.authenticate.domain.model.valobj.UserExt;
import com.ecommerce.auth.core.authenticate.domain.repository.UserRepository;
import com.ecommerce.auth.core.infrastructure.dao.UserDao;
import com.ecommerce.auth.core.infrastructure.dao.UserExtDao;
import com.ecommerce.auth.core.infrastructure.dao.UserRoleDao;
import com.ecommerce.auth.core.infrastructure.po.UserExtPo;
import com.ecommerce.auth.core.infrastructure.po.UserPo;
import com.ecommerce.auth.core.infrastructure.po.UserRolePo;
import com.ecommerce.basicplatform.util.BeanCopyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Resource
    private UserDao userDao;

    @Resource
    private UserExtDao userExtDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Override
    public UserAggregate byId(Long id) {
        UserAggregate userAggregate = new UserAggregate();
        UserPo userPo = userDao.getById(id);
        if (null != userPo) {
            userAggregate.setId(userPo.getId());
            userAggregate.setUser((User) BeanCopyUtil.copyBeanProperties(userPo, User.class));
            QueryWrapper<UserExtPo> userExtQuery = new QueryWrapper<>();
            userExtQuery.eq("user_id", id);
            List<UserExtPo> userExtPoList = userExtDao.list(userExtQuery);
            if (CollectionUtils.isNotEmpty(userExtPoList)) {
                userAggregate.setUserExt((UserExt) BeanCopyUtil.copyBeanProperties(userExtPoList.get(0), UserExt.class));
            }
            QueryWrapper<UserRolePo> userRoleQuery = new QueryWrapper<>();
            userRoleQuery.eq("id", id);
            List<UserRolePo> userRolePos = userRoleDao.list(userRoleQuery);
            if (CollectionUtils.isNotEmpty(userRolePos)) {
                userAggregate.setUserRoles(BeanCopyUtil.copyListProperties(userRolePos, new Supplier<UserRole>() {
                    @Override
                    public UserRole get() {
                        return new UserRole();
                    }
                }));
            }
        }
        return userAggregate;
    }

    @Override
    @Transactional
    public void save(UserAggregate aggregateUser) {
        if (null == aggregateUser) {
            return;
        }
        // 注册
        if (null != aggregateUser.getUser() && null == aggregateUser.getId()) {
            UserPo userPo = (UserPo) BeanCopyUtil.copyBeanProperties(aggregateUser.getUser(), UserPo.class);
            userDao.save(userPo);
        }
        // 赋权
        if (CollectionUtils.isNotEmpty(aggregateUser.getUserRoles()) && null != aggregateUser.getId()){
            List<UserRolePo> userRolePos = aggregateUser.getUserRoles().stream().map(userRole -> {
                UserRolePo userRolePo = (UserRolePo)BeanCopyUtil.copyBeanProperties(userRole,UserRolePo.class);
                return userRolePo;
            }).collect(Collectors.toList());
            userRoleDao.saveBatch(userRolePos);
        }
        // 修改用户详情
        if (null != aggregateUser.getUserExt() && null != aggregateUser.getId()) {
            UserExtPo userExtPo = (UserExtPo) BeanCopyUtil.copyBeanProperties(aggregateUser.getUserExt(), UserExtPo.class);
            userExtPo.setUserId(aggregateUser.getId());
            userExtDao.saveOrUpdate(userExtPo);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        userExtDao.remove(queryWrapper);
        userRoleDao.remove(queryWrapper);
        userDao.removeById(id);
    }
}
