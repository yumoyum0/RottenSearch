package searchEngine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    private UserMapper userMapper;
    @Override
    public Result<String> login(User user) {
        //初始化返回状态
        Result<String> result = new Result<String>();
        result.setSuccess(false);
        result.setData(null);
        result.setCode(100);
        result.setErrMsg(null);
        try {
            final User userDB = userMapper.login(user.getUsername(), user.getPassword());
            if(userDB.getId()==null){
                result.setErrMsg("用户名或密码错误");
                result.setCode(401);
            }else {
                result.setSuccess(true);
                result.setData("token");
                result.setCode(200);
            }
        }catch (Exception e){
            result.setErrMsg(e.getMessage());
            result.setCode(400);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result<String> regist(User user) {
        final Result<String> result = new Result<>();
        //初始化返回状态
        result.setSuccess(false);
        result.setErrMsg(null);
        result.setCode(100);
        result.setData(null);
        try {
            //判断数据库中是否存在用户
            final User existUser = userMapper.queryUserByusername(user.getUsername());
            if (existUser!=null){
                result.setErrMsg("用户已存在");
                result.setCode(401);
            }else {
                //注册用户
                userMapper.regist(user);
                result.setSuccess(true);
                result.setCode(200);
            }
        }catch (Exception e){
            result.setErrMsg(e.getMessage());
            result.setCode(400);
            e.printStackTrace();
        }
        return result;
    }
}
