package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("index")
    public String index(){

        return "permission/index";
    }

    @RequestMapping("toAdd")
    public String toAdd(){

        return "permission/add";
    }

    @RequestMapping("toUpdate")
    public String toUpdate(Integer id,Map map){

        Permission permission=permissionService.getPermission(id);
        map.put("permission",permission);
        return "permission/update";
    }




    @ResponseBody
    @RequestMapping("/deletePermission")
    public Object deletePermission(Integer id) {

        AjaxResult result = new AjaxResult();
        try{

            int count = permissionService.deletePermission(id);
            result.setSuccess(count==1);

        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除许可失败");
        }
        return result;
    }










    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(Permission permission) {

        AjaxResult result = new AjaxResult();
        try{

            int count = permissionService.updatePermission(permission);
            result.setSuccess(count==1);

        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("修改许可失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(Permission permission) {

        AjaxResult result = new AjaxResult();
        try{

            int count = permissionService.savePermission(permission);
            result.setSuccess(count==1);

        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("保存许可失败");
        }
        return result;
    }









    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {

        AjaxResult result = new AjaxResult();
        try{
            List<Permission> root=new ArrayList<Permission>();

            List<Permission> childrenPermission=permissionService.queryAllPermission();

            Map<Integer,Permission> map=new HashMap<Integer, Permission>();

            for (Permission innerpermission:childrenPermission){
                map.put(innerpermission.getId(),innerpermission);
            }

            for (Permission permission:childrenPermission){
                //通过子查找父
                //子菜单
                Permission child=permission;//假设为子菜单
                if (child.getPid()==null){
                    root.add(permission);
                }else {
                    //父结点
                    Permission parent=map.get(child.getPid());
                    parent.getChildren().add(child);
                }
            }



            result.setSuccess(true);
            result.setData(root);


        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("加载许可树失败");
        }
        return result;
    }
}
