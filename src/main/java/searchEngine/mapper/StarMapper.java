package searchEngine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import searchEngine.entity.Doc;
import searchEngine.entity.Star;

/**
 * @Author: xun
 * @Description: TODO
 * @DateTime: 2022/5/10 13:55
 **/
@Mapper
public interface StarMapper extends BaseMapper<Star> {
    /**
     * 向收藏夹中添加网页
     * @param sid 收藏夹id
     * @param did 网页id
     */
    void addS (@Param("sid") Integer sid, @Param("did") Integer did);
    /**
     * 向用户添加收藏夹
     * @param sid 收藏夹id
     * @param uid 用户id
     */
    void addU (@Param("sid") Integer sid, @Param("uid") Integer uid);

    /**
     * 根据收藏夹的名字查找收藏夹id
     * 在star中查找
     * @param starname 收藏夹名字
     * @return 收藏夹id
     */
    Integer queryIdByname (@Param("starname") String starname);

    /**
     * 根据网页描述查找网页id
     * 在doc中查找
     * @param desc 网页描述
     * @return 网页id
     */
    Integer queryIdBydesc (@Param("desc") String desc);

    /**
     * 根据收藏夹名字查找收藏夹id
     * @param starname 收藏夹名字
     * @return 收藏夹id
     */
    Integer queryIdByStarname (@Param("starname") String starname);
    void deleteDoc (@Param("did") Integer did, @Param("sid") Integer sid);
    void deleteStars (@Param("sid") Integer sid);

    /**
     * 删除收藏夹中的网页
     * @param sid 收藏夹id
     */
    void deleteStar (@Param("sid") Integer sid);

    /**
     *
     * @param desc
     * @return
     */
    Doc search (@Param("desc") String desc);

    /**
     * 判断收藏夹是否重名
     * @param starname 收藏夹名字
     * @return 收藏夹名字
     */
    String judge (@Param("starname") String starname);

    /**
     * 创建收藏夹
     * @param starname 收藏夹名字
     */
    void createStars(@Param("starname") String starname);

}
