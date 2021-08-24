package com.atguigu.atcrowdfunding.potal.listener;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import com.atguigu.atcrowdfunding.util.ApplicationContextUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;

//实名认证审核通过
public class PassListener implements ExecutionListener {

    //不能直接进行自动装配，因为流程监听器对象是自己创建的

    public void notify(DelegateExecution delegateExecution) throws Exception {
       Integer memberid=(Integer)delegateExecution.getVariable("memberid");

       //获取IOC容器。通过自定义的工具类。实现Spring接口，以接口注入的方式获取IOC容器对象
        ApplicationContext applicationContext= ApplicationContextUtils.applicationContext;
        TicketService ticketService=applicationContext.getBean(TicketService.class);
        MemberService memberService=applicationContext.getBean(MemberService.class);

       //更新t_member表的authstatus字段 1 -> 2  -  已实名认证
        Member member=memberService.getMemberById(memberid);
        member.setAuthstatus("2");
        memberService.updateAuthStatus(member);

        //更新t_ticket表的status字段0 -> 1 表示流程结束
        ticketService.updateStatus(member);
    }
}
