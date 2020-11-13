package com.example.hr.service;

import com.example.hr.model.Position;

import java.util.List;

public interface PositionService {

    List<Position> getAllPositions();

    Integer addPosition(Position position);

    Integer updatePosition(Position position);

    Integer deletePositions(List<Integer> ids);
}
