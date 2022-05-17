package searchEngine.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import searchEngine.entity.Doc;
import searchEngine.mapper.DocMapper;
import searchEngine.service.DocService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/5/10 14:01
 **/
@Service
public class DocServiceImpl extends ServiceImpl<DocMapper, Doc> implements DocService {
    @Resource
    private DocMapper docMapper;
    @Override
    public List<Doc> getAllDoc() {
        return docMapper.getAllDoc();
    }

    public IPage<Doc> selectPageVo(IPage<Doc> page, Wrapper<Doc> queryWrapper){
        return docMapper.selectPageVo(page,queryWrapper);
    }
}
