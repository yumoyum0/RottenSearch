package searchEngine.controller.search;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchEngine.annotation.SecurityParameter;
import searchEngine.entity.User;
import searchEngine.pojo.Result;
import searchEngine.service.UserService;

import javax.annotation.Resource;

/**
 * @Author: WindPo
 * @Description: TODO
 * @DateTime: 2022/5/10 19:00
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 登录
     * @param user 参数封装
     * @return Result
     */
    @SecurityParameter(inDecode = false,outEncode = true)
    @GetMapping(value = "/login")
    public Result login(User user){
        return userService.login(user);
    }

    /**
     * 注册
     * @param user 参数封装
     * @return Result
     */
    @SecurityParameter(inDecode = true,outEncode = false)
    @PostMapping(value = "/regist")
    public Result regist(User user){
        return userService.regist(user);
    }
}
