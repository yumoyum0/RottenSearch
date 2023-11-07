import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import searchEngine.SearchEngineApplication;
import searchEngine.utils.AesEncryptUtils;
import searchEngine.utils.RSAUtils;

import java.util.Random;

/**
 * 测试Aes加密
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchEngineApplication.class)
public class AesTest {

    @Value("${rsa.public-key}")
    private String publicKey;

    @Value("${rsa.private-key}")
    private String privateKey;

    @Test
    public void rsaAesTest() throws Exception {
        System.out.println("------------encrypt start------------");

        //数据
        String data = "南京邮电大学";
        System.out.println("origin data: "+data);

        // 生成一个加解密数据的 aes秘钥
        String aesKey = getRandomString(16);
        System.out.println("aesKey: "+aesKey);

        // 用 rsa公钥 将 aes秘钥 进行加密
        String encryptedAesKey = RSAUtils.encryptedDataOnJava(aesKey, publicKey);
        System.out.println("encrypted aesKey: "+encryptedAesKey);

        // 用 aes秘钥 将 数据 进行加密
        String encryptedData = AesEncryptUtils.encrypt(data, aesKey);
        System.out.println("encrypted data: "+encryptedData);

        System.out.println("------------encrypt  end------------");
        System.out.println();
        System.out.println("------------decrypt start------------");

        // 用 rsa密钥 给 aes秘钥 解密
        System.out.println("encrypted aesKey: "+encryptedAesKey);
        String decryptedAesKey = RSAUtils.decryptDataOnJava(encryptedAesKey, privateKey);
        System.out.println("decrypted aesKey: "+decryptedAesKey);

        // 用解密后的 aes秘药 给数据解密
        System.out.println("encrypted data: "+encryptedData);
        String decryptedData = AesEncryptUtils.decrypt(encryptedData, decryptedAesKey);
        System.out.println("decrypted data: "+decryptedData);
        System.out.println("------------decrypt  end------------");
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
    @Test
    public void testDemo() throws Exception {
        String s = "hello,您好";

        System.out.println("s:" + s);

        String s1 = AesEncryptUtils.encrypt(s, "jkl;POIU1234++==");
        System.out.println("s1:" + s1);

        System.out.println("s2:"+AesEncryptUtils.decrypt(s1, "jkl;POIU1234++=="));
    }
}
