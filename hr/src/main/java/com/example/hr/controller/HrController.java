package com.example.hr.controller;

import com.example.hr.model.Hr;
import com.example.hr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HrController {

    @Autowired
    private HrService hrService;

    @RequestMapping("/hr")
    public Hr selectOne(){
        return this.hrService.selectOne();
    }

}
