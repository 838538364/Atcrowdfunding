package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.vo.Data;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    Cert selectByPrimaryKey(Integer id);

    List<Cert> selectAll();

    int updateByPrimaryKey(Cert record);


    List<Cert> queryList(Map paramMap);

    Integer queryCount(Map paramMap);

    int deleteUserByBatchCerts(Data data);

    List<Cert> queryCertsByAccttype(String accttype);

    void insertMemberCert(MemberCert memberCert);
}