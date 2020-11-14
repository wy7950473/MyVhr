package com.example.hr.service;

import com.example.hr.model.JobLevel;

import java.util.List;

public interface JobLevelService {

    List<JobLevel> getAllJobLevels();

    Integer addJobLevel(JobLevel jobLevel);

    Integer updateJobLevelById(JobLevel jobLevel);

    Integer deleteJobLevels(List<Integer> ids);

}
