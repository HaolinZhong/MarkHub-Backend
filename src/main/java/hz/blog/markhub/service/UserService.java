package hz.blog.markhub.service;

import hz.blog.markhub.domain.UserDo;
import hz.blog.markhub.exception.ServiceException;

public interface UserService {
    UserDo getUserById(Long userId);

    UserDo getUserByName(String name);

    void registerNewUser(UserDo newUser) throws ServiceException;
}
