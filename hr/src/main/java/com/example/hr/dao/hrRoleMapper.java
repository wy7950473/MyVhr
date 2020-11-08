package com.example.hr.dao;

import com.example.hr.model.hrRole;

public interface hrRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(hrRole record);

    int insertSelective(hrRole record);

    hrRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(hrRole record);

    int updateByPrimaryKey(hrRole record);
}