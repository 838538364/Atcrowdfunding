package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.RolePermission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.dao.RoleMapper;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Page queryPage(Map paramMap) {
        Page page=new Page((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
        Integer startIndex=page.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Role> roleList=roleMapper.queryList(paramMap);
        page.setRoleList(roleList);

        Integer count=roleMapper.queryCount(paramMap);
        page.setTotalsize(count);
        return page;
    }

    @Override
    public int savaRolePermissionRelationship(Integer roleid, Data datas) {
        roleMapper.deleteRolePermissionRelationship(roleid);
        int totalCount=0;
        List<Integer> ids=datas.getIds();
        for (Integer permissionid:ids){
            RolePermission rp=new RolePermission();
            rp.setRoleid(roleid);
            rp.setPermissionid(permissionid);
            int count=roleMapper.insertRolePermission(rp);
            totalCount+=count;
        }
        return totalCount;
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteBatchRoleVo(Data data) {
        return roleMapper.deleteUserByBatchRoles(data);
    }

    @Override
    public int deleteRole(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int saveRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public List<Role> queryAll() {
        return roleMapper.selectAll();
    }
}
