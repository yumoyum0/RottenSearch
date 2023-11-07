package searchEngine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.Cookie;
import org.springframework.stereotype.Service;
import searchEngine.entity.User;
import searchEngine.mapper.UserMapper;
import searchEngine.pojo.Result;
import searchEngine.service.UserService;
import searchEngine.utils.AesEncryptUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @Author: WindPo
 * @Description: TODO
 * @DateTime: 2022/5/10 13:59
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final String SECRETKEY="xr9A2r2+KTsU3VLP";
    @Resource
    private  UserMapper userMapper;

    @Override
    public Result login(User user,HttpServletResponse response) {
        Result result = new Result();
        try {
            user.setPassword(AesEncryptUtils.decrypt(user.getPassword(),SECRETKEY));
            final User userDB = userMapper.selectOne(new QueryWrapper<User>()
                    .eq("username",user.getUsername()).eq("password",user.getPassword()));
            if(userDB==null){
                result=Result.failure("用户名或密码错误");
            }else {
                Cookie cookie = new Cookie("username",user.getUsername());
                cookie.setMaxAge(30 * 24 * 60 * 60);
                response.addCookie(cookie);

                result=Result.success(user.getPassword());
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
        //限制密码
        if (!user.getPassword().matches("^[a-zA-Z0-9!@#$%^&*()_=+]{6,32}")) {
            return Result.failure("输入密码规则错误");
        }
        System.out.println(user.getUsername() + "-" + user.getPassword());
        try {
            //判断数据库中是否存在用户
            boolean exists = userMapper.exists(new QueryWrapper<User>().eq("username", user.getUsername()));
            if (exists){
                result=Result.failure("用户已存在");
            }else {
                //注册用户
                user.setPassword(AesEncryptUtils.encrypt(user.getPassword(),SECRETKEY));
                userMapper.insert(user);
                result=Result.success(null);
            }
        }catch (Exception e){
            result=Result.failure(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
