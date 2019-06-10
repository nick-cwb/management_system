package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.entity.BaseDataOption;
import com.isoft.system.entity.dto.BaseDataDTO;
import com.isoft.system.enums.BusinessExceptionEnum;
import com.isoft.system.exception.BusinessException;
import com.isoft.system.mapper.BaseDataOptionMapper;
import com.isoft.system.service.IBaseDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@DS("master")
public class BaseDataServiceImpl implements IBaseDataService {

    private static String NAME = "name";
    private static String CODE = "option_code";

    @Resource
    BaseDataOptionMapper optionMapper;



    @Override
    public List<BaseDataOption> findOption(Integer dataId) {
        List<BaseDataOption> options = optionMapper.selectList(new QueryWrapper<BaseDataOption>().eq("parent_id",dataId).eq("is_del",0).orderByAsc("sort"));
        return options;
    }

    @Override
    public boolean saveOption(BaseDataOption dataOption) {

        //当parentid为0时保存前先校验code是否已经存在
        if (dataOption.getParentId()!=null && dataOption.getParentId().intValue() == 0){
            BaseDataOption baseDataOption = optionMapper.selectOne(new QueryWrapper<BaseDataOption>().eq("code",dataOption.getCode()).eq("parent_id",0).eq("is_del",0));
            if (baseDataOption!=null){
                throw new BusinessException(BusinessExceptionEnum.DATA_CODE_EXIST.value());
            }
        }

        int num = optionMapper.insert(dataOption);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updOption(BaseDataOption dataOption) {
        int num = optionMapper.updateById(dataOption);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean delOption(Integer id) {
        int num = optionMapper.deleteById(id);
        if(num > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public IPage<BaseDataOption> findAll(BaseDataDTO baseDataDTO) {
        QueryWrapper<BaseDataOption> qw = new QueryWrapper<BaseDataOption>();
        if (baseDataDTO.getName()!=null && !"".equals(baseDataDTO.getName())){
            qw.like("name",baseDataDTO.getName());
        }
        if (baseDataDTO.getOptionCode()!=null && !"".equals(baseDataDTO.getOptionCode())){
            qw.like("code",baseDataDTO.getOptionCode());
        }
        qw.eq("parent_id",0);
        qw.orderByAsc("sort");
        IPage<BaseDataOption> page = optionMapper.selectPage(baseDataDTO,qw);
        return page;
    }

    @Override
    public List<BaseDataOption> findOptionByCode(String codes) {

        return null;
    }

}
