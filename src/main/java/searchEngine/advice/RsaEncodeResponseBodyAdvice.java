package searchEngine.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import searchEngine.annotation.SecurityParameter;
import searchEngine.utils.AesEncryptUtils;
import searchEngine.utils.RSAUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * @Author: yumo
 * @Description: 响应体加密增强器
 * @DateTime: 2022/5/10 18:32
 **/
@Component
@ControllerAdvice(basePackages = "searchEngine.controller.search")
@Slf4j
@Order(value = 2)
public class RsaEncodeResponseBodyAdvice implements ResponseBodyAdvice {

    @Value("${rsa.public-key}")
    private String publicKey;

    @Value("${rsa.private-key}")
    private String privateKey;


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("------响应数据加密-------");
        boolean encode = false;
        if (Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(SecurityParameter.class)) {
            //获取注解配置的包含和去除字段
            SecurityParameter serializedField = methodParameter.getMethodAnnotation(SecurityParameter.class);
            //出参是否需要加密
            assert serializedField != null;
            encode = serializedField.outEncode();
        }
        if (encode) {
            log.info("对方法method :[" + methodParameter.getMethod().getName() + "]返回数据进行加密");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                //获取响应数据
                String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
                // 生成一个解数据的秘钥
                String aesKey = getRandomString(16);
                // 用 公钥 将 秘钥 进行加密
                String encrypted = RSAUtils.encryptedDataOnJava(aesKey, publicKey);
                // 用 秘钥 将 响应数据 进行加密
                String requestData = AesEncryptUtils.encrypt(result, aesKey);
                System.out.println("aesKey"+aesKey);
                System.out.println("解密："+ AesEncryptUtils.decrypt(requestData,aesKey));
                Map<String, String> map = new HashMap<>();
                map.put("encrypted", encrypted);
                map.put("requestData", requestData);
                return map;
            } catch (Exception e) {
                e.printStackTrace();
                log.error("对方法method :[" + methodParameter.getMethod().getName() + "]返回数据进行解密出现异常：" + e.getMessage());
            }
        }
        return body;
    }


    /**
     * 创建指定位数的随机字符串
     * @param length 表示生成字符串的长度
     * @return 字符串
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
