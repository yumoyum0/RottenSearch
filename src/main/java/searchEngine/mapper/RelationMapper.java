package searchEngine.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 映射关系表的操作
 */
@Mapper
public interface RelationMapper {

    int addStarsToUser(@Param("sid") Integer sid, @Param("uid") Integer uid);

    int deleteStarsFromUser(@Param("sid") Integer sid,@Param("uid") Integer uid);
}
