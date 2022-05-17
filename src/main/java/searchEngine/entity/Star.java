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

import java.util.List;

/**
 * @Author: yumo
 * @Description: 收藏夹实体类，对应数据库里的star表
 * @DateTime: 2022/5/9 19:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("star")
@Alias("star")
public class Star {

    /**
     * 收藏夹id
     */
    @JSONField(name = "star_id")
    @TableId(value = "star_id")
    private Integer id;

    /**
     * 收藏夹名
     */
    @JSONField(name = "star_name")
    @TableField(value = "star_name")
    private String starName;


    /**
     * 当前收藏夹所属于的用户
     * 不映射表中的任何字段
     */
    @TableField(exist = false)
    private User user;

    /**
     * 属于当前收藏夹的所有文档集合
     * 不映射表中的任何字段
     */
    @TableField(exist = false)
    private List<Doc> documentList;
}
