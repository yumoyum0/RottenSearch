package searchEngine.service.impl;


import com.huaban.analysis.jieba.JiebaSegmenter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import searchEngine.entity.Doc;
import searchEngine.mapper.DocMapper;
import searchEngine.pojo.SearchPair;
import searchEngine.service.DocService;
import searchEngine.service.IndexService;
import searchEngine.utils.JieBaUtils.JieBaAnalyzer;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * @Description: 索引操作
 */
@Slf4j
@Service
public class IndexServiceImpl implements IndexService {

    @Value("${index.path}")
    private String path;

    @Resource
    private DocMapper docMapper;

    /**
     * 创建索引库
     * @Author: yumo
     * @throws IOException
     */
    @Override
    public void create()throws IOException {
        File file = new File(path);
        if(null != file && file.isDirectory()){
            if(file.list().length>0){
                log.info("该索引库下已有内容");
            }else {
                long start = System.currentTimeMillis();
                // 1、采集数据

                List<Doc> docList = docMapper.getAllDoc();
                List<Document> documentList=new ArrayList<>();
                // 2、创建文档对象
                for (Doc doc:docList){
                    Document document = new Document();
                    // 创建域对象并放入文档对象

                    /**
                     * 是否分词：N，主键分词无意义
                     * 是否索引：Y，要根据id主键查询，就必须索引
                     * 是否存储：Y，因为需要获取id
                     */
                    document.add(new IntPoint("id",doc.getId()));
                    document.add(new StoredField("id",doc.getId()));

                    /**
                     * 是否分词：Y，title字段需要查询且分词后有意义
                     * 是否索引：Y，要根据title查询
                     * 是否存储：Y，因为需要获取title
                     */
                    document.add(new TextField("title",doc.getTitle(), Field.Store.YES));

                    /**
                     * 是否分词：Y，desc字段需要查询且分词后有意义
                     * 是否索引：Y，要根据desc查询
                     * 是否存储：Y，因为需要获取desc
                     */
                    document.add(new TextField("desc",doc.getDesc(), Field.Store.YES));

                    /**
                     * 是否分词：N，url分词无意义
                     * 是否索引：N，因为不需要根据url路径查询
                     * 是否存储：Y ,因为需要获取
                     */
                    document.add(new StoredField("url",doc.getUrl()));

                    documentList.add(document);
                }
                // 3、创建分词器
                Analyzer analyzer=new JieBaAnalyzer(JiebaSegmenter.SegMode.INDEX);
                // 4、创建Directory目录对象，代表索引库的位置
                Directory directory = FSDirectory.open(Paths.get(path));
                // 5、创建IndexWriterConfig对象，这个对象中指定切分词使用的分词器
                IndexWriterConfig config=new IndexWriterConfig(analyzer);
                // 6、创建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
                IndexWriter indexWriter=new IndexWriter(directory,config);
                // 7、写入文档到索引库
                for (Document document : documentList) {
                    indexWriter.addDocument(document);
                }
                // 8、释放资源
                indexWriter.close();
                long end = System.currentTimeMillis();
                long time=end-start;
                log.info("创建索引库耗时："+time+" ms");
            }
        }else {
            log.info("索引库创建失败");
        }
    }

