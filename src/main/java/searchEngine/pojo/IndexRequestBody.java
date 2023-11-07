package searchEngine.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import searchEngine.entity.Doc;

/**
 * @Description: 增加/修改索引
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IndexRequestBody {

    /**
     * 文档的主键id，需要保持唯一性，如果id重复，将会覆盖直接的文档。
     */
    private Integer id;

    /**
     * 需要索引的文本块
     */
    private String text;

    /**
     * 附带的文档数据，json格式，搜索的时候原样返回
     */
    private Doc document;
}
