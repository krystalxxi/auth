package com.ecommerce.auth.core.authenticate.filter;

import com.alibaba.fastjson.JSONObject;
import com.ecommerce.auth.api.vo.UserVo;
import com.ecommerce.basicplatform.util.BeanCopyUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * 登录成功处理器
 */
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        User user = (User) authentication.getPrincipal();
        UserVo userDto = (UserVo) BeanCopyUtil.copyBeanProperties(user, UserVo.class);
        if (!CollectionUtils.isEmpty(user.getAuthorities())) {
            userDto.setAuthorities((Set) user.getAuthorities());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "登录成功");
        jsonObject.put("code", 0000);
        jsonObject.put("user", userDto);
        String s = jsonObject.toJSONString();
        out.write(s);
        out.flush();
        out.close();
    }
}
