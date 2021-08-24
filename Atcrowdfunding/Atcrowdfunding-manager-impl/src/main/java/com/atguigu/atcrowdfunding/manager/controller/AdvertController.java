package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.atguigu.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/advert")
public class AdvertController {

    @Autowired
    private AdvertService advertService;

    @RequestMapping("/index")
    public String index() {

        return "advert/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {

        return "advert/add";
    }


    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(Advertisement advertisement) {
        AjaxResult result = new AjaxResult();

        try {

            int count = advertService.updateAdvert(advertisement);
            result.setSuccess(count == 1);


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("保存角色失败");
        }

        return result;
    }



    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Map map) {
        Advertisement advertisement = advertService.selectByPrimaryKey(id);
        map.put("advertisement", advertisement);

        return "advert/update";
    }


    @ResponseBody
    @RequestMapping("/toIndex")
    public Object toIndex(Integer pageno, Integer pagesize,String queryText) {
        AjaxResult result = new AjaxResult();
        try {
            Map paramMap=new HashMap();
            paramMap.put("pageno",pageno);
            paramMap.put("pagesize",pagesize);
            if (StringUtil.isNotEmpty(queryText)){
                if (queryText.contains("%")){

                    queryText=queryText.replaceAll("%","\\\\%");
                }
                paramMap.put("queryText",queryText);
            }
            Page page=advertService.queryPage(paramMap);
            result.setPage(page);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(Integer id) {
        AjaxResult result = new AjaxResult();

        try {

            int count = advertService.deleteAdvert(id);
            result.setSuccess(count == 1);


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除角色失败");
        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/doDeleteAdvertBatch")
    public Object doDeleteAdvertBatch(Data data) {
        AjaxResult result = new AjaxResult();

        try {

            int count = advertService.deleteBatchAdvertVo(data);
            result.setSuccess(count == data.getAdvertisementList().size());


        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除广告失败");
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(HttpServletRequest request, Advertisement advert , HttpSession session) {
        AjaxResult result = new AjaxResult();

        try {

            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;
            MultipartFile mfile = mreq.getFile("advpic");

            String name = mfile.getOriginalFilename();//java.jpg

            String extname = name.substring(name.lastIndexOf(".")); // .jpg

            String iconpath = UUID.randomUUID().toString()+extname; //232243343.jpg

            ServletContext servletContext = session.getServletContext();

            String realpath = servletContext.getRealPath("/pics");

            String path =realpath+ "\\adv\\"+iconpath;

            mfile.transferTo(new File(path));

            User user = (User)session.getAttribute(Const.LOGIN_USER);

            advert.setUserid(user.getId());

            advert.setStatus("1");

            advert.setIconpath(iconpath);

            int count = advertService.insertAdvert(advert);

            result.setSuccess(count==1);

        } catch ( Exception e ) {

            e.printStackTrace();

            result.setSuccess(false);

        }
        return result;
    }
}
