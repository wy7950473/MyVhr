package com.example.hr.service.impl;

import com.example.hr.dao.JobLevelMapper;
import com.example.hr.model.JobLevel;
import com.example.hr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class JobLevelServiceImpl implements JobLevelService {

    @Autowired
    private JobLevelMapper jobLevelMapper;

    @Override
    public List<JobLevel> getAllJobLevels() {
        return this.jobLevelMapper.selectAllJobLevels();
    }

    @Override
    public Integer addJobLevel(JobLevel jobLevel) {
        jobLevel.setCreateDate(new Date());
        jobLevel.setEnabled(Boolean.TRUE);
        return this.jobLevelMapper.insertSelective(jobLevel);
    }

    @Override
    public Integer updateJobLevelById(JobLevel jobLevel) {
        return this.jobLevelMapper.updateByPrimaryKeySelective(jobLevel);
    }

    @Override
    @Transactional
    public Integer deleteJobLevels(List<Integer> ids) {
        return this.jobLevelMapper.deleteJobLevels(ids);
    }
}
