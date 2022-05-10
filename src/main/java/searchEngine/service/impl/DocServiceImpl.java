package searchEngine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import searchEngine.entity.Document;
import searchEngine.mapper.DocMapper;
import searchEngine.service.DocService;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/5/10 14:01
 **/
@Service
public class DocServiceImpl extends ServiceImpl<DocMapper, Document> implements DocService {
}
