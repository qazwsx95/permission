package com.cch.service;

import com.cch.model.bean.Account;
import com.cch.model.vo.AccountVO;
import com.github.pagehelper.PageInfo;

/**
 * 账号业务
 */
public interface AccountService {
    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    Account login(String username, String password);

    /**
     * 新增账户信息
     * @param account
     */
    void insert(AccountVO account);

    /**
     * 批量删除账户
     * @param ids
     */
    void deleteByIds(int...ids);

    /**
     * 修改账户信息
     * @param accountVO
     */
    void updateById(AccountVO accountVO);

    /**
     * 分页查询账户信息列表
     * @param accountVO
     * @param page
     * @param pageSize
     * @return
     */
    PageInfo<Account> findAll(AccountVO accountVO,int page,int pageSize);

    /**
     * 查询账号是否被封
     * @param username 用户名
     * @return true=封禁，false=正常
     */
    Boolean isDisable(String username);










}
