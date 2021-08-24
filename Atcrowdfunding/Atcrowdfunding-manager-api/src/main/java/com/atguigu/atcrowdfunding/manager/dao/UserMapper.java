package com.atguigu.atcrowdfunding.manager.dao;


import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

	User queryUserlogin(Map<String, Object> paramMap);

//    List<User> queryList(@Param("startIndex") Integer startIndex,@Param("pagesize") Integer pagesize);
//
//    Integer queryCount();

    List<User> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int deleteUserByBatchUsers(Data data);

    List<Role> queryAll();

    List<Integer> queryRoleIdsByUserid(Integer id);

    Integer insertUserRole(@Param("userid")Integer userid, @Param("data")Data data);

    Integer deleteUserRole(@Param("userid")Integer userid, @Param("data")Data data);

    List<Permission> queryPermissionByUserid(Integer id);
}