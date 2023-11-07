package searchEngine.controller.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import searchEngine.entity.Doc;
import searchEngine.pojo.QueryResponseBody;
import searchEngine.pojo.Result;
import searchEngine.pojo.SearchPair;
import searchEngine.service.IndexService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/search")
public class SearchController {

    /**
     * 预期命中数
     */
    @Value("${index.numHits}")
    private Integer numHits;

    @Resource
    private IndexService indexService;

    @ResponseBody
    @GetMapping("/ping")
    public String pong(){
        return "pong";
    }


    @GetMapping("/query")
    public String query(){
        return "search";
    }



    /**
     *
     * @param query 查询关键词
     * @param page 当前页码
     * @param limit 一页最多展示条目数
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/queryDoc")
    public String queryDoc(@RequestParam("query") String query,
                           @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                           @RequestParam(value = "limit",required = false,defaultValue = "50") Integer limit,
                           Model model,HttpServletRequest request){
        try {

            /**
             * 创建索引库
             */
            indexService.create();

            /**
             * 提取搜索关键词和过滤关键词
             */
            String filter="--filter";
            String queryWord=query;
            if (query.contains("--filter")){
                filter=query.substring(query.lastIndexOf("--filter")+"--filter".length()).trim();
                queryWord=query.substring(0, query.lastIndexOf("--filter")).trim();
            }else if (query.contains("-f")) {
                filter = query.substring(query.lastIndexOf("-f") + "-f".length()).trim();
                queryWord = query.substring(0, query.lastIndexOf("-f")).trim();
            }
            String[] filterWords = filter.split("[,|，]");

            /**
             * 从索引库搜索并计时
             */
            long start = System.currentTimeMillis();

            SearchPair<Doc> searchPair = indexService.search(queryWord, filterWords,page, limit, numHits, "desc");
            List<Doc> docList = searchPair.getList();
            long totalNum = searchPair.getNum();
            List<String> relateQueryList = indexService.relatesearch(queryWord,numHits);


            long end = System.currentTimeMillis();
            long time=end-start;
            log.info("查询耗时："+time+" ms");


            /**
             * 封装响应数据
             */
            long pageCount = (totalNum+limit-1)/limit;
            QueryResponseBody queryResponseBody = new QueryResponseBody(time, page,pageCount, limit, docList);
            Result queryDocSuccess = Result.success(queryResponseBody);

            docList.forEach(System.out::println);
            System.out.println("=================================================================");
            relateQueryList.forEach(System.out::println);

            model.addAttribute("query",query);
            model.addAttribute("relateQueryList",relateQueryList);
            model.addAttribute("queryDocSuccess",queryDocSuccess);
            model.addAttribute("queryResponseBody",queryResponseBody);
            return "searchResult";
        } catch (Exception e) {
            Result queryDocFailure = Result.failure(e.getMessage());
            model.addAttribute("queryDocFailure",queryDocFailure);
            System.out.println("queryDocFailure = " + queryDocFailure);
            return "error";
        }
    }

}
