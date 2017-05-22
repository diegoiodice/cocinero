package com.cocinero.infrastructure.security;


import com.cocinero.domain.User;
import com.cocinero.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    protected final UserRepository userRepository;

    public ShiroRealm(UserRepository userRepository) {
        this.userRepository = userRepository;
        setName("ShiroRealm");
        setCredentialsMatcher(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME));
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        User user = userRepository.findByEmail(token.getUsername());
        if( user != null ) {
            return new SimpleAuthenticationInfo(token.getUsername(), user.getPassword(), getName());
        } else {
            return null;
        }
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.fromRealm(getName()).iterator().next();
        User user = userRepository.findByEmail(userName);
        if( user != null ) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            /*for( Role role : user.getRoles() ) {
                info.addRole(role.getName());
                info.addStringPermissions( role.getPermissions() );
            }*/
            return info;
        } else {
            return null;
        }
    }

}