    /**
     * 从索引库中针对给定的域数组查询关键词
     * @Author: yumo
     * @param query 查询关键词
     * @param filterWords 过滤关键词数组
     * @param page 当前页码
     * @param limit 每页条目总数
     * @param numHits 预期命中数
     * @param fields 查询域数组
     * @return 从索引库中获取的Doc集合
     * @throws Exception
     */
    public  SearchPair<Doc> search(String query,String[] filterWords,Integer page,Integer limit,Integer numHits,String... fields) throws Exception {
        log.info("查询的关键词："+query);
        log.info("过滤的关键词："+ Arrays.toString(filterWords));
        log.info("当前页码："+page);
        log.info("每页条目总数："+limit);
        log.info("预期命中数："+numHits);
        log.info("查询域："+ Arrays.stream(fields).toList());
        // 创建分词器
        Analyzer analyzer=new JieBaAnalyzer(JiebaSegmenter.SegMode.SEARCH);

        // 创建多字段查询对象，设置查询域
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(fields, analyzer);

        // 设置搜索关键词
        Query q=null;
        if(StringUtils.isEmpty(query)){
            // 若查询关键词为空，则查询所有
            q=multiFieldQueryParser.parse("*:*");
        }else {
            // 根据关键词查询条件设置
            q = multiFieldQueryParser.parse(query);
        }
        // 创建Directory目录对象，指定索引库的位置
        Directory directory = FSDirectory.open(Paths.get(path));
        // 创建输入流对象
        IndexReader indexReader= DirectoryReader.open(directory);
        // 创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 搜索并返回结果,第二个参数表示返回多少条数据，分页使用
        TopDocs topDocs = indexSearcher.search(q, numHits);
        log.info("根据关键词 "+ query +" 查询到的数据总条数："+topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Doc> docList=new ArrayList<>();

        // 分页查询及关键词过滤
        int cnt=0;
        int n=page * limit;
        outLoop : for (int i = (page - 1) * limit; i < n && i<scoreDocs.length; i++) {
            int docId = scoreDocs[i].doc;

            Document document = indexSearcher.doc(docId);
            Integer id = Integer.parseInt(document.get("id"));
            String title = document.get("title");
            String desc = document.get("desc");
            String url = document.get("url");
            /**
             * 关键词过滤
             */
            for (String filter:filterWords){
                if (desc.contains(filter)||title.contains(filter)){
                    cnt++;
                    n++;
                    continue outLoop;
                }
            }

            //首先获取docId的TokenStream
            TokenStream tokenStream= TokenSources.getAnyTokenStream(indexReader, docId, fields[0], analyzer);
            //构建Fragmenter对象,用于文档切片  默认字符为100
            Fragmenter fragmenter = new SimpleFragmenter(100);
            //构建Scorer,用于选取最佳切片
            Scorer fragmentScore = new QueryScorer(q);
            //构建Formatter格式化最终显示(将字体颜色设置为红色)
            Formatter formatter = new SimpleHTMLFormatter("<font color='red'>","</font>");
            //实例化Highlighter组件
            Highlighter highlighter = new Highlighter(formatter,fragmentScore);
            highlighter.setTextFragmenter(fragmenter);
            // 最后一步,获取hightlightText  第二个参数为原始文档信息
            desc=highlighter.getBestFragment(tokenStream, desc);

            Doc doc = new Doc(id, title, desc, url);

            docList.add(doc);
        }
        log.info("根据关键词 "+ Arrays.toString(filterWords) +" 过滤的数据总条数："+cnt);
        return new SearchPair<Doc>(docList,topDocs.totalHits.value);
    }

    /**
     * 相关搜索
     * @Author: xun
     * @param words 查询的关键词
     * @return 相关字段
     */
    public List<String> relatesearch (String words, Integer numHits) throws Exception {
        // 分词
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> relate = segmenter.sentenceProcess(words);

        Integer length = relate.size();
        List<String> relateList = new ArrayList<>();
        List<String> resultList=new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (i >= 5) {
                break;
            }
            // 获取最长的字符串
            String strmax = Collections.max(relate, (o1, o2) -> {
                if (o1.length() > o2.length()) {
                    return 1;
                } else if (o1.length() == o2.length()) {
                    return 0;
                } else {
                    return -1;
                }
            });
            // 如果不是标点符号就搜索
            if (!check(strmax)) {
                relateList = Search(strmax, numHits);
                relate.remove(strmax);
            }
            for (String s : relateList) {
                resultList.add(s);
            }
        }
        return resultList;
    }

    /**
     * 为相关搜索提供的搜索函数
     * @Author: xun
     * @param words 关键词
     * @param numHits 预期命中数
     * @return 相关数据的desc`
     * @throws Exception
     */
    public List<String> Search(String words, Integer numHits) throws Exception {
        // 短语查询
        TermQuery termQuery = new TermQuery(new Term("desc", words));
        // 创建Directory目录对象，指定索引库的位置
        Directory directory = FSDirectory.open(Paths.get(path));
        // 创建输入流对象
        IndexReader indexReader = DirectoryReader.open(directory);
        // 创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 搜索并返回结果,第二个参数表示返回多少条数据，分页使用
        TopDocs topDocs = indexSearcher.search(termQuery, numHits);
        log.info("根据关键词 "+words+" 查询到的数据总条数：" + topDocs.totalHits);
        // 获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<String> relateList = new ArrayList<>();
        if (scoreDocs.length>0) {
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docId = scoreDoc.doc;
                Document doc = indexSearcher.doc(docId);




                relateList.add(doc.get("desc"));
            }
        }else {
            log.error("结果集为空!");
        }
        indexReader.close();
        return relateList.stream().limit(3).toList();
    }

    /**
     * 检查是否有中英文标点符号
     * @Author: xun
     * @param s 提供检查的字符串
     * @return boolean
     */
    public boolean check(String s) {
        boolean b = false;

        String tmp = s;
        tmp = tmp.replaceAll("\\p{P}", "");
        if (s.length() != tmp.length()) {
            b = true;
        }
        return b;
    }
}
