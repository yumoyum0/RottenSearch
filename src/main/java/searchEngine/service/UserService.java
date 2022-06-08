package searchEngine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import searchEngine.entity.User;
import searchEngine.pojo.Result;

/**
 * @Author: WindPo
 * @Description: TODO
 * @DateTime: 2022/5/10 13:58
 **/
public interface UserService extends IService<User> {
    /**
     * 用户登录接口
     * @param user 用户信息
     * @return 返回登录结果
     */
    Result login(User user);

    /**
     * 用户注册接口
     * @param user 用户信息
     * @return 返回注册结果
     */
    Result regist(User user);
}
