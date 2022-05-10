package searchEngine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import searchEngine.entity.User;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/5/10 13:54
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
