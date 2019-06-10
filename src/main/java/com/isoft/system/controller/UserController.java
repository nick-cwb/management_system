package com.isoft.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.annotation.LogAnnotation;
import com.isoft.system.common.BaseController;
import com.isoft.system.entity.Position;
import com.isoft.system.entity.Role;
import com.isoft.system.entity.SysLogs;
import com.isoft.system.entity.User;
import com.isoft.system.entity.dto.*;
import com.isoft.system.enums.ResultEnum;
import com.isoft.system.service.IEmpPositionService;
import com.isoft.system.service.IOrgEmpService;
import com.isoft.system.service.IPartyAuthService;
import com.isoft.system.service.IPositionService;
import com.isoft.system.service.IRoleService;
import com.isoft.system.service.ISysLogService;
import com.isoft.system.service.IUserService;

import io.swagger.annotations.ApiOperation;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

/**
 *   用到shiro的三种情况的注解
 *   需要认证校验  @RequiresAuthentication
 *   需要校验用户角色  @RequiresRoles("admin")
 *   需要校验角色权限  @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Resource
    IUserService service;

    @Resource
    IEmpPositionService empPositionService;
    
    @Resource
    IOrgEmpService orgEmpService;
    
    @Resource
    IPartyAuthService partyAuthService;
    
    @Resource
    ISysLogService sysLogService;
    
    @Resource
    IRoleService roleService;
    
    @Resource
    IPositionService positionService;
    
    /**
     * 查询全部用户,分页
     * @param request
     * @param response
     * @return
     */
    /*@LogAnnotation
    @ApiOperation("查找所有用户")*/
