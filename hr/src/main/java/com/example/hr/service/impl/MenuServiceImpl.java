package com.example.hr.service.impl;

import com.example.hr.dao.MenuMapper;
import com.example.hr.model.Hr;
import com.example.hr.model.Menu;
import com.example.hr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public List<Menu> getMenuByHrId() {
        return this.menuMapper.selectMenuByHrId(((Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }
}
