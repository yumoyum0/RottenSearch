import org.junit.Test;
import searchEngine.utils.AesEncryptUtils;

/**
 * 测试Aes加密
 */
public class AesTest {
    @Test
    public void testDemo() throws Exception {
        String s = "hello,您好";

        System.out.println("s:" + s);

        String s1 = AesEncryptUtils.encrypt(s, "jkl;POIU1234++==");
        System.out.println("s1:" + s1);

        System.out.println("s2:"+AesEncryptUtils.decrypt(s1, "jkl;POIU1234++=="));
    }
}
