package com.isoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.Position;
import com.isoft.system.entity.dto.PositionDTO;

public interface IPositionService extends IService<Position> {

    //分页查询所有岗位
    IPage<Position> findAll(PositionDTO positionDTO);

    //新增岗位
    boolean savePosition(Position position) throws Exception;

    //修改岗位
    boolean updatePosition(Position position) throws Exception;

}
