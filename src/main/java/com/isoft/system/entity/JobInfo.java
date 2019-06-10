package com.isoft.system.entity;

import lombok.Data;

@Data
public class JobInfo {

    private String jobClassName;
    private String jobGroupName;
    private String cronExpression;
    private String jobType;
    private Integer timeType;
    private String description;

    public String getJobClassName() {
        return "com.isoft.system.jobs."+jobClassName.trim();
    }

}
