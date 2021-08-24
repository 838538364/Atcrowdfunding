package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);

    List<Advertisement> selectAdvertVo(Data data);

//    List<Advertisement> queryList(@Param("startIndex")Integer startIndex, @Param("pagesize")Integer pagesize);

    Integer queryCount(Map<String, Object> paramMap);

    List<Advertisement> queryList(Map<String, Object> paramMap);

    int deleteUserByBatchAdverts(Data data);
}