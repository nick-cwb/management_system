package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.VacationDays;
import com.isoft.system.mapper.VacationDaysMapper;
import com.isoft.system.service.IVacationDaysService;
import org.springframework.stereotype.Service;

@Service
@DS("master")
public class VacationDaysServiceImpl extends ServiceImpl<VacationDaysMapper,VacationDays> implements IVacationDaysService {
}
