package com.example.hr.controller.system.basic;

import com.example.hr.model.Position;
import com.example.hr.model.RespBean;
import com.example.hr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("/")
    public List<Position> getAllPositions(){
        return this.positionService.getAllPositions();
    }

    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        if (this.positionService.addPosition(position) == 1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if (this.positionService.updatePosition(position) == 1){
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @DeleteMapping("/")
    public RespBean deletePositions(@RequestBody List<Integer> ids){
        if (this.positionService.deletePositions(ids) == ids.size()){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
