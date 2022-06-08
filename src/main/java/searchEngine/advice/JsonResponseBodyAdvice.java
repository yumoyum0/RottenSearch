package searchEngine.advice;

import com.alibaba.fastjson2.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import searchEngine.pojo.Result;

/**
 * @Author: yumo
 * @Description: Json响应体增强器
 * 允许在执行@ResponseBody或ResponseEntity控制器方法之后，但在使用HttpMessageConverter写入正文之前自定义响应。
 * 实现可以直接在RequestMappingHandlerAdapter和ExceptionHandlerExceptionResolver中注册，或者更可能使用@ControllerAdvice进行注释，在这种情况下，它们都将被自动检测到。
 * @DateTime: 2022/5/10 14:50
 **/
@RestControllerAdvice(basePackages = "searchEngine.controller")
@Order(value = 3)
public class JsonResponseBodyAdvice implements ResponseBodyAdvice {


    /**
     * 是否执行转换，默认为true
     * @param returnType 带有@RestBody标注的方法的返回值类型
     * @param converterType 所选择的转换器类型
     * @return 如果beforeBodyWrite方法应该被执行，则返回true;否则返回false
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * 在执行@ResponseBody或ResponseEntity控制器方法之后，但在使用HttpMessageConverter写入正文之前自定义响应
     * @param body 需要被写入的主体
     * @param returnType 带有@RestBody标注的方法的返回值类型
     * @param selectedContentType 通过内容协商选择的内容类型
     * @param selectedConverterType 所选择的写入响应的转换器类型
     * @param request 当前请求
     * @param response 当前响应
     * @return 传入的主体或被修改的（可能是新的）实例
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Result result=new Result();
        if (body instanceof String) {
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            result=Result.success(body);
        }
        if (body instanceof Result) {
            result = (Result) body;
        }
        String jsonResult = JSON.toJSONString(result);
        System.out.println("result:"+jsonResult);
        return jsonResult;
    }
}
