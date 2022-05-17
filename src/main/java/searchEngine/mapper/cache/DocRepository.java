package searchEngine.mapper.cache;

import org.springframework.stereotype.Repository;
import searchEngine.entity.Doc;
import searchEngine.mapper.DocMapper;
import searchEngine.service.RedisService;

import javax.annotation.Resource;

/**
 * @Author: yumo
 * @Description: 实现缓存document表的数据
 * @DateTime: 2022/5/10 21:05
 **/
@Repository
public class DocRepository extends AbstractRedisCacheRepository<String, Doc>{

    private static final Doc NULL_THING = new Doc();

    @Resource
    private DocMapper docMapper;

    public DocRepository(RedisService redisService) {
        super(redisService, 30*60, "DOC:");
    }


    @Override
    protected Doc nullThing() {
        return NULL_THING;
    }

    @Override
    protected Doc getIfAbsent(String key) {
        return docMapper.selectById(key);
    }
}
