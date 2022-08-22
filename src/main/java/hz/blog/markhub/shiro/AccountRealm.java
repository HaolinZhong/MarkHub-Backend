package hz.blog.markhub.shiro;

import hz.blog.markhub.converter.UserConverter;
import hz.blog.markhub.domain.UserDo;
import hz.blog.markhub.service.UserService;
import hz.blog.markhub.utils.JwtUtils;
import hz.blog.markhub.web.model.UserVo;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountRealm extends AuthorizingRealm {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final UserConverter userConverter;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        JwtToken jwtToken = (JwtToken) authenticationToken;

        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        UserDo userDo = userService.getUserById(Long.valueOf(userId));
        UserVo userVo = userConverter.userDoToVo(userDo);

        if (userDo == null) {
            throw new UnknownAccountException("Account does not exist!");
        }

        if (userDo.getStatus() == -1) {
            throw new LockedAccountException("Account has been locked");
        }

        return new SimpleAuthenticationInfo(userVo, jwtToken.getCredentials(), getName());
    }
}
