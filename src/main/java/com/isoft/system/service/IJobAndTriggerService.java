package com.isoft.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.entity.JobAndTrigger;

public interface IJobAndTriggerService {
    IPage<JobAndTrigger> getJobAndTriggerDetails(Integer pageNum, Integer pageSize);
}
