package searchEngine.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import searchEngine.entity.Doc;

import java.util.List;

/**
 * @Description: TODO
 **/
public interface DocService extends IService<Doc> {
    public List<Doc> getAllDoc();

    public IPage<Doc> selectPageVo(IPage<Doc> page,Wrapper<Doc> queryWrapper);
}
