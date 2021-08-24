package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> queryAllPermission();

    int updateByPrimaryKey(Permission record);

    Permission queryRootPermission();

    List<Permission> queryChildrenPermissionByPid(Integer id);

    List<Integer> queryPermissionidByRoleid(Integer roleid);
}