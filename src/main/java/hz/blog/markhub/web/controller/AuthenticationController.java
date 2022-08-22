package hz.blog.markhub.web.controller;

import hz.blog.markhub.converter.UserConverter;
import hz.blog.markhub.domain.UserDo;
import hz.blog.markhub.exception.ServiceException;
import hz.blog.markhub.exception.ServiceExceptionEnum;
import hz.blog.markhub.service.UserService;
import hz.blog.markhub.utils.JwtUtils;
import hz.blog.markhub.web.model.CommonReturnType;
import hz.blog.markhub.web.model.LoginDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final JwtUtils jwtUtils;
    @Value("${encrypt.salt}")
    private String salt;
    @Value("${encrypt.times}")
    private int times;

    @PostMapping("/login")
    public CommonReturnType login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) throws ServiceException {
        UserDo userDo = userService.getUserByName(loginDto.getUsername());
        if (userDo == null)
            throw new ServiceException(ServiceExceptionEnum.AUTHENTICATION_FAILED, "Incorrect username or password");

        String encryptedPw = new SimpleHash("md5", loginDto.getPassword(), salt, times).toString();
        if (!StringUtils.equals(userDo.getPassword(), encryptedPw)) {
            throw new ServiceException(ServiceExceptionEnum.AUTHENTICATION_FAILED, "Incorrect username or password");
        }

        String jwt = jwtUtils.generateToken(userDo.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        return CommonReturnType.builder().status("success").data(userConverter.userDoToVo(userDo)).build();
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public CommonReturnType logout() {
        SecurityUtils.getSubject().logout();
        return CommonReturnType.builder().status("success").build();
    }
}
