package searchEngine.config.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yumo
 */
@Slf4j
@ConfigurationProperties(prefix = "index")
public class IndexProperties {

    private static String path;

    private static Integer numHits;

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        IndexProperties.path = path;
    }
}
