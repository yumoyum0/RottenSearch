package searchEngine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import searchEngine.entity.Doc;
import searchEngine.entity.Star;
import searchEngine.pojo.Result;

/**
 * @Author: xun
 * @Description: 收藏夹的增删改查
 * @DateTime: 2022/5/10 14:02
 **/
public interface StarService extends IService<Star> {
    /**
     * 创建收藏夹
     * @param starname 收藏夹名字
     */
    String createStars(String starname);

    /**
     * 判断收藏夹是否重名
     * @param starname 收藏夹名称
     * @return boolean
     */
    boolean judge (String starname);

    /**
     * 往收藏夹中添加网页
     * @param starname 收藏夹名称
     * @param desc 网页描述
     */
    String addS (String starname, String desc);

    /**
     * 删除收藏夹
     * @param starname 收藏夹名称
     */
    String deleteStars (String starname);

    /**
     * 删除收藏夹中的网页
     * @param starname 收藏夹名称
     * @param desc 网页描述
     */
    String deleteDoc (String starname, String desc);

    /**
     * 搜索内容
     * @param desc 网页描述
     */
    Doc search (String desc);

    /**
     * 记录用户的收藏夹
     * @param sid 收藏夹id
     * @param uid 用户id
     */
    void addU (Integer sid, Integer uid);
}
