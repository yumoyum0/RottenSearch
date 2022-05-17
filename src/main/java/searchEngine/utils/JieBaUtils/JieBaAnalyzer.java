package searchEngine.utils.JieBaUtils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * @Author: yumo
 * @Description: JieBa分析器
 */

public class JieBaAnalyzer extends Analyzer {
    private static final String DEFAULT_STOPWORD_FILE = "stopwords.txt";
    private final CharArraySet stopWords;

    private JiebaSegmenter.SegMode segMode;

    public JieBaAnalyzer(JiebaSegmenter.SegMode segMode){
        this.segMode = segMode;
        this.stopWords = new CharArraySet(128, true);
    }

    public JieBaAnalyzer(JiebaSegmenter.SegMode segMode, CharArraySet stopWords){
        this.segMode = segMode;
        this.stopWords = new CharArraySet(stopWords, true);
    }

    /**
     * use for add user dictionary and stop words,
     * user dictionary need with .dict suffix, stop words with file name: stopwords.txt
     */
    public void init(String userDictPath){
        if ( ! StringUtils.isEmpty(userDictPath)){
            File file  = new File(userDictPath);
            if (file.exists()){
                //load user dict
                WordDictionary wordDictionary = WordDictionary.getInstance();
                wordDictionary.init(Paths.get(userDictPath));

                //load stop words from userDictPath with name stopwords.txt, one word per line.
                loadStopWords(Paths.get(userDictPath, DEFAULT_STOPWORD_FILE) , StandardCharsets.UTF_8);
            }
        }
    }

    /**
     * 从path路径加载stop word
     * @param userDict stop word path, one word per line
     */
    private void loadStopWords(Path userDict, Charset charset) {
        try {
            BufferedReader br = Files.newBufferedReader(userDict, charset);
            int count = 0;
            while (br.ready()) {
                String line = br.readLine();
                if (! StringUtils.isEmpty(line)){
                    stopWords.add(line);
                    ++count;
                }
            }
            System.out.println(String.format(Locale.getDefault(), "%s: load stop words total:%d!", userDict.toString(), count));
            br.close();
        }
        catch (IOException e) {
            System.err.println(String.format(Locale.getDefault(), "%s: load stop words failure!", userDict.toString()));
        }
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {
        final Tokenizer tokenizer = new JieBaTokenizer(segMode);
        TokenStream result = tokenizer;

        if (!stopWords.isEmpty()) {
            result = new StopFilter(result, stopWords);
        }

        return new TokenStreamComponents(tokenizer, result);
    }
}
