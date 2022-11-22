package com.cch.service;

import com.cch.model.bean.Account;

public interface AccountService {
    Account login(String username, String password);
}
