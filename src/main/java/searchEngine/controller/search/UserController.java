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
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/5/10 19:00
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @SecurityParameter(inDecode = false,outEncode = true)
    @GetMapping("list")
    public Result userList(){
        return Result.success(userService.list());
    }

    @SecurityParameter(inDecode = true,outEncode = false)
    @PostMapping("/add")
    public Result addUser(User user){
        System.out.println(user);
        return Result.success(user);
    }
}
