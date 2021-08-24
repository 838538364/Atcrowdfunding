package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.dao.AdvertisementMapper;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Override
    public int insertAdvert(Advertisement advert) {
        return advertisementMapper.insert(advert);
    }

    @Override
    public int deleteBatchAdvertVo(Data data) {
        return advertisementMapper.deleteUserByBatchAdverts(data);
    }

    @Override
    public int updateAdvert(Advertisement advertisement) {
        return advertisementMapper.updateByPrimaryKey(advertisement);
    }

    @Override
    public int deleteAdvert(Integer id) {
        return advertisementMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Page queryPage(Map<String,Object> paramMap) {
        Page page=new Page((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
        Integer startIndex=page.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Advertisement> advertisementList=advertisementMapper.queryList(paramMap);
        page.setAdvertisementList(advertisementList);

        Integer count=advertisementMapper.queryCount(paramMap);
        page.setTotalsize(count);
        return page;
    }

//    @Override
//    public Page queryPage(Integer pageno, Integer pagesize) {
//        Page page=new Page(pageno,pagesize);
//        Integer startIndex=page.getStartIndex();
//        List<Advertisement> advertisementList=advertisementMapper.queryList(startIndex,pagesize);
//        page.setAdvertisementList(advertisementList);
//        Integer count=advertisementMapper.queryCount();
//		page.setTotalsize(count);
//        return page;
//    }

    @Override
    public List<Advertisement> selectAll() {
        return advertisementMapper.selectAll();
    }

    @Override
    public Advertisement selectByPrimaryKey(Integer id) {
        return advertisementMapper.selectByPrimaryKey(id);
    }
}
