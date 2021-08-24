package com.atguigu.atcrowdfunding.potal.service;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.Ticket;

public interface TicketService {

    Ticket queryTickByMember(Integer id);

    void insertTick(Ticket ticket);

    void updateTicketPstep(Ticket ticket);

    void updatePiidAndPstep(Ticket ticket);

    Member getMemberByPiid(String getProcessInstanceId);

    void updateStatus(Member member);
}
