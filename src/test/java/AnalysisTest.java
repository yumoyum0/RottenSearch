import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 分词测试类
 **/

public class AnalysisTest {


    @Test
    public void analysis(){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String[] sentences={
                " 基于 trie 树结构实现高效词图扫描" ,
                " 生成所有切词可能的有向无环图 DAG" ,
                " 采用动态规划算法计算最佳切词组合" ,
                " 基于 HMM 模型，采用 Viterbi (维特比)算法实现未登录词识别"
        };
        for (String sentence : sentences){
            List<SegToken> tokenList = segmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH);
            List<String> wordlist = tokenList.stream().map(e -> e.word).collect(Collectors.toList());
            System.out.println(wordlist);
            System.out.println("=======================================");
        }
    }
}
