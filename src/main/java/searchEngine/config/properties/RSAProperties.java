package searchEngine.config.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: RSA属性
 **/

@Slf4j
@ConfigurationProperties(prefix = "rsa")
public class RSAProperties {

    /**
     * 密文
     */
    private String secret;


    /**
     * 公钥
     */
    private static String publicKey;


    /**
     * 私钥
     */
    private static String privateKey;

    public static String getPublicKey(){
        return publicKey;
    }

    public static String getPrivateKey(){
        return privateKey;
    }



}
