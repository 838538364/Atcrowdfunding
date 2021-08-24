package com.atguigu.atcrowdfunding.vo;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.atcrowdfunding.bean.*;

public class Data {

    private List<User> userList = new ArrayList<User>();
    private List<User> datas = new ArrayList<User>();
    private List<Role> roleList = new ArrayList<Role>();
    private List<Cert> certList=new ArrayList<Cert>();
    private List<Advertisement> advertisementList=new ArrayList<Advertisement>();
    private List<MemberCert> certimgs=new ArrayList<MemberCert>();

    public List<MemberCert> getCertimgs() {
        return certimgs;
    }

    public void setCertimgs(List<MemberCert> certimgs) {
        this.certimgs = certimgs;
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

	private List<Integer> ids;


    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getDatas() {
        return datas;
    }

    public void setDatas(List<User> datas) {
        this.datas = datas;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }





}
