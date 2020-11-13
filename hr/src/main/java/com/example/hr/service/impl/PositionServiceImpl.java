package com.example.hr.service.impl;

import com.example.hr.dao.PositionMapper;
import com.example.hr.model.Position;
import com.example.hr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public List<Position> getAllPositions() {
        return this.positionMapper.selectAllPositions();
    }

    @Override
    public Integer addPosition(Position position) {
        position.setEnabled(Boolean.TRUE);
        position.setCreateDate(new Date());
        return this.positionMapper.insertSelective(position);
    }

    @Override
    public Integer updatePosition(Position position) {
        return this.positionMapper.updateByPrimaryKeySelective(position);
    }

    @Override
    @Transactional
    public Integer deletePositions(List<Integer> ids) {
        return this.positionMapper.deleteByIds(ids);
    }
}
