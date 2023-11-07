package searchEngine.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * @Description: 用户实体类，对应数据库的user表
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("user")
@Alias("user")
public class User {
    /**
     * 用户id
     */
    @JSONField(name = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @JSONField(name = "username")
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @JSONField(name = "password")
    @TableField(value = "password")
    private String password;

    /**
     * 属于该用户的所有收藏夹集合
     * 不映射表中任何字段
     */
    @TableField(exist = false)
    private List<Star> starList;
}
