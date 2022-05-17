package searchEngine.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author: yumo
 * @Description: RSA工具类
 * @DateTime: 2022/5/10 15:25
 **/
public class RSAUtils {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 128;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    /**
     * 私钥解密
     * @param encryptedData 密文字节数组
     * @param privateKey 私钥
     * @return 明文字节数组
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);

        byte[] decryptedData = process(encryptedData,privateKey,cipher,MAX_DECRYPT_BLOCK);
        return decryptedData;
    }


    /**
     * java端私钥解密
     * @param data 密文
     * @param privateKey 私钥
     * @return 明文
     */
    public static String decryptDataOnJava(String data, String privateKey) {
        String temp = "";
        try {
            byte[] rs = Base64.decodeBase64(data);
            temp = new String(RSAUtils.decryptByPrivateKey(rs, privateKey), StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }


    /**
     * java端公钥加密
     * @param data 明文
     * @param publicKey 公钥
     * @return 密文
     */
    public static String encryptedDataOnJava(String data, String publicKey) {
        try {
            data = Base64.encodeBase64String(encryptByPublicKey(data.getBytes(), publicKey));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 公钥加密
     * @param data 明文字节数组
     * @param publicKey 公钥
     * @return 密文字节数组
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        Key publicK = KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(keyBytes));
        // 对数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicK);

        byte[] encryptedData = process(data,publicKey,cipher,MAX_ENCRYPT_BLOCK);
        return encryptedData;
    }

    /**
     * 执行流程
     * @param data 明文/密文 字节数组
     * @param key 公/私 钥匙
     * @param cipher 密码类的对象
     * @param MAX_BLOCK 最大 加密明文/解密密文 大小
     * @return 密文/明文 字节数组
     */
    public static byte[] process(byte[] data,String key,Cipher cipher,int MAX_BLOCK)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        //对数据分段加密/解密
        while (inputLen - offSet > 0) {
            if(inputLen - offSet > MAX_BLOCK){
                cache=cipher.doFinal(data,offSet,MAX_BLOCK);
            }else {
                cache=cipher.doFinal(data,offSet,inputLen-offSet);
            }
            out.write(cache,0,cache.length);
            i++;
            offSet=i*MAX_BLOCK;
        }
        byte[] processedData = out.toByteArray();
        out.close();
        return processedData;
    }



}
