package com.cch.service.impl;

import com.cch.mapper.PermissionMapper;
import com.cch.mapper.RoleMapper;
import com.cch.model.bean.Permission;
import com.cch.model.bean.Role;
import com.cch.service.StpInterfaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StpInterfaceServiceImpl implements StpInterfaceService {
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<Permission> permissions = permissionMapper.findByAccountId(Integer.valueOf(loginId.toString()));
        List<String> list = permissions.stream()
//                map里面每一个都用调用这个类里面的getCode(),传入getCode方法
                .map(Permission::getCode)
//                得到新的值变成list集合
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<Role> roles = roleMapper.findByAccountId(Integer.valueOf(loginId.toString()));
        List<String> list = roles.stream().map(Role::getCode).collect(Collectors.toList());
        return list;
    }
}
