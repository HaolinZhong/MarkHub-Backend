package hz.blog.markhub.converter;

import hz.blog.markhub.domain.UserDo;
import hz.blog.markhub.web.model.UserVo;
import org.mapstruct.Mapper;

@Mapper
public interface UserConverter {
    UserVo userDoToVo(UserDo userDo);
}
