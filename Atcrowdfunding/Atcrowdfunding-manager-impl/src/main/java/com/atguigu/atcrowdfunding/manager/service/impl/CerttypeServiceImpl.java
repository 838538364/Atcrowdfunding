package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.manager.dao.AccountTypeCertMapper;
import com.atguigu.atcrowdfunding.manager.service.CerttypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CerttypeServiceImpl implements CerttypeService {
    @Autowired
    private AccountTypeCertMapper accountTypeCertMapper;

    @Override
    public int insertAcctTypeCert(Map<String, Object> paramMap) {
        return accountTypeCertMapper.insertAcctTypeCert(paramMap);
    }

    @Override
    public int deleteAcctTypeCert(Map<String, Object> paramMap) {
        return accountTypeCertMapper.deleteAcctTypeCert(paramMap);
    }

    @Override
    public List<Map<String, Object>> queryAcctTypeCerts() {
        return accountTypeCertMapper.queryAcctTypeCerts();
    }


}
