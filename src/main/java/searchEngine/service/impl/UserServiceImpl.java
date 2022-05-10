package searchEngine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import searchEngine.entity.User;
import searchEngine.mapper.UserMapper;
import searchEngine.service.UserService;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/5/10 13:59
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
