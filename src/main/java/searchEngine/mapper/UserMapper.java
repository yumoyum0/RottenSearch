package searchEngine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import searchEngine.entity.User;

/**
 * @Author: WindPo
 * @Description: finished
 * @DateTime: 2022/5/10 13:54
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 用户登录
     * @param username  用户名
     * @param password  密码
     * @return   用户信息
     */
    User login(@Param("username")String username,@Param("password")String password);

    /**
     * 用户注册
     */
    /**
     * 通过用户id查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User queryUserByusername(@Param("username")String username);

    /**
     * 上传用户信息
     * @param user 用户信息
     */
    void regist(User user);


}
