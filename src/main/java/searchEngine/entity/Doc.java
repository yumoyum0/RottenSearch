package searchEngine.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * @Author: yumo
 * @Description: 文档实体类，对应数据库中的document表
 * @DateTime: 2022/5/9 18:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "doc")
@Alias("doc")
public class Doc {

    @JSONField(name = "doc_id")
    @TableId(value = "doc_id")
    private Integer id;
    /**
     * 文档标题
     */
    @JSONField(name = "title")
    @TableField(value = "title")
    private String title;

    /**
     * 文档描述
     */
    @JSONField(name = "desc")
    @TableField(value = "desc")
    private String desc;

    /**
     * 文档URL
     */
    @JSONField(name = "url")
    @TableField(value = "url")
    private String url;

    /**
     * 文档权重
     */
    @JSONField(name = "score")
    @TableField(value = "score")
    private Integer score;

    /**
     * 当前文档所属于的收藏夹
     * 不映射表中的任何字段
     */
    @TableField(exist = false)
    private Star star;

    public Doc(Integer id, String title, String desc, String url) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.url = url;
    }
}
