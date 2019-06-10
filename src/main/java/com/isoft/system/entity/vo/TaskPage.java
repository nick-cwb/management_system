package com.isoft.system.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class TaskPage {
    private Integer total;
    private List<TaskVO> taskVOList;
}
