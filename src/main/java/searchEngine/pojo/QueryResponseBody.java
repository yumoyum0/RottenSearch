package searchEngine.pojo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import searchEngine.entity.Document;

import java.util.List;

/**
 * @Author: yumo
 * @Description: 执行查询操作时响应的json字段，传给Result类的data
 * @DateTime: 2022/5/9 18:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryResponseBody {

    /**
     * 搜索文档用时
     */
    @JSONField(name = "time")
    private Double time;

    /**
     * 符合条件的数量
     */
    @JSONField(name = "total")
    private Integer total;

    /**
     * 总页数
     */
    @JSONField(name = "page_count")
    private Integer pageCount;

    /**
     * 每页数量
     */
    @JSONField(name = "limit")
    private Integer limit;

    /**
     * 文档列表
     */
    @JSONField(name = "docs")
    private List<Document> documentList;
}
