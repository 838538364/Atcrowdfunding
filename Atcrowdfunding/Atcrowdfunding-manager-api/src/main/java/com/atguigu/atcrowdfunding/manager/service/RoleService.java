package com.atguigu.atcrowdfunding.manager.service;


import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface RoleService {


    List<Role> queryAll();

    Page queryPage(Map paramMap);

    int saveRole(Role role);

    int deleteRole(Integer id);


    int deleteBatchRoleVo(Data data);

    Role selectByPrimaryKey(Integer id);

    int updateRole(Role role);


    int savaRolePermissionRelationship(Integer roleid, Data datas);
}
