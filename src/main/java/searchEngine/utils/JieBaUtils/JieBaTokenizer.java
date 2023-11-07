package searchEngine.utils.JieBaUtils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.analysis.util.SegmentingTokenizerBase;
import org.apache.lucene.util.AttributeFactory;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.*;

/**
 * @Description: JieBa分词器
 */

public class JieBaTokenizer extends SegmentingTokenizerBase {
    /**
     * 用来把一段文本分成句子
     */
    private static final BreakIterator SENTENCE_PROTO = BreakIterator.getSentenceInstance(Locale.ROOT);

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

    /**
     * JieBa Segmenter and tokens
     */
    private final JiebaSegmenter wordSegmenter = new JiebaSegmenter();
    private Iterator<SegToken> tokens;

    /**
     * JieBa segMode
     */
    private final JiebaSegmenter.SegMode segMode;

    /**
     * record field sentence offset
     */
    private int sentenceStart = 0;
    private int sentenceEnd = 0;

    /**
     * used for sentence witch length > 1024
     * if sentence length greater than 1024, setNextSentence method parameter sentenceStart will be 0
     * fieldIdCounter is filed id counter,
     * curFieldId
     */
    private long fieldIdCounter = 0;
    private static final long MAX_FIELD_ID=1000000;
    private long curFieldId = 0;
    private int fieldOffset = 0;


    public JieBaTokenizer(JiebaSegmenter.SegMode segMode) {
        super(SENTENCE_PROTO);
        this.segMode = segMode;
    }


    public JieBaTokenizer(JiebaSegmenter.SegMode segMode, AttributeFactory factory) {
        super(factory, (BreakIterator)SENTENCE_PROTO.clone());
        this.segMode = segMode;
    }

    @Override
    protected void setNextSentence(int sentenceStart, int sentenceEnd) {
        if (curFieldId != fieldIdCounter){
            //after reset, new field start.
            curFieldId = fieldIdCounter;

            this.sentenceStart = sentenceStart;
            this.sentenceEnd = sentenceEnd;
        }else{//field not change
            if (sentenceStart == 0){
                fieldOffset = this.sentenceEnd;
            }

            this.sentenceStart = sentenceStart + fieldOffset;
            this.sentenceEnd = sentenceEnd + fieldOffset;
        }

        String sentence = new String(buffer, sentenceStart, sentenceEnd - sentenceStart);
        List<SegToken> segTokenList = wordSegmenter.process(sentence, segMode);

        //need order SegTokens by startOffset
        segTokenList.sort(new Comparator<SegToken>() {
            @Override
            public int compare(SegToken o1, SegToken o2) {
                return o1.startOffset - o2.startOffset;
            }
        });

        tokens = segTokenList.iterator();
    }

    @Override
    protected boolean incrementWord() {
        if (tokens == null || !tokens.hasNext()) {
            return false;
        } else {
            SegToken token = tokens.next();
            clearAttributes();
            termAtt.copyBuffer(token.word.toCharArray(), 0, token.word.length());
            int startOffset = sentenceStart + token.startOffset;
            int endOffset = sentenceStart + token.endOffset;

            offsetAtt.setOffset(startOffset, endOffset);
            typeAtt.setType("word");
            return true;
        }
    }

    @Override
    public void reset() throws IOException {
        super.reset();

        fieldIdCounter = (++fieldIdCounter % MAX_FIELD_ID);

        tokens = null;
        sentenceStart = 0;
        sentenceEnd = 0;
        fieldOffset = 0;
    }
}