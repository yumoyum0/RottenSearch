package searchEngine.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import searchEngine.entity.Doc;

import java.util.List;

/**
 * @Description: TODO
 **/
@Mapper
public interface DocMapper extends BaseMapper<Doc> {
    List<Doc> getAllDoc();

    IPage<Doc> selectPageVo(IPage<Doc> page, @Param(Constants.WRAPPER) Wrapper<Doc> queryWrapper);
}
