package pro.onlyou.apache.shiro.boot.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义自己的校验规则
 */

public class MyCredentialMatcher extends SimpleCredentialsMatcher {

    static final Logger logger = LoggerFactory.getLogger("shiro.debug");

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken =
                (UsernamePasswordToken) token;
        logger.debug("初始化CredentialsMatcher自定义验证规则　" + logger);
        String password = new String(usernamePasswordToken.getPassword());
        String rightPassword = (String) info.getCredentials();

        return this.equals(password,rightPassword);
    }
}
