package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping("/index")
    public String index() {

        return "process/index";
    }

    @RequestMapping("/showimg")
    public String showimg() {

        return "process/showimg";
    }

    @ResponseBody
    @RequestMapping("/showimgProDef")
    public void showimgProDef(String id, HttpServletResponse response) throws Exception{
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
        InputStream in=repositoryService.getResourceAsStream(pd.getDeploymentId(),pd.getDiagramResourceName());
        ServletOutputStream out=response.getOutputStream();
        IOUtils.copy(in,out);

    }

    @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(Integer pageno, Integer pagesize) {
        AjaxResult result = new AjaxResult();

        try {
            Page page = new Page(pageno, pagesize);
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
            List<ProcessDefinition> listPage = processDefinitionQuery.listPage(page.getStartIndex(), pagesize);
            List<Map<String, Object>> mylistPage = new ArrayList<Map<String, Object>>();
            for (ProcessDefinition processDefinition : listPage) {
                Map<String, Object> pdMap = new HashMap<String, Object>();
                pdMap.put("id", processDefinition.getId());

                pdMap.put("name", processDefinition.getName());

                pdMap.put("key", processDefinition.getKey());

                pdMap.put("version", processDefinition.getVersion());

                mylistPage.add(pdMap);

            }
            int totalsize = (int) processDefinitionQuery.count();
            page.setData(mylistPage);
            page.setTotalsize(totalsize);
            result.setPage(page);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();

        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/deploy")
    public Object deploy(HttpServletRequest req) {
        AjaxResult result = new AjaxResult();

        try {

            MultipartHttpServletRequest request = (MultipartHttpServletRequest) req;

            MultipartFile file = request.getFile("processDefFile");

            repositoryService.createDeployment()
                    .addInputStream(file.getOriginalFilename(), file.getInputStream()).deploy();
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(String id) {
        AjaxResult result = new AjaxResult();

        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(),true);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }
}
