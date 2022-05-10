package searchEngine.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import searchEngine.config.properties.RSAProperties;

/**
 * @Author: yumo
 * @Description: RSA配置类
 * @DateTime: 2022/5/10 15:36
 **/
@Configuration
@EnableConfigurationProperties(RSAProperties.class)
public class RSAConfig {
}
