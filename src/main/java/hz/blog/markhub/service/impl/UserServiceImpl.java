package hz.blog.markhub.service.impl;

import hz.blog.markhub.domain.UserDo;
import hz.blog.markhub.exception.ServiceException;
import hz.blog.markhub.exception.ServiceExceptionEnum;
import hz.blog.markhub.mapper.UserDoMapper;
import hz.blog.markhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserDoMapper userDoMapper;

    @Override
    public UserDo getUserById(Long userId) {

        UserDo userDo = userDoMapper.selectByPrimaryKey(userId);

        return userDo;
    }

    @Override
    public UserDo getUserByName(String name) {
        UserDo userDo = userDoMapper.selectByUserName(name);
        return userDo;
    }

    @Transactional
    @Override
    public void registerNewUser(UserDo newUser) throws ServiceException {
        int res = userDoMapper.insertSelective(newUser);
        if (res != 1) throw new ServiceException(ServiceExceptionEnum.UNKNOWN_ERROR, "registration failed");
    }
}
