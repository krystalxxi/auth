package com.ecommerce.auth.api;

import com.ecommerce.auth.api.dto.UserDto;
import com.ecommerce.basicplatform.vo.ResultVo;

public interface UserApplication {
    /**
     * 注册
     * @param userDto
     * @return
     */
    ResultVo<Boolean> register(UserDto userDto);

    /**
     * 更新用户信息
     * @param userDto
     * @return
     */
    ResultVo<Boolean> updateUser(UserDto userDto) throws Exception;

    /**
     * 注销
     * @param userDto
     * @return
     */
    ResultVo<Boolean> logout(UserDto userDto);
}
