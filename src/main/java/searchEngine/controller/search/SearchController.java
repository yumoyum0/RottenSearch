package searchEngine.controller.search;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import searchEngine.entity.Doc;
import searchEngine.pojo.QueryRequestBody;
import searchEngine.pojo.QueryResponseBody;
import searchEngine.pojo.Result;
import searchEngine.pojo.SearchPair;
import searchEngine.service.DocService;
import searchEngine.service.IndexService;
import searchEngine.service.StarService;
import searchEngine.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.List;

/**
 * @author yumo
 */
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
    private DocService docService;

    @Resource
    private UserService userService;

    @Resource
    private StarService starService;

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
                           @RequestParam(value = "limit",required = false,defaultValue = "10") Integer limit,
                           Model model,HttpServletRequest request){
        try {

            /**
             * 创建索引库
             */
            indexService.create();

            /**
             * 从索引库搜索并计时
             */
            long start = System.currentTimeMillis();

            SearchPair<Doc> searchPair = indexService.search(query, page, limit, numHits, "desc");
            List<Doc> docList = searchPair.getList();
            long totalNum = searchPair.getNum();
            List<String> relateQueryList = indexService.relatesearch(query,numHits);


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
