package searchEngine.pojo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import searchEngine.enums.ErrorEnum;

import java.io.Serializable;

/**
 * @Author: yumo
 * @Description: 前端统一返回类型
 * @DateTime: 2022/5/9 18:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result<T> implements Serializable {
    /**
     * 成功与否
     */
    @JSONField(name = "success")
    private Boolean success;

    /**
     * 状态码
     */
    @JSONField(name = "code")
    private Integer code;

    /**
     * 错误消息
     */
    @JSONField(name = "errMsg")
    private String errMsg;

    /**
     * 数据对象
     */
    @JSONField(name = "data")
    private T data;

    public static <T> Result success(T data){
        return new Result(true,200,null,data);
    }

    public static Result failure(String errMsg){
        return new Result(false,500,errMsg,null);
    }

    public static Result failure(ErrorEnum error) {
        return new Result(false, error.getErrCode(), error.getErrMsg(), null);
    }
}
