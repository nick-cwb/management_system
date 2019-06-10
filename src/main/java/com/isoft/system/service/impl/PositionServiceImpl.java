package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.Position;
import com.isoft.system.entity.dto.PositionDTO;
import com.isoft.system.enums.BusinessExceptionEnum;
import com.isoft.system.exception.BusinessException;
import com.isoft.system.mapper.PositionMapper;
import com.isoft.system.service.IPositionService;
import com.isoft.system.utils.Util;
import org.springframework.stereotype.Service;


@Service
@DS("master")
public class PositionServiceImpl extends ServiceImpl<PositionMapper,Position> implements IPositionService {

    private static String POSI_CODE = "posi_code";
    private static String POSI_NAME = "posi_name";

    @Override
    public IPage<Position> findAll(PositionDTO positionDTO) {
        QueryWrapper<Position> qw = new QueryWrapper<Position>();
        if(positionDTO.getPosiCode() != null && !"".equals(positionDTO.getPosiCode())){
            qw.like(POSI_CODE,positionDTO.getPosiCode());
        }
        if(positionDTO.getPosiName() != null && !"".equals(positionDTO.getPosiName())){
            qw.like(POSI_NAME,positionDTO.getPosiName());
        }
        IPage<Position> positionPage = baseMapper.selectPage(positionDTO,qw);
        return positionPage;
    }

    @Override
    public boolean savePosition(Position position) throws Exception {
        //添加前校验岗位编码查看是否已存在该岗位
        Position posi = baseMapper.selectOne(new QueryWrapper<Position>().eq(POSI_CODE,position.getPosiCode()));
        if(posi != null){
            throw new BusinessException(BusinessExceptionEnum.POSITION_EXIST.value());
        }
        position.setCreatedAt(Util.createTimestamp());
        int num = baseMapper.insert(position);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updatePosition(Position position) throws Exception {
        position.setLastUpdate(Util.createTimestamp());
        int num = baseMapper.updateById(position);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }
}
