package com.isoft.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.JobAndTrigger;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface JobMapper {

    @Select("SELECT DISTINCT " +
            "QRTZ_JOB_DETAILS.JOB_NAME, " +
            "QRTZ_JOB_DETAILS.JOB_GROUP, " +
            "QRTZ_JOB_DETAILS.JOB_CLASS_NAME, " +
            "QRTZ_JOB_DETAILS.DESCRIPTION, " +
            "QRTZ_TRIGGERS.TRIGGER_NAME, " +
            "QRTZ_TRIGGERS.TRIGGER_GROUP, " +
            "QRTZ_TRIGGERS.TRIGGER_STATE, " +
            "QRTZ_CRON_TRIGGERS.CRON_EXPRESSION, " +
            "QRTZ_CRON_TRIGGERS.TIME_ZONE_ID " +
            "FROM QRTZ_JOB_DETAILS " +
            "INNER JOIN QRTZ_TRIGGERS ON QRTZ_TRIGGERS.TRIGGER_GROUP=QRTZ_JOB_DETAILS.JOB_GROUP " +
            "INNER JOIN QRTZ_CRON_TRIGGERS ON QRTZ_JOB_DETAILS.JOB_NAME = QRTZ_TRIGGERS.JOB_NAME " +
            "and QRTZ_TRIGGERS.TRIGGER_NAME = QRTZ_CRON_TRIGGERS.TRIGGER_NAME " +
            "and QRTZ_TRIGGERS.TRIGGER_GROUP = QRTZ_CRON_TRIGGERS.TRIGGER_GROUP ")
    List<JobAndTrigger> getJobAndTriggerDetails(Page<JobAndTrigger> page);

    @Select("SELECT Count(0) " +
            "FROM QRTZ_JOB_DETAILS " +
            "INNER JOIN QRTZ_TRIGGERS ON QRTZ_TRIGGERS.TRIGGER_GROUP=QRTZ_JOB_DETAILS.JOB_GROUP " +
            "INNER JOIN QRTZ_CRON_TRIGGERS ON QRTZ_JOB_DETAILS.JOB_NAME = QRTZ_TRIGGERS.JOB_NAME " +
            "and QRTZ_TRIGGERS.TRIGGER_NAME = QRTZ_CRON_TRIGGERS.TRIGGER_NAME " +
            "and QRTZ_TRIGGERS.TRIGGER_GROUP = QRTZ_CRON_TRIGGERS.TRIGGER_GROUP ")
    long countJobAndTriggerDetails();


}
