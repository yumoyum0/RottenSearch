package searchEngine.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: yumo
 * @Description: 关键字高亮，相对text字段中的文本
 * @DateTime: 2022/5/9 17:56
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HighLight {

    /**
     * 关键词前缀
     */
    private String preTag;

    /**
     * 关键词后缀
     */
    private String postTag;
}
