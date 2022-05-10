package searchEngine.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/5/10 16:42
 **/
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface SecurityParameter {
    /**
     * 入参是否解密，默认为true
     */
    boolean inDecode() default true;

    /**
     * 返回数据是否加密,默认为true
     */
    boolean outEncode() default true;
}
