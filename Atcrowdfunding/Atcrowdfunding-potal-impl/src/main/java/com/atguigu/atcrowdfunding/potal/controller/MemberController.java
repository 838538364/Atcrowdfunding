package com.atguigu.atcrowdfunding.potal.controller;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.potal.listener.PassListener;
import com.atguigu.atcrowdfunding.potal.listener.RefuseListener;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.vo.Data;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.jfree.chart.axis.Tick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CertService certService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;



    @RequestMapping("/accttype")
    public String accttype() {
        return "member/accttype";
    }


    @RequestMapping("/apply")
    public String apply() {
        return "member/apply";
    }


    @RequestMapping("/uploadCert")
    public String uploadCert() {
        return "member/uploadcert";
    }


    @RequestMapping("/checkemail")
    public String checkemail() {
        return "member/checkemail";
    }


    @RequestMapping("/finishapply")
    public String finishapply() {
        return "member/finishapply";
    }

    @RequestMapping("/doapply")
    public Object doapply(HttpSession session) {

        Member member = (Member) session.getAttribute(Const.LOGIN_Member);
        Ticket ticket = ticketService.queryTickByMember(member.getId());
        if (ticket == null) {
            //创建流程审批单
            ticket = new Ticket();
            ticket.setMemberid(member.getId());
            ticket.setStatus("0");
            ticket.setPstep("apply");
            ticketService.insertTick(ticket);
            return "member/accttype";
        } else {
            // 根据流程审批单的步骤，跳转页面
            if ("accttype".equals(ticket.getPstep())) { //如果账户类型选择完成,去到基本信息页面
                return "member/apply";
            } else if ("basicinfo".equals(ticket.getPstep())) { //如果基本信息页面提交完成,则去到上传资质图片页面

                List<Cert> queryCertsByAccttype=certService.queryCertsByAccttype(member.getAccttype());
                session.setAttribute("queryCertsByAccttype",queryCertsByAccttype);

                return "member/uploadcert";
            }else if ("uploadfile".equals(ticket.getPstep())){

                return "member/checkemail";
            }else if ("checkemail".equals(ticket.getPstep())){

                return "member/finishapply";
            }
        }
        return "member/accttype";
    }


    @ResponseBody
    @RequestMapping("/updateAcctType")
    public Object updateAcctType(HttpSession session, String accttype) {
        AjaxResult result = new AjaxResult();
        try {

            // 获取登录会员信息
            Member loginMember = (Member) session.getAttribute(Const.LOGIN_Member);
            loginMember.setAccttype(accttype);
            // 更新账户类型
            memberService.updateAcctType(loginMember);
            Ticket ticket = ticketService.queryTickByMember(loginMember.getId());
            ticket.setPstep("accttype");
            ticketService.updateTicketPstep(ticket);


            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/updateBasicinfo")
    public Object updateBasicinfo(HttpSession session, Member member) {
        AjaxResult result = new AjaxResult();
        try {


            Member loginMember = (Member) session.getAttribute(Const.LOGIN_Member);
            loginMember.setRealname(member.getRealname());
            loginMember.setCardnum(member.getCardnum());
            loginMember.setTel(member.getTel());
            memberService.updateBasicinfo(loginMember);
            Ticket ticket = ticketService.queryTickByMember(loginMember.getId());
            ticket.setPstep("basicinfo");
            ticketService.updateTicketPstep(ticket);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/doUploadCert")
    public Object doUploadCert(HttpSession session, Data ds) {
        AjaxResult result = new AjaxResult();
        try {

            String realPath = session.getServletContext().getRealPath("/pics");
            // 获取登录会员信息
            Member loginMember = (Member) session.getAttribute(Const.LOGIN_Member);

            List<MemberCert> certimgs=ds.getCertimgs();
            for (MemberCert memberCert:certimgs){
                MultipartFile fileImg=memberCert.getMultipartFile();
                String extName=fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
                String filename=realPath+"/cert"+"/"+ UUID.randomUUID().toString()+extName;
                fileImg.transferTo(new File(filename));
                memberCert.setIconpath(filename);
                memberCert.setMemberid(loginMember.getId());
            }

            certService.saveMemberCert(certimgs);

            Ticket ticket = ticketService.queryTickByMember(loginMember.getId());
            ticket.setPstep("uploadfile");
            ticketService.updateTicketPstep(ticket);


            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }



    @ResponseBody
    @RequestMapping("/startProcess")
    public Object startProcess(HttpSession session,String email) {
        AjaxResult result = new AjaxResult();
        try {

            Member loginMember = (Member) session.getAttribute(Const.LOGIN_Member);

            //如果用户输入新的邮箱，将旧的邮箱地址替换
            if (!loginMember.getEmail().equals(email)){
                loginMember.setEmail(email);
                memberService.updateEmail(loginMember);
            }



            //启动实名认证流程 系统自动发送邮件，生成验证码，验证邮箱地址是否合法


            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("auth").singleResult();
            Map<String, Object> varMap = new HashMap<String, Object>();

            // 4 位验证码

            StringBuilder authcode = new StringBuilder();
            for ( int i = 0; i < 4; i++ ) {
                authcode.append(new Random().nextInt(10));
            }



            varMap.put("toEmail",email);
            varMap.put("loginacct",loginMember.getLoginacct());
            varMap.put("authcode", authcode);
            varMap.put("passListener",new PassListener());
            varMap.put("refuseListener",new RefuseListener());


            ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),varMap);
            Ticket ticket = ticketService.queryTickByMember(loginMember.getId());
            ticket.setPstep("checkemail");
            ticket.setPiid(processInstance.getId());
            ticket.setAuthcode(authcode.toString());
            ticketService.updatePiidAndPstep(ticket);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/doFinishApply")
    public Object doFinishApply(HttpSession session,String authcode) {
        AjaxResult result = new AjaxResult();
        try {

            Member loginMember = (Member) session.getAttribute(Const.LOGIN_Member);
            Ticket ticket = ticketService.queryTickByMember(loginMember.getId());

            if (ticket.getAuthcode().equals(authcode)){
                loginMember.setAuthstatus("1");
                memberService.updateAuthStatus(loginMember);

                Task task = taskService.createTaskQuery().processInstanceId(ticket.getPiid()).taskAssignee(loginMember.getLoginacct()).singleResult();
                taskService.complete(task.getId());
                ticket.setPstep("finishapply");
                ticketService.updateTicketPstep(ticket);
                result.setSuccess(true);
            }else {
                result.setSuccess(false);
                result.setMessage("验证码不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }


}



