package searchEngine.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import searchEngine.config.properties.RSAProperties;

/**
 * @Description: RSA配置类
 **/
@Configuration
@EnableConfigurationProperties(RSAProperties.class)
public class RSAConfig {
}
