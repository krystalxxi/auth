package com.ecommerce.auth.core.authenticate.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.ecommerce.auth.core.infrastructure.dao.UserDao;
import com.ecommerce.auth.core.infrastructure.dao.UserRoleDao;
import com.ecommerce.auth.core.authenticate.domain.service.UserQueryService;
import com.ecommerce.auth.core.infrastructure.po.UserPo;
import com.ecommerce.auth.core.infrastructure.po.UserRolePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    @Autowired
    private UserDao userRepository;

    @Autowired
    private UserRoleDao userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据username查询用户
        QueryWrapper<UserPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserPo userPo = userRepository.getOne(queryWrapper);
        if (null == userPo) {
            throw new UsernameNotFoundException("用户不存在，请先注册");
        }
        // 查询角色
        QueryWrapper queryRoleWrapper = new QueryWrapper<>();
        queryRoleWrapper.eq("user_id", userPo.getId());
        List<UserRolePo> userRolePoList = userRoleRepository.list(queryRoleWrapper);
        if (CollectionUtils.isEmpty(userRolePoList)) {
            throw new UsernameNotFoundException("用户:" + username + "未授权");
        }

        List<SimpleGrantedAuthority> grantedAuthorities = userRolePoList.stream().map(userRolePo -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(String.valueOf(userRolePo.getRoleId()));
            return simpleGrantedAuthority;
        }).collect(Collectors.toList());

        org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(userPo.getUsername(), userPo.getPassword(), grantedAuthorities);
        if (!securityUser.isEnabled()){
            throw new UsernameNotFoundException("Account Disabled");
        }else if (!securityUser.isAccountNonLocked()){
            throw new UsernameNotFoundException("Account Locked");
        }else if (!securityUser.isAccountNonExpired()){
            throw new UsernameNotFoundException("Account Expired");
        }else if (!securityUser.isCredentialsNonExpired()){
            throw new UsernameNotFoundException("Credentials expired");
        }
        return securityUser;
    }
}
