package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.atguigu.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/r_index")
    public String index() {

        return "role/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {

        return "role/add";
    }

    @RequestMapping("/toAssignPermission")
    public String toAssignPermission() {

        return "role/assignPermission";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Map map) {
        Role role = roleService.selectByPrimaryKey(id);
        map.put("role", role);

        return "role/update";
    }


    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(Role role) {
        AjaxResult result = new AjaxResult();

        try {

            int count = roleService.updateRole(role);
            result.setSuccess(count == 1);


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("保存角色失败");
        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(Role role) {
        AjaxResult result = new AjaxResult();

        try {

            int count = roleService.saveRole(role);
            result.setSuccess(count == 1);


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("保存角色失败");
        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(Integer id) {
        AjaxResult result = new AjaxResult();

        try {

            int count = roleService.deleteRole(id);
            result.setSuccess(count == 1);


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除角色失败");
        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/doDeleteRoleBatch")
    public Object doDeleteRoleBatch(Data data) {
        AjaxResult result = new AjaxResult();

        try {

            int count = roleService.deleteBatchRoleVo(data);
            result.setSuccess(count == data.getRoleList().size());


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除角色失败");
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/toIndex")
    public Object toIndex(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                          @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize, String queryText) {
        AjaxResult result = new AjaxResult();
        try {
            Map paramMap = new HashMap();
            paramMap.put("pageno", pageno);
            paramMap.put("pagesize", pagesize);
            if (StringUtil.isNotEmpty(queryText)) {
                if (queryText.contains("%")) {

                    queryText = queryText.replaceAll("%", "\\\\%");
                }
                paramMap.put("queryText", queryText);
            }
            Page page = roleService.queryPage(paramMap);
            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("查询数据失败");
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/loadDataAsync")
    public Object loadDataAsync(Integer roleid) {


        List<Permission> root = new ArrayList<Permission>();

        List<Permission> childrenPermission = permissionService.queryAllPermission();

        List<Integer> permissionids = permissionService.queryPermissionidByRoleid(roleid);

        Map<Integer, Permission> map = new HashMap<Integer, Permission>();

        for (Permission innerpermission : childrenPermission) {
            map.put(innerpermission.getId(), innerpermission);
            if(permissionids.contains(innerpermission.getId())){

                innerpermission.setChecked(true);

            }


        }

        for (Permission permission : childrenPermission) {
            //通过子查找父
            //子菜单
            Permission child = permission;//假设为子菜单
            if (child.getPid() == null) {
                root.add(permission);
            } else {
                //父结点
                Permission parent = map.get(child.getPid());
                parent.getChildren().add(child);
            }
        }


        return root;
    }



    @ResponseBody
    @RequestMapping("/doAssignPermission")
    public Object doAssignPermission(Integer roleid,Data datas) {
        AjaxResult result = new AjaxResult();

        try {

            int count = roleService.savaRolePermissionRelationship(roleid,datas);
            result.setSuccess(count == datas.getIds().size());


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();

        }

        return result;
    }
}
