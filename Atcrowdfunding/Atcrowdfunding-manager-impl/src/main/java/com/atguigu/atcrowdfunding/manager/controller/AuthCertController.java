package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
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
@RequestMapping("/auth_cert")
public class AuthCertController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String index(){
        return "authcert/index";
    }



    @ResponseBody
    @RequestMapping("/pageQuery")
    public Object pageQuery(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize){
        AjaxResult result=new AjaxResult();

        try {

            Page page=new Page(pageno,pagesize);

            TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey("auth").taskCandidateGroup("backuser");
            List<Task> listPage = taskQuery.listPage(page.getStartIndex(), pagesize);

            List<Map<String, Object>> taskMapList = new ArrayList<Map<String, Object>>();//避免JSON数据转换出错
            for ( Task task : listPage ) {

                Map<String, Object> taskMap = new HashMap<String, Object>();
                taskMap.put("id", task.getId());
                taskMap.put("name", task.getName());
                //通过任务表的流程定义id查询流程定义
                ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                        .processDefinitionId(task.getProcessDefinitionId())
                        .singleResult();
                taskMap.put("procDefName", pd.getName());
                taskMap.put("procDefVersion", pd.getVersion());
                // 通过流程查找流程审批单，再查询会员信息
                Member member=ticketService.getMemberByPiid(task.getProcessInstanceId());
                taskMap.put("memberUsername",member.getUsername());
                taskMap.put("memberid",member.getId());
                taskMapList.add(taskMap);

            }


            int count=(int)taskQuery.count();
            page.setData(taskMapList);
            page.setTotalsize(count);
            result.setPage(page);


            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }


    @RequestMapping("/show")
    public String show(Integer memberid,Map<String,Object> map){

        Member member=memberService.getMemberById(memberid);
        List<Map<String,Object>> list=memberService.queryCertByMemberid(memberid);

        map.put("member",member);
        map.put("certimgs",list);

        return "authcert/show";
    }

    @ResponseBody
    @RequestMapping("/pass")
    public Object pass( String taskid, Integer memberid ) {

        AjaxResult result = new AjaxResult();
        try {
            taskService.setVariable(taskid, "flag", true);
            taskService.setVariable(taskid, "memberid", memberid);
            // 传递参数，让流程继续执行
            taskService.complete(taskid);
            result.setSuccess(true);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }



    @ResponseBody
    @RequestMapping("/refuse")
    public Object refuse(String taskid, Integer memberid) {

        AjaxResult result = new AjaxResult();
        try {
            taskService.setVariable(taskid, "flag", false);
            taskService.setVariable(taskid, "memberid", memberid);
            // 传递参数，让流程继续执行
            taskService.complete(taskid);
            result.setSuccess(true);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
}
