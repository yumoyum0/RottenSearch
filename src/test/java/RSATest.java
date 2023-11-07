import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import searchEngine.utils.RSAUtils;

import java.security.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 **/

public class RSATest {

    private static Map<String, String> map = new HashMap<>();

    @Test
    public void RSATest() throws Exception {
        String content = "好好好";
        genKeyPair();
        //加密
        String secret = RSAUtils.encryptedDataOnJava(content, map.get("publicKey"));
        System.out.println("加密后："+secret);
        //解密
        System.out.println("解密后："+RSAUtils.decryptDataOnJava(secret,map.get("privateKey")));

    }

    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //私钥
        PrivateKey privateK = keyPair.getPrivate();
        //公钥
        PublicKey publicK = keyPair.getPublic();

        //PrivateKey2String
        String privateKey = new String(Base64.encodeBase64(privateK.getEncoded()));
        //PublicKey2String
        String publicKey = new String(Base64.encodeBase64(publicK.getEncoded()));
        System.out.println("公钥："+publicKey);
        System.out.println("私钥："+privateKey);
        map.put("publicKey",publicKey);
        map.put("privateKey",privateKey);
    }
}

