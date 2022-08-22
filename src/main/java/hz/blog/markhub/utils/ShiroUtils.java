package hz.blog.markhub.utils;

import hz.blog.markhub.web.model.UserVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

public class ShiroUtils {

    public static UserVo getProfile() {
        return (UserVo) SecurityUtils.getSubject().getPrincipal();
    }
}