//    @RequiresAuthentication
    @GetMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAll(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO){
        IPage<User> userPage = service.findAll(userDTO);
        return renderSuccess(userPage);
    }


    /**
     * 查询单个用户
     * @param request
     * @param response
     * @param id
     * @return
     */
    @GetMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findUser(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id){
        User user = service.getById(id);
        if(user == null){
            return renderComplete(ResultEnum.NONE_RESULT.value());
        }
        return renderSuccess(user);
    }

    /**
     * 新增用户
     * @param request
     * @param response
     * @param user
     * @return
     */
    @LogAnnotation
    @ApiOperation("添加用户")
    @PostMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object addUser(HttpServletRequest request, HttpServletResponse response,@RequestBody User user) throws Exception {
        boolean result = service.saveUser(user);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }

    /**
     * 修改用户
     * @param request
     * @param response
     * @param user
     * @return
     */
    @LogAnnotation
    @ApiOperation("修改用户")
    @PutMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updateUser(HttpServletRequest request, HttpServletResponse response,@RequestBody User user) throws Exception{
        boolean result = service.updateUser(user);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }

    /**
     * 修改用户密码
     * @param request
     * @param response
     * @param passwordDTO
     * @return
     */
    @LogAnnotation
    @ApiOperation("修改用户密码")
    @PutMapping(value = "/password",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updatePassword(HttpServletRequest request, HttpServletResponse response,@RequestBody PasswordDTO passwordDTO)throws Exception{
        boolean result = service.updatePasswor(passwordDTO);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(ResultEnum.PASS_MODIFY_SUCCESS.value());
    }


    /**
     * 删除用户
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("删除用户")
    @DeleteMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteUser(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id){
        boolean result = service.removeById(id);
        if(!result){
            return renderError(ResultEnum.DELETE_FAILURE.value());
        }
        return renderSuccess(ResultEnum.DELETE_SUCCESS.value());
    }


    /**
     * 根据用户id获取用户岗位
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("根据用户id获取用户岗位")
    @GetMapping(value="/position/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findUserPosition(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id){

    	Integer[] positionIds = empPositionService.findUserPosition(id);
       	List<PositionNameDTO> result = new ArrayList<PositionNameDTO>();
        if(positionIds == null){
            return renderComplete(ResultEnum.NONE_RESULT.value());
        }else {
        	for(int positionId:positionIds) {
        		 Position position = positionService.getById(positionId);
        		 PositionNameDTO positionNameDTO = new PositionNameDTO();
        		 positionNameDTO.setId(position.getId());
        		 positionNameDTO.setPosiName(position.getPosiName());
        		 result.add(positionNameDTO);
        	}
        }
        return renderSuccess(result);
    }

    /**
     * 根据用户id编辑用户岗位
     * @param request
     * @param response
     * @param empPositionDTO
     * @return
     * @throws Exception 
     */
    @LogAnnotation
    @ApiOperation("根据用户id编辑用户岗位")
    @PostMapping(value="/position/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updateUserPosition(HttpServletRequest request, HttpServletResponse response,@RequestBody EmpPositionResourceDTO empPositionDTO) throws Exception{

        boolean result = empPositionService.updateUserPosition(empPositionDTO);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }


    /**
     * 根据用户id获取用户机构
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("根据用户id获取用户机构")
    @GetMapping(value="/organization/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findUserOrganization(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id){

    	Integer[] orgIds = orgEmpService.findUserOrganization(id);
        if(orgIds == null){
            return renderComplete(ResultEnum.NONE_RESULT.value());
        }
        return renderSuccess(orgIds);
    }

    /**
     * 根据用户id获编辑用户机构
     * @param request
     * @param response
     * @param orgEmpDTO
     * @return
     * @throws Exception 
     */
    @LogAnnotation
    @ApiOperation("根据用户id获编辑用户机构")
    @PostMapping(value="/organization/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updateUserOrganization(HttpServletRequest request, HttpServletResponse response,@RequestBody OrgEmpResourceDTO orgEmpDTO) throws Exception{

        boolean result = orgEmpService.updateUserOrganization(orgEmpDTO);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }


    /**
     * 根据参与的对象查找角色
     * @param request
     * @param response
     * @param partyAuthDTO
     * @return
     */
    @LogAnnotation
    @ApiOperation("根据参与的对象查找角色")
    @GetMapping(value="/role",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findUserRole(HttpServletRequest request, HttpServletResponse response, PartyAuthDTO partyAuthDTO){

       	Integer[] roleIds = partyAuthService.findUserRole(partyAuthDTO);
       	List<RoleNameDTO> result = new ArrayList<RoleNameDTO>();
        if(roleIds == null){
            return renderComplete(ResultEnum.NONE_RESULT.value());
        }else {
        	for(int id:roleIds) {
        		 Role role = roleService.getById(id);
        		 RoleNameDTO roleNameDTO = new RoleNameDTO();
        		 roleNameDTO.setRoleId(id);
        		 roleNameDTO.setRoleDesc(role.getRoleDesc());
        		 result.add(roleNameDTO);
        	}
        }
        return renderSuccess(result);
    }

    /**
     * 根据用户id获编辑用户角色
     * @param request
     * @param response
     * @param resDTO
     * @return
     * @throws Exception 
     */
    @LogAnnotation
    @ApiOperation("根据用户id获编辑用户角色")
    @PostMapping(value="/role/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updateUserRole(HttpServletRequest request, HttpServletResponse response,@RequestBody PartyAuthResourceDTO resDTO) throws Exception{
        boolean result = partyAuthService.updateUserRole(resDTO);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }

    
    /**
     * 查询全部日志,分页
     * @param request
     * @param response
     * @return
     */
    //@ApiOperation("查找所有日志")
    @GetMapping(value="/sysLogs",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAllLog(HttpServletRequest request, HttpServletResponse response, @ModelAttribute SysLogsDTO sysLogsDTO){
        IPage<SysLogs> sysPage = sysLogService.findAll(sysLogsDTO);
        return renderSuccess(sysPage);
    }
    
    /**
     * 根据日志id获取日志
     * @param request
     * @param response
     * @param id
     * @return
     * @throws Exception 
     */
    //@ApiOperation("根据日志id获取日志")
    @GetMapping(value="/sysLogs/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findLogById(HttpServletRequest request, HttpServletResponse response,@PathVariable int id) throws Exception{
    	 SysLogs sysLogs = sysLogService.getLogById(id);
         return renderSuccess(sysLogs);
    }
}
