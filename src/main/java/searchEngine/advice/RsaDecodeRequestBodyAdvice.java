package searchEngine.advice;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import searchEngine.annotation.SecurityParameter;
import searchEngine.utils.RSAUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Author: yumo
 * @Description: 请求体解密增强器
 * @DateTime: 2022/5/10 16:45
 **/
@ControllerAdvice(basePackages = "searchEngine.controller" )
@Slf4j
@Order(value = 1)
public class RsaDecodeRequestBodyAdvice implements RequestBodyAdvice {
    @Value("${rsa.private-key}")
    private String privateKey;

    /**
     * 首先调用以确定此拦截器是否适用，默认为ture
     * @param methodParameter 方法参数
     * @param targetType 目标类型，不一定与方法参数类型相同
     * @param converterType 所选转换器类型
     * @return 是否应调用此拦截器
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        boolean encode=false;


        if(Objects.requireNonNull(parameter.getMethod()).isAnnotationPresent(SecurityParameter.class)){
            SecurityParameter methodAnnotation = parameter.getMethodAnnotation(SecurityParameter.class);
            assert methodAnnotation != null;
            encode = methodAnnotation.inDecode();
        }
        if (encode){
            log.info("对方法method :[" + parameter.getMethod().getName() + "]返回数据进行解密");
            return new MyHttpInputMessage(httpInputMessage);
        }else {
            return httpInputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    class MyHttpInputMessage implements HttpInputMessage{
        private HttpHeaders httpHeaders;

        private InputStream inputStream;

        public MyHttpInputMessage(HttpInputMessage httpInputMessage) throws IOException {
            this.httpHeaders=httpInputMessage.getHeaders();
            String easpString = easpString(IOUtils.toString(httpInputMessage.getBody(), StandardCharsets.UTF_8));
            this.inputStream=IOUtils.toInputStream(RSAUtils.decryptDataOnJava(easpString,privateKey),StandardCharsets.UTF_8);
        }
        @Override
        public InputStream getBody() throws IOException {
            return null;
        }

        @Override
        public HttpHeaders getHeaders() {
            return null;
        }

        public String easpString(String requestData){
            if(null != requestData && !requestData.isEmpty()){
                String s = "{\"requestData\":";
                if (!requestData.startsWith(s)){
                    throw new RuntimeException("参数[requestData]缺失异常！");
                }
                int closeLen = requestData.length()-1;
                int openLen = "{\"requestData\":".length();
                String substring = StringUtils.substring(requestData, openLen, closeLen);
                return substring;
            }
            return null;
        }
    }
}
