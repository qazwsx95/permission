package com.cch.service.impl;

import com.cch.mapper.AccountMapper;
import com.cch.model.bean.Account;
import com.cch.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;
    @Override
    public Account login(String username, String password) {
        return accountMapper.login(username, password);
    }
}
