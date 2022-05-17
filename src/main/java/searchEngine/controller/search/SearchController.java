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

//    @GetMapping("/queryDoc")
//    public void testQuery(@RequestParam("query") String query,Model model,HttpServletRequest request){
//        queryDoc(new QueryRequestBody(query,1,100),model,request);
//    }
//    th:action="@{/search/queryDoc}

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
            HttpSession session = request.getSession();
            StringBuffer requestURLStringBuffer = request.getRequestURL();
            int lastIndexOf = requestURLStringBuffer.lastIndexOf("query=");
            if (lastIndexOf>0){
                String requestURL = requestURLStringBuffer.substring(0, lastIndexOf-1);

            }
            /**
             * 获取传入的请求参数值
             */
//            String query = queryRequestBody.getQuery();
//            Integer page = queryRequestBody.getPage();
//            Integer limit = queryRequestBody.getLimit();
//            session.setAttribute("query",query);
            /**
             * 创建索引库
             */
            indexService.create();

            /**
             * 从索引库搜索并计时
             */
            long start = System.currentTimeMillis();
            List<Doc> docList = indexService.search(query, numHits, "desc");
            List<String> relateQueryList = indexService.relatesearch(query,numHits);



            IPage<Doc> docIPage = docService.selectPageVo(new Page<>(page, limit), new QueryWrapper<Doc>());
            long end = System.currentTimeMillis();
            long time=end-start;
            log.info("查询耗时："+time+" ms");
            /**
             * 封装响应数据
             */
            QueryResponseBody queryResponseBody = new QueryResponseBody(time, page, docIPage.getTotal(), limit, docList);
            Result queryDocSuccess = Result.success(queryResponseBody);


            docList.forEach(System.out::println);
            System.out.println("=================================================================");
            relateQueryList.forEach(System.out::println);
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
