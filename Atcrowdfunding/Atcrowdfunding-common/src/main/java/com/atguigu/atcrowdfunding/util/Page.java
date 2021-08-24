package com.atguigu.atcrowdfunding.util;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;

import java.util.List;
import java.util.Map;

public class Page {

    private Integer pageno;
    private Integer pagesize;
    private List<User> datas;
    private List<Role> roleList;
    private Integer totalsize;
    private Integer totalno;
    private List<Cert> certList;
    private List<Advertisement> advertisementList;
    private List<Map<String, Object>> data;

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public List<Advertisement> getAdvertisementList() {
        return advertisementList;
    }

    public void setAdvertisementList(List<Advertisement> advertisementList) {
        this.advertisementList = advertisementList;
    }

    public List<Cert> getCertList() {
        return certList;
    }

    public void setCertList(List<Cert> certList) {
        this.certList = certList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Page(Integer pageno, Integer pagesize) {
        if (pageno <= 0) {
            this.pageno = 1;
        } else {
            this.pageno = pageno;
        }

        if (pagesize <= 0) {
            this.pagesize = 10;
        } else {
            this.pagesize = pagesize;
        }
    }

    public Integer getPageno() {
        return pageno;
    }

    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public List getDatas() {
        return datas;
    }

    public void setDatas(List datas) {
        this.datas = datas;
    }

    public Integer getTotalsize() {
        return totalsize;
    }

    public void setTotalsize(Integer totalsize) {
        this.totalsize = totalsize;
        this.totalno = (totalsize % pagesize) == 0 ? (totalsize / pagesize) : (totalsize / pagesize + 1);
    }

    public Integer getTotalno() {
        return totalno;
    }

    private void setTotalno(Integer totalno) {
        this.totalno = totalno;
    }

    public Integer getStartIndex() {
        return (this.pageno - 1) * pagesize;
    }
}
