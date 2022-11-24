package com.cch.controller;

import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.stp.StpUtil;
import com.cch.model.bean.Account;
import com.cch.model.vo.LoginVO;
import com.cch.model.vo.HttpResult;
import com.cch.service.AccountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class LoginController {
    @Resource
    private AccountService accountService;
    @PostMapping("/login")
    public HttpResult login(@Validated LoginVO account){
        if (accountService.isDisable(account.getUsername())){
            return HttpResult.error(400004,"账号被封了");
        }
        Account login = accountService.login(account.getUsername(), account.getPassword());
        //        没有session    2.02.39
        if (login   != null){
            StpUtil.login(login.getId());
            //得到token
            String tokenValue = StpUtil.getTokenValue();
            List<String> permissions =StpUtil.getPermissionList();
            List<String> roles =StpUtil.getRoleList();
            return HttpResult.ok()
                    .put("token", tokenValue)
                    .put("permissions", permissions)
                    .put("roles", roles);

        }else {
            return HttpResult.error(400,"用户名或密码错误");
        }

    }



















}
