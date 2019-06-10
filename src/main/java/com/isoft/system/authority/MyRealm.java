package com.isoft.system.authority;

import com.isoft.system.entity.AuthButton;
import com.isoft.system.entity.Role;
import com.isoft.system.entity.User;
import com.isoft.system.entity.vo.RoleResourceVO;
import com.isoft.system.service.IAuthService;
import com.isoft.system.service.IRoleService;
import com.isoft.system.service.IUserService;
import com.isoft.system.utils.JwtHelper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyRealm extends AuthorizingRealm {

    private static Logger log = LoggerFactory.getLogger(MyRealm.class);

    @Resource
    IUserService userService;

    @Resource
    IAuthService authService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户id
        String userId = JwtHelper.getUserId(principals.toString());
        //根据用户id获取用户角色
        List<Role> roleList = authService.findUserRole(Integer.valueOf(userId));
        Set<String> roles = new HashSet<String>();
        for (Role role:roleList){
            roles.add(role.getRoleCode());
        }
        //根据角色获取用户权限
        RoleResourceVO resourceVO = (RoleResourceVO) authService.findRoleAuth(Integer.valueOf(userId));
        List<AuthButton> permissionList = resourceVO.getButtonList();
        Set<String> permissions = new HashSet<>();
        for (AuthButton permision:permissionList) {
            permissions.add(permision.getBtnCode());
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        //解密token获取用户id
        String userId = JwtHelper.getUserId(token);
        if (userId == null) {
            throw new AuthenticationException("token内容不合法");
        }
        //查询用户信息
        User user = userService.getById(userId);
        if (user == null){
            throw new AuthenticationException("用户不存在");
        }
        //JWT校验token是否合法
        boolean flag = false;
        try {
            flag = JwtHelper.verifyJWT(token,userId,user.getPassword());
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
        if (! flag) {
            throw new AuthenticationException("token校验不合法");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
