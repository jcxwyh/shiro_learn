package pro.onlyou.apache.shiro.boot.security;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import pro.onlyou.apache.shiro.boot.model.Permission;
import pro.onlyou.apache.shiro.boot.model.Role;
import pro.onlyou.apache.shiro.boot.model.User;
import pro.onlyou.apache.shiro.boot.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 自定义实现Realm
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User)principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        // 获取用户的权限列表
        List<String> permissions = new ArrayList<>();
        // 获取用户的角色列表
        List<String> roleNameList = new ArrayList<>();

        Set<Role> roles = user.getRoles();

        if(CollectionUtils.isNotEmpty(roles)){
            roles.parallelStream().forEach(role -> {
                // 将角色名称放入角色列表中
                roleNameList.add(role.getRname());

                Set<Permission> tempPermissions = role.getPermissions();
                if(CollectionUtils.isNotEmpty(tempPermissions)){
                    // 遍历将权限名称添加到List
                    tempPermissions.parallelStream().forEach(tempP -> {
                        permissions.add(tempP.getPname());
                    });
                }
            });
        }

        // 创建授权信息对象
        SimpleAuthorizationInfo authorizationInfo =
                new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roleNameList);
        authorizationInfo.addStringPermissions(permissions);

        return authorizationInfo;
    }

    /**
     * 认证登陆
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken =
                (UsernamePasswordToken)authenticationToken;

        String username = usernamePasswordToken.getUsername();
        User user = userService.findByUsername(username);

        return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());
    }
}
