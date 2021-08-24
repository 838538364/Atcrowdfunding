package com.atguigu.atcrowdfunding.manager.service.impl;


import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.dao.PermissionMapper;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Permission queryRootPermission() {
        return permissionMapper.queryRootPermission();
    }

    @Override
    public List<Permission> queryChildrenPermissionByPid(Integer id) {
        return permissionMapper.queryChildrenPermissionByPid(id);
    }

    @Override
    public int updatePermission(Permission permission) {
        return permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public List<Integer> queryPermissionidByRoleid(Integer roleid) {
        return permissionMapper.queryPermissionidByRoleid(roleid);
    }

    @Override
    public int deletePermission(Integer id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Permission getPermission(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int savePermission(Permission permission) {
        return permissionMapper.insert(permission);
    }

    @Override
    public List<Permission> queryAllPermission() {
        return permissionMapper.queryAllPermission();
    }
}
