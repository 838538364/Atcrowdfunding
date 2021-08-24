package com.atguigu.atcrowdfunding.manager.service;


import com.atguigu.atcrowdfunding.bean.Permission;

import java.util.List;

public interface PermissionService {


    Permission queryRootPermission();

    List<Permission> queryChildrenPermissionByPid(Integer id);

    List<Permission> queryAllPermission();

    int savePermission(Permission permission);

    Permission getPermission(Integer id);

    int updatePermission(Permission permission);

    int deletePermission(Integer id);

    List<Integer> queryPermissionidByRoleid(Integer roleid);
}
