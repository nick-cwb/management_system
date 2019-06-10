package com.isoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.Organization;
import com.isoft.system.entity.dto.OrganizationDTO;

public interface IOrganizationService extends IService<Organization> {

    //分页查询所有机构
    IPage<Organization> findAll(OrganizationDTO organizationDTO);

    //查询树形机构
    Object findOrganizationTree();

    //新增机构
    boolean saveOrganization(Organization organization) throws Exception;

    //修改机构
    boolean updateOrganization(Organization organization) throws Exception;

}
