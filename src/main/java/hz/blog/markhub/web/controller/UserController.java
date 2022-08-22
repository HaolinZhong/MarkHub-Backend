package hz.blog.markhub.web.controller;

import hz.blog.markhub.converter.UserConverter;
import hz.blog.markhub.domain.UserDo;
import hz.blog.markhub.exception.ServiceException;
import hz.blog.markhub.exception.ServiceExceptionEnum;
import hz.blog.markhub.mapper.UserDoMapper;
import hz.blog.markhub.service.UserService;
import hz.blog.markhub.web.model.CommonReturnType;
import hz.blog.markhub.web.model.RegisterDto;
import hz.blog.markhub.web.model.UserVo;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @Value("${encrypt.salt}")
    private String salt;
    @Value("${encrypt.times}")
    private int times;

    private final String DEFAULT_AVATAR = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png";

    @GetMapping("/get")
    @ResponseBody
    public CommonReturnType getUserById(@RequestParam("id") Long userId) {
        UserDo userDo = userService.getUserById(userId);
        UserVo userVo = userConverter.userDoToVo(userDo);
        return CommonReturnType.builder().status("success").data(userVo).build();
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public CommonReturnType createUser(@Validated @RequestBody RegisterDto registerDto) throws ServiceException {

        if (!StringUtils.equals(registerDto.getPassword(), registerDto.getConfPassword())) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_PARAMETER,
                    "Two entered passwords are different.");
        }

        UserDo userDo = userService.getUserByName(registerDto.getUsername());
        if (userDo != null) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_PARAMETER,
                    "Input user name has already been used. Please try another name.");
        }

        UserDo newUser = new UserDo();
        newUser.setName(registerDto.getUsername());

        String encryptedPw = new SimpleHash("md5", registerDto.getPassword(), salt, times).toString();
        newUser.setPassword(encryptedPw);

        if (registerDto.getAvatar() != null && !registerDto.getAvatar().isBlank()) {
            newUser.setAvatar(registerDto.getAvatar());
        } else {
            newUser.setAvatar(DEFAULT_AVATAR);
        }

        newUser.setCreatedAt(new Date());
        newUser.setStatus(0);

        userService.registerNewUser(newUser);

        return CommonReturnType.builder().status("success").build();
    }
}
