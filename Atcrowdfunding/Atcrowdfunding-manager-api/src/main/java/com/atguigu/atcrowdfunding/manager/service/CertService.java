package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface CertService {

    Page queryPage(Map paramMap);


    int saveCert(Cert cert);

    int updateCert(Cert cert);

    Cert selectByPrimaryKey(Integer id);

    int deleteCert(Integer id);

    int deleteBatchCertVo(Data data);

    List<Cert> queryAll();

    List<Cert> queryCertsByAccttype(String accttype);

    void saveMemberCert(List<MemberCert> certimgs);
}
