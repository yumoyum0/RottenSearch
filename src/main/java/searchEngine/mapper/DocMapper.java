package searchEngine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import searchEngine.entity.Document;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/5/10 12:15
 **/
@Mapper
public interface DocMapper extends BaseMapper<Document> {

}
