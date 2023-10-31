package searchEngine.controller.search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import searchEngine.annotation.SecurityParameter;
import searchEngine.entity.User;
import searchEngine.pojo.Result;
import searchEngine.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    public String getLogin() {
        return "login";
    }

    /**
     * 登录
     *
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username
            , @RequestParam("password") String password
            , Model model, HttpServletRequest request) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        if (userService.login(user).getSuccess()) {
            model.addAttribute("code", "200");
            return "search";
        } else {
            model.addAttribute("code", "500");
            model.addAttribute("errMsg", ".......");
            return "login";
        }
    }


    @GetMapping("/getRegister")
    public String getRegister() {
        return "register";
    }

    /**
     * 注册
     *
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/register")
    public String register(
            @RequestParam("username") String username
            , @RequestParam("password") String password
            , Model model, HttpServletRequest request) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (userService.regist(user).getSuccess()) {
            return "login";
        } else {
            model.addAttribute("code", "500");
            model.addAttribute("errMsg", ".......");
            return "register";
        }
    }
}
