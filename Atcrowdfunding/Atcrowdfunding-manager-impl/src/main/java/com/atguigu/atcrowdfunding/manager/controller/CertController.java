package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.atguigu.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cert")
public class CertController {

    @Autowired
    private CertService certService;

    @RequestMapping("/index")
    public String index(){
        return "cert/index";
    }


    @RequestMapping("/toAdd")
    public String toAdd(){
        return "cert/add";
    }

    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(Cert cert) {
        AjaxResult result = new AjaxResult();

        try {

            int count = certService.saveCert(cert);
            result.setSuccess(count == 1);


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("保存证件失败");
        }

        return result;
    }


    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id,Map map){
        Cert cert=certService.selectByPrimaryKey(id);
        map.put("cert",cert);
        return "cert/update";
    }


    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(Cert cert) {
        AjaxResult result = new AjaxResult();

        try {

            int count = certService.updateCert(cert);
            result.setSuccess(count == 1);


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("修改证件失败");
        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(Integer id) {
        AjaxResult result = new AjaxResult();

        try {

            int count = certService.deleteCert(id);
            result.setSuccess(count == 1);


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除角色失败");
        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/doDeleteCertBatch")
    public Object doDeleteCertBatch(Data data) {
        AjaxResult result = new AjaxResult();

        try {

            int count = certService.deleteBatchCertVo(data);
            result.setSuccess(count == data.getCertList().size());


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除角色失败");
        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/toIndex")
    public Object toIndex(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                          @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize, String queryText) {
        AjaxResult result = new AjaxResult();
        try {
            Map paramMap = new HashMap();
            paramMap.put("pageno", pageno);
            paramMap.put("pagesize", pagesize);
            if (StringUtil.isNotEmpty(queryText)) {
                if (queryText.contains("%")) {

                    queryText = queryText.replaceAll("%", "\\\\%");
                }
                paramMap.put("queryText", queryText);
            }
            Page page = certService.queryPage(paramMap);
            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("查询数据失败");
        }
        return result;
    }
}
