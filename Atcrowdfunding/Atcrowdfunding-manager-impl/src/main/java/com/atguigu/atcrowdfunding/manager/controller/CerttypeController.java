package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.manager.service.CerttypeService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/certtype")
public class CerttypeController {

    @Autowired
    private CerttypeService certtypeService;

    @Autowired
    private CertService certService;

    @RequestMapping("/index")
    public String index(Map map){

        List<Cert> certList=certService.queryAll();
        map.put("certAll",certList);

        List<Map<String,Object>> queryAcctTypeCerts = certtypeService.queryAcctTypeCerts();
        map.put("queryAcctTypeCerts",queryAcctTypeCerts);

        return "certtype/index";
    }

    @ResponseBody
    @RequestMapping("/insertAcctTypeCert")
    public Object insertAcctTypeCert( Integer certid, String accttype ) {
        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("certid", certid);
            paramMap.put("accttype", accttype);
            int count = certtypeService.insertAcctTypeCert(paramMap);
            result.setSuccess(count==1);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }



    @ResponseBody
    @RequestMapping("/deleteAcctTypeCert")
    public Object deleteAcctTypeCert( Integer certid, String accttype ) {
        AjaxResult result = new AjaxResult();
        try {

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("certid", certid);
            paramMap.put("accttype", accttype);
            int count = certtypeService.deleteAcctTypeCert(paramMap);
            result.setSuccess(count==1);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
