package com.ecommerce.auth.core.authenticate.application;

import com.ecommerce.auth.api.UserApplication;
import com.ecommerce.auth.api.dto.UserDto;
import com.ecommerce.auth.core.authenticate.domain.model.entity.User;
import com.ecommerce.auth.core.authenticate.domain.model.valobj.UserExt;
import com.ecommerce.auth.core.authenticate.domain.service.UserDomainService;
import com.ecommerce.basicplatform.util.BeanCopyUtil;
import com.ecommerce.basicplatform.vo.ResultVo;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DubboService
public class UserApplicationImpl implements UserApplication {
    @Autowired
    private UserDomainService userDomainService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public ResultVo<Boolean> register(UserDto userDto) {
        if (null == userDto || StringUtils.isEmpty(userDto.getUsername()) || StringUtils.isEmpty(userDto.getPassword())){
            return ResultVo.newFailResult("缺少入参");
        }
        userDto.setPassword(encoder.encode(userDto.getPassword()));

        userDomainService.register((User)BeanCopyUtil.copyBeanProperties(userDto, User.class));
        return ResultVo.newSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultVo<Boolean> updateUser(UserDto userDto) throws Exception{
        UserExt userExt = new UserExt();
        userExt.setUserId(userDto.getUserId());
        userExt.setSex(Byte.valueOf(userDto.getSex()));
        userExt.setBirth(DateUtils.parseDate(userDto.getBirth(),"YYYY-MM-dd"));
        userDomainService.updateUser(userExt);
        return ResultVo.newSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultVo<Boolean> logout(UserDto userDto) {
        if (null == userDto || null == userDto.getUserId()){
            return ResultVo.newFailResult("入参错误");
        }
        userDomainService.logout(userDto.getUserId());
        return ResultVo.newSuccessResult(Boolean.TRUE);
    }
}
