package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface AdvertService {
    Advertisement selectByPrimaryKey(Integer id);


    List<Advertisement> selectAll();

//    Page queryPage(Integer pageno, Integer pagesize);

    Page queryPage(Map<String,Object> paramMap);

    int deleteAdvert(Integer id);

    int updateAdvert(Advertisement advertisement);

    int deleteBatchAdvertVo(Data data);

    int insertAdvert(Advertisement advert);
}
