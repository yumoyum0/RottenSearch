package searchEngine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchEngine.entity.Doc;
import searchEngine.entity.Star;
import searchEngine.exception.LocalRunTimeException;
import searchEngine.mapper.RelationMapper;
import searchEngine.mapper.StarMapper;
import searchEngine.pojo.Result;
import searchEngine.service.StarService;

import java.sql.Wrapper;
import java.util.List;

/**
 * @Author: xun
 * @Description: TODO
 * @DateTime: 2022/5/10 14:02
 **/
@Service
public class StarServiceImpl extends ServiceImpl<StarMapper,Star> implements StarService {

    private final StarMapper starMapper;
    public StarServiceImpl(StarMapper starMapper) {
        this.starMapper = starMapper;
    }

    @Override
    public String createStars(String starname) {
        if (judge(starname)) {
            throw new LocalRunTimeException("收藏夹重名了噢");
        }
        starMapper.createStars(starname);

        return "ok";
    }

    @Override
    public boolean judge(String starname) {
        String judge = starMapper.judge(starname);
        if (starname.equals(judge)) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 这里到时候可以加一个判断条件
     * 判断是否已经加到那个收藏夹中
     * @param starname 收藏夹名称
     * @param desc 网页描述
     */
    @Override
    public String addS(String starname, String desc) {
        Integer sid = starMapper.queryIdByname(starname);
        Integer did = starMapper.queryIdBydesc(desc);
        starMapper.addS(sid, did);
        return "ok";
    }

    @Override
    public String deleteStars(String starname) {
        Integer sid = starMapper.queryIdByname(starname);
        if (sid == null) {
            throw new LocalRunTimeException("不存在这个收藏夹呢");
        }
        starMapper.deleteStar(sid);
        return "ok";
    }

    @Override
    public String deleteDoc(String starname, String desc) {
        Integer did = starMapper.queryIdBydesc(desc);
        Integer sid = starMapper.queryIdByStarname(starname);
        if (did == null || sid == null) {
            throw new LocalRunTimeException("没有这个收藏噢");
        }

        starMapper.deleteDoc(did, sid);
        return "ok";
    }

    @Override
    public Doc search(String desc) {

        return null;
    }

    @Override
    public void addU(Integer sid, Integer uid) {

    }

    @Override
    public Boolean modifyStar(String starName, String newStarName) {
        // 参数非空判断
        if (starName == null || newStarName == null){
            throw  new LocalRunTimeException("请检查修改名是否为空");
        }
        // 获取收藏夹名
        Integer sid = starMapper.queryIdByname(starName);
        // 修改收藏夹名
        starMapper.modifyStar(sid,newStarName);
        return true;
    }

    @Override
    public List<Star> getStars(Integer userId) {
        List<Integer> sids = starMapper.queryStarIdsByUserId(userId);
        List<Star> stars = starMapper.queryStarsByIds(sids);
        return stars;
    }
}
