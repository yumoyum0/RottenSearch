package searchEngine.mapper.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import searchEngine.entity.Document;
import searchEngine.mapper.DocMapper;
import searchEngine.service.RedisService;

import javax.annotation.Resource;

/**
 * @Author: yumo
 * @Description: 实现缓存document表的数据
 * @DateTime: 2022/5/10 21:05
 **/
@Repository
public class DocRepository extends AbstractRedisCacheRepository<String, Document>{

    private static final Document NULL_THING = new Document();

    @Resource
    private DocMapper docMapper;

    public DocRepository(RedisService redisService) {
        super(redisService, 30*60, "DOC:");
    }


    @Override
    protected Document nullThing() {
        return NULL_THING;
    }

    @Override
    protected Document getIfAbsent(String key) {
        return docMapper.selectById(key);
    }
}
