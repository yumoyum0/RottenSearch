package searchEngine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngine.entity.User;
import searchEngine.mapper.UserMapper;
import searchEngine.pojo.Result;
import searchEngine.service.UserService;

import javax.annotation.Resource;

/**
 * @Author: WindPo
 * @Description: TODO
 * @DateTime: 2022/5/10 13:59
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private  UserMapper userMapper;

    @Override
    public Result login(User user) {
        Result result = new Result();
        try {
            final User userDB = userMapper.login(user.getUsername(), user.getPassword());
            if(userDB==null){
                result=Result.failure("用户名或密码错误");
            }else {
                result=Result.success(userDB.getId());
            }
        }catch (Exception e){
            result=Result.failure(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result regist(User user) {
        Result result = new Result();
        try {
            //判断数据库中是否存在用户
            final User existUser = userMapper.queryUserByusername(user.getUsername());
            if (existUser!=null){
                result=Result.failure("用户已存在");
            }else {
                //注册用户
                userMapper.regist(user);
                result=Result.success(null);
            }
        }catch (Exception e){
            result=Result.failure(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
