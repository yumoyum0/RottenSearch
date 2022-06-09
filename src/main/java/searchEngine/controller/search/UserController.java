package searchEngine.controller.search;

import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;


    @GetMapping("/getLogin")
    public String getLogin(){
        return "login";
    }

    /**
     * 登录
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/login")
    public String login(User user){
        userService.login(user);
        return "search";
    }


    @GetMapping("/getRegister")
    public String getRegister(){
        return "register";
    }

    /**
     * 注册
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/register")
    public String register(User user){
         userService.regist(user);
         return "login";
    }
}
