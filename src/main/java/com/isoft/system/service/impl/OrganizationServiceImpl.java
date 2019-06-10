package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.Organization;
import com.isoft.system.entity.dto.OrganizationDTO;
import com.isoft.system.enums.BusinessExceptionEnum;
import com.isoft.system.exception.BusinessException;
import com.isoft.system.mapper.OrganizationMapper;
import com.isoft.system.service.IOrganizationService;
import com.isoft.system.utils.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@DS("master")
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper,Organization> implements IOrganizationService {

    //0为根目录id
    private static Integer ROOT_ID = 0;

    private static String ORG_CODE = "org_code";
    private static String ORG_NAME = "org_name";


    @Override
    public IPage<Organization> findAll(OrganizationDTO organizationDTO) {
        QueryWrapper<Organization> qw = new QueryWrapper<Organization>();
        if(organizationDTO.getOrgCode() != null && !"".equals(organizationDTO.getOrgCode())){
            qw.like(ORG_CODE,organizationDTO.getOrgCode());
        }
        if(organizationDTO.getOrgName() != null && !"".equals(organizationDTO.getOrgName())){
            qw.like(ORG_NAME,organizationDTO.getOrgName());
        }
        IPage<Organization> organizationPage = baseMapper.selectPage(organizationDTO,qw);
        return organizationPage;
    }

    @Override
    public Object findOrganizationTree() {
        //查询所有组织
        List<Organization> organizationList = baseMapper.selectList(null);
        if(organizationList.size()==0){
            return null;
        }
        //调用递归算法生成树状机构
        Organization organization = getOrganizationTree(ROOT_ID,organizationList);
        return organization;
    }

    @Override
    public boolean saveOrganization(Organization organization) throws Exception {
        //添加组织前先查看组织编码是否已存在
        Organization org = baseMapper.selectOne(new QueryWrapper<Organization>().eq(ORG_CODE,organization.getOrgCode()));
        if(org != null){
            throw new BusinessException(BusinessExceptionEnum.ORGANIZATION_EXIST.value());
        }
        boolean flag = false;
        organization.setCreatedAt(Util.createTimestamp());
        int num = baseMapper.insert(organization);
        if(num>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateOrganization(Organization organization) throws Exception {
        boolean flag = false;
        organization.setLastUpdate(Util.createTimestamp());
        int num = baseMapper.updateById(organization);
        if(num>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 递归获取机构树
     * @param rootId
     * @param organizationList
     * @return
     */
    private static Organization getOrganizationTree(Integer rootId,List<Organization> organizationList){
        //根据id获取根节点对象
        Organization node = getRootNode(rootId,organizationList);
        //查询根下的所有子节点
        List<Organization> childTreeNodes = getChildNode(rootId, organizationList);
        //遍历子节点
        for(Organization child : childTreeNodes){
            Organization n = getOrganizationTree(child.getId(),organizationList); //递归
            if(node.getChildren() == null){
                node.setChildren(new ArrayList<Organization>());
            }
            node.getChildren().add(n);
        }
        return node;
    }

    /**
     * 获取根节点
     * @param rootId
     * @param organizationList
     * @return
     */
    private static Organization getRootNode(Integer rootId,List<Organization> organizationList){
        for (Organization organization:organizationList) {
            if(organization.getId() == rootId){
                return organization;
            }
        }
        return null;
    }

    /**
     * 获取子节点
     * @param parentId
     * @param organizationList
     * @return
     */
    private static List<Organization> getChildNode(Integer parentId,List<Organization> organizationList){
        List<Organization> organizations = new LinkedList<Organization>();
        for (Organization organization:organizationList) {
            if(organization.getParentId() == parentId){
                organizations.add(organization);
            }
        }
        return organizations;
    }

}
