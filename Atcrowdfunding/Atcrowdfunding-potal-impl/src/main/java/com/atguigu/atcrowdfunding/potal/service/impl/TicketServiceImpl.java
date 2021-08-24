package com.atguigu.atcrowdfunding.potal.service.impl;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.Ticket;
import com.atguigu.atcrowdfunding.potal.dao.TicketMapper;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    public void updateTicketPstep(Ticket ticket) {
        ticketMapper.updatePstep(ticket);
    }

    public void updateStatus(Member member) {
        ticketMapper.updateStatus(member);
    }

    public Member getMemberByPiid(String getProcessInstanceId) {
        return ticketMapper.getMemberByPiid(getProcessInstanceId);
    }

    public void updatePiidAndPstep(Ticket ticket) {
        ticketMapper.updatePiidAndPstep(ticket);
    }

    public void insertTick(Ticket ticket) {
        ticketMapper.saveTicket(ticket);
    }

    public Ticket queryTickByMember(Integer id) {
        return ticketMapper.getTicketByMemberId(id);
    }
}
