package searchEngine.controller.search;

import com.alibaba.fastjson2.JSON;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 分词测试,controller样板
 **/
@RestController
@RequestMapping(value = "analysis")
public class AnalysisController {

    @GetMapping("/")
    public String analysisTest(@RequestParam("content") String content){

        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> tokenList = segmenter.process(content, JiebaSegmenter.SegMode.SEARCH);
        List<String> wordlist = tokenList.stream().map(e -> e.word).collect(Collectors.toList());
        return JSON.toJSONString(wordlist);
    }


}