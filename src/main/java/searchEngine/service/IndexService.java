package searchEngine.service;


import searchEngine.entity.Doc;
import searchEngine.pojo.SearchPair;

import java.io.IOException;
import java.util.List;

/**
 * @author yumo
 */
public interface IndexService {
    /**
     * 创建索引库
     * @throws IOException
     */
    void create() throws IOException;

    /**
     * 从索引库中查询
     * @param query 查询关键词
     * @param numHits 预期命中数
     * @param fields 查询域数组
     * @return 所有命中的文档的前numHits个
     * @throws Exception
     */
    SearchPair<Doc> search(String query, Integer page, Integer limit, Integer numHits, String... fields) throws Exception;

    List<String> relatesearch (String Words,Integer numHits) throws Exception;
    boolean check(String s);
}
