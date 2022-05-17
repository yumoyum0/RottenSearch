package searchEngine.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import searchEngine.config.properties.IndexProperties;
import searchEngine.config.properties.RSAProperties;

/**
 * @author yumo
 */
@Configuration
@EnableConfigurationProperties(IndexProperties.class)
public class IndexConfig {
}
