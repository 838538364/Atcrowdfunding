package com.atguigu.atcrowdfunding.manager.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.exception.LoginFailException;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.atguigu.atcrowdfunding.manager.dao.UserMapper;
import com.atguigu.atcrowdfunding.manager.service.UserService;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;


	@Override
	public List<Role> queryAll() {
		return userMapper.queryAll();
	}

	@Override
	public Integer insertUserRole(Integer userid, Data data) {
		return userMapper.insertUserRole(userid,data);
	}

	@Override
	public Integer deleteUserRole(Integer userid, Data data) {
		return userMapper.deleteUserRole(userid,data);
	}

	@Override
	public List<Integer> queryRoleIdsByUserid(Integer id) {
		return userMapper.queryRoleIdsByUserid(id);
	}

	@Override
	public List<Permission> queryPermissionByUserid(Integer id) {
		return userMapper.queryPermissionByUserid(id);
	}

	@Override
	public int deleteBatchUserVo(Data data) {
		return userMapper.deleteUserByBatchUsers(data);
	}

	@Override
	public int deleteBatchUser(Integer[] id) {
		int totalCount=0;
		for (Integer userid:id){
			int count=userMapper.deleteByPrimaryKey(userid);
			totalCount+=count;
		}
		if (totalCount!=id.length){
			throw new RuntimeException();
		}
		return totalCount;
	}

	@Override
	public int deleteUser(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateByPrimaryKey(user);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int saveUser(User user) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String createtime=sdf.format(new Date());
		user.setCreatetime(createtime);
		user.setUserpswd(MD5Util.digest("123"));

		return userMapper.insert(user);
	}



//	@Override
//	public Page queryPage(Integer pageno, Integer pagesize) {
//		Page page=new Page(pageno,pagesize);
//
//		Integer startIndex=page.getStartIndex();
//		List<User> datas=userMapper.queryList(startIndex,pagesize);
//		page.setDatas(datas);
//
//		Integer count=userMapper.queryCount();
//		page.setTotalsize(count);
//		return page;
//	}

	@Override
	public Page queryPage(Map<String,Object> paramMap) {
		Page page=new Page((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
		Integer startIndex=page.getStartIndex();
		paramMap.put("startIndex",startIndex);
		List<User> datas=userMapper.queryList(paramMap);
		page.setDatas(datas);

		Integer count=userMapper.queryCount(paramMap);
		page.setTotalsize(count);
    	return page;
	}

	@Override
	public User queryUserlogin(Map<String, Object> paramMap) {
		
		User user = userMapper.queryUserlogin(paramMap);

		if(user==null){
			throw new LoginFailException("用户账号或密码不正确!");
		}
		
		return user;
	}

	
}
