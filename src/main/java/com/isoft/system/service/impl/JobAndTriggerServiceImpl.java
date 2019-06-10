package com.isoft.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.JobAndTrigger;
import com.isoft.system.mapper.JobMapper;
import com.isoft.system.service.IJobAndTriggerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class JobAndTriggerServiceImpl implements IJobAndTriggerService {

    @Resource
    private JobMapper jobMapper;

    @Override
    public IPage<JobAndTrigger> getJobAndTriggerDetails(Integer pageNum, Integer pageSize) {
        Page<JobAndTrigger> pageIn = new Page<JobAndTrigger>().setSize(pageSize).setCurrent(pageNum);
                List<JobAndTrigger> list = jobMapper.getJobAndTriggerDetails(pageIn);
        long total = jobMapper.countJobAndTriggerDetails();
        Page<JobAndTrigger> page = new Page<JobAndTrigger>().setRecords(list).setTotal(total);
        return page;
    }
}
