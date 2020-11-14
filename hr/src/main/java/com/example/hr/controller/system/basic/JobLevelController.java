package com.example.hr.controller.system.basic;

import com.example.hr.model.JobLevel;
import com.example.hr.model.RespBean;
import com.example.hr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {

    @Autowired
    private JobLevelService jobLevelService;

    @GetMapping("/")
    public List<JobLevel> getAllJobLevels(){
        return this.jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel jobLevel){
        if (this.jobLevelService.addJobLevel(jobLevel) == 1) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody JobLevel jobLevel){
        if (this.jobLevelService.updateJobLevelById(jobLevel) == 1){
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevels(@RequestBody List<Integer> ids){
        if (this.jobLevelService.deleteJobLevels(ids) == ids.size()){
            return RespBean.ok("删除成功");
        }
        return  RespBean.error("删除失败");
    }
}
