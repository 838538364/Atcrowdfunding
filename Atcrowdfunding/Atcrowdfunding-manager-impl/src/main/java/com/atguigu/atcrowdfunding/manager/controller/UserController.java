package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/toIndex")
    public String toIndex() {
        return "user/index_one";
    }

    @RequestMapping("/assignRole")
    public String assignRole(Integer id,Map map) {

        List<Role> allRole=userService.queryAll();
        List<Integer> roleId=userService.queryRoleIdsByUserid(id);
        //未分配角色
        List<Role> unassign=new ArrayList<Role>();

        //已分配角色
        List<Role> assign=new ArrayList<Role>();

        for (Role role:allRole){
            if (roleId.contains(role.getId())){
                assign.add(role);
            }else {
                unassign.add(role);
            }
        }
        map.put("unassign", unassign);
        map.put("assign", assign);


        return "user/assignrole";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id,Map map) {
        User user=userService.selectByPrimaryKey(id);
        map.put("user",user);
        return "user/update";
    }


    @ResponseBody
    @RequestMapping("/assign")
    public Object assign(Integer userid,Data data) {
        AjaxResult result = new AjaxResult();
        try{

            userService.insertUserRole(userid,data);

                result.setSuccess(true);


        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("分配角色数据失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/unassign")
    public Object unassign(Integer userid,Data data) {
        AjaxResult result = new AjaxResult();
        try{

             userService.deleteUserRole(userid,data);

                result.setSuccess(true);


        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("取消分配数据失败");
        }
        return result;
    }




    @ResponseBody
    @RequestMapping("/doDeleteUserBatch")
    public Object doDeleteUserBatch(Data data) {
        AjaxResult result = new AjaxResult();
        try{

            int count = userService.deleteBatchUserVo(data);
            result.setSuccess(count==data.getDatas().size());

        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除数据失败");
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/doDeleteBatch")
    public Object doDeleteBatch(Integer[] id) {
        AjaxResult result = new AjaxResult();
        try{

            int count = userService.deleteBatchUser(id);
            result.setSuccess(count==id.length);

        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除数据失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(Integer id) {
        AjaxResult result = new AjaxResult();
        try{

            int count = userService.deleteUser(id);
            result.setSuccess(count==1);

        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除数据失败");
        }
        return result;
    }


    @RequestMapping("/toAdd")
    public String toAdd() {
        return "user/add";
    }

    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(User user) {
        AjaxResult result = new AjaxResult();
        try{

            int count = userService.updateUser(user);
            result.setSuccess(count==1);

        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("保存数据失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(User user) {
        AjaxResult result = new AjaxResult();
        try{

            int count = userService.saveUser(user);
            result.setSuccess(count==1);

        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("保存数据失败");
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/index")
    public Object index(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize,String queryText) {
        AjaxResult result = new AjaxResult();
        try{
            Map paramMap=new HashMap();
            paramMap.put("pageno",pageno);
            paramMap.put("pagesize",pagesize);
            if (StringUtil.isNotEmpty(queryText)){
                if (queryText.contains("%")){

                    queryText=queryText.replaceAll("%","\\\\%");
                }
                paramMap.put("queryText",queryText);
            }
            Page page = userService.queryPage(paramMap);
            result.setSuccess(true);
            result.setPage(page);
        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("查询数据失败");
        }
        return result;
    }
    //异步请求
//    @ResponseBody
//    @RequestMapping("/index")
//    public Object index(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
//                        @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize) {
//        AjaxResult result = new AjaxResult();
//        try{
//            Page page = userService.queryPage(pageno, pagesize);
//            result.setSuccess(true);
//            result.setPage(page);
//        }catch (Exception e){
//            result.setSuccess(false);
//            e.printStackTrace();
//            result.setMessage("查询数据失败");
//        }
//
//
//        return result;
//    }

//    @RequestMapping("/index")
//    public String index(@RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
//                        @RequestParam(value = "pagesize",required = false,defaultValue = "10")Integer pagesize, Map map){
//
//        Page page=userService.queryPage(pageno,pagesize);
//        map.put("page",page);
//        return "user/index";
//    }

//    @RequestMapping("/index")
//    public String index(@RequestParam(value = "pageno",required = false,defaultValue = "1")Integer pageno,
//                        @RequestParam(value = "pagesize",required = false,defaultValue = "10")Integer pagesize,Model model){
//
//        Page page=userService.queryPage(pageno,pagesize);
//        model.addAttribute("page",page);
//        return "user/index";
//    }
}
