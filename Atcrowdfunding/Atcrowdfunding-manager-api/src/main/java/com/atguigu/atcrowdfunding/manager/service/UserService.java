package com.atguigu.atcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;


import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

public interface UserService {

	User queryUserlogin(Map<String, Object> paramMap);

    User selectByPrimaryKey(Integer id);

    //Page queryPage(Integer pageno, Integer pagesize);

    int saveUser(User user);

    Page queryPage(Map<String,Object> paramMap);

    int updateUser(User user);


    int deleteUser(Integer id);

    int deleteBatchUser(Integer[] id);


    int deleteBatchUserVo(Data data);

    List<Role> queryAll();

    List<Integer> queryRoleIdsByUserid(Integer id);

    Integer insertUserRole(Integer userid, Data data);

    Integer deleteUserRole(Integer userid, Data data);

    List<Permission> queryPermissionByUserid(Integer id);
}
