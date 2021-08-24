package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Cert;

import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.manager.dao.CertMapper;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CertServiceImpl implements CertService {

    @Autowired
    private CertMapper certMapper;

    @Override
    public int deleteBatchCertVo(Data data) {
        return certMapper.deleteUserByBatchCerts(data);
    }

    @Override
    public void saveMemberCert(List<MemberCert> certimgs) {
        for (MemberCert memberCert:certimgs){
            certMapper.insertMemberCert(memberCert);
        }
    }

    @Override
    public List<Cert> queryCertsByAccttype(String accttype) {
        return certMapper.queryCertsByAccttype(accttype);
    }

    @Override
    public List<Cert> queryAll() {
        return certMapper.selectAll();
    }

    @Override
    public int deleteCert(Integer id) {
        return certMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Cert selectByPrimaryKey(Integer id) {
        return certMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateCert(Cert cert) {
        return certMapper.updateByPrimaryKey(cert);
    }

    @Override
    public int saveCert(Cert cert) {
        return certMapper.insert(cert);
    }

    @Override
    public Page queryPage(Map paramMap) {
        Page page=new Page((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
        Integer startIndex=page.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Cert> certList=certMapper.queryList(paramMap);
        page.setCertList(certList);

        Integer count=certMapper.queryCount(paramMap);
        page.setTotalsize(count);
        return page;
    }
}
