package com.atguigu.atcrowdfunding.potal.service;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.Ticket;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Member queryMemberlogin(Map<String, Object> paramMap);

    void updateAcctType(Member loginMember);

    void updateBasicinfo(Member loginMember);


    void updateEmail(Member loginMember);

    void updateAuthStatus(Member loginMember);

    Member getMemberById(Integer memberid);

    List<Map<String, Object>> queryCertByMemberid(Integer memberid);
}
