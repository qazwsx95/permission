package com.cch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cch.mapper.AccountMapper;
import com.cch.model.bean.Account;
import com.cch.model.vo.AccountVO;
import com.cch.service.AccountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;
    @Override
    public Account login(String username, String password) {
        return accountMapper.login(username, password);
    }

    @Override
    public void insert(AccountVO avo) {
        Account account = BeanUtil.copyProperties(avo, Account.class);
        accountMapper.insert(account);
        avo.setId(account.getId());
    }

    @Override
    public void deleteByIds(int... ids) {
        accountMapper.deleteByIds(ids);
    }

    @Override
    public void updateById(AccountVO accountVO) {
        Account account = BeanUtil.copyProperties(accountVO, Account.class);
        accountMapper.updateById(account);
    }

    @Override
    public PageInfo<Account> findAll(AccountVO accountVO, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Account> list = accountMapper.findAll(accountVO);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean isDisable(String username) {
        return accountMapper.isDisable(username) == 1;
    }


}
