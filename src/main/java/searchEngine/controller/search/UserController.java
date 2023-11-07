package searchEngine.controller.search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import searchEngine.entity.User;
import searchEngine.pojo.Result;
import searchEngine.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: TODO
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
     * @return Result
     */
    @PostMapping(value = "/login")
    public String login(@RequestParam("name") String name
            , @RequestParam("password") String password
            , Model model, HttpServletResponse response) {

        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        Result result = userService.login(user, response);
        if (result.getSuccess()) {
            model.addAttribute("code", "200");
            return "search";
        } else {
            model.addAttribute("code", "500");
            model.addAttribute("errMsg", "登陆失败！"+result.getErrMsg());
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
            String name
            ,String password
            , Model model) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        Result result = userService.regist(user);
        if (result.getSuccess()) {
            model.addAttribute("code", "200");
            model.addAttribute("errMsg", "注册成功！");
            return "login";
        } else {
            model.addAttribute("code", "500");
            model.addAttribute("errMsg", "注册失败！"+result.getErrMsg());
            return "register";
        }
    }
}
