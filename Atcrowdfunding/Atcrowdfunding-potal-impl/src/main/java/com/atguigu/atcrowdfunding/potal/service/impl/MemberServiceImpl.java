package com.atguigu.atcrowdfunding.potal.service.impl;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.potal.dao.MemberMapper;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    public List<Map<String, Object>> queryCertByMemberid(Integer memberid) {
        return memberMapper.queryCertByMemberid(memberid);
    }

    public Member getMemberById(Integer memberid) {
        return memberMapper.getMemberById(memberid);
    }

    public void updateAuthStatus(Member loginMember) {
        memberMapper.updateAuthStatus(loginMember);
    }

    public void updateEmail(Member loginMember) {
        memberMapper.updateEmail(loginMember);
    }

    public void updateBasicinfo(Member loginMember) {
        memberMapper.updateBasicinfo(loginMember);
    }

    public void updateAcctType(Member loginMember) {
        memberMapper.updateAcctType(loginMember);
    }

    public Member queryMemberlogin(Map<String, Object> paramMap) {
        return memberMapper.queryMebmerlogin(paramMap);
    }


}
