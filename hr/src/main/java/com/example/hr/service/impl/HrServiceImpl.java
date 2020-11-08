package com.example.hr.service.impl;

import com.example.hr.dao.HrMapper;
import com.example.hr.model.Hr;
import com.example.hr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HrServiceImpl implements HrService {

    @Autowired
    private HrMapper hrMapper;

    @Override
    public Hr selectOne() {
        return hrMapper.selectByPrimaryKey(3);
    }
}
