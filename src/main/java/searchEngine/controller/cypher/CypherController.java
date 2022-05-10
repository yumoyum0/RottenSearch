package searchEngine.controller.cypher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import searchEngine.pojo.Result;
import searchEngine.utils.AesEncryptUtils;
import searchEngine.utils.RSAUtils;

/**
 * @Author: yumo
 * @Description: 解密测试
 * @DateTime: 2022/5/10 20:23
 **/
@RestController
@RequestMapping("/cypher")
public class CypherController {

    @Value("${rsa.public-key}")
    private String publicKey;

    @Value("${rsa.private-key}")
    private String privateKey;

    /**
     * 解密
     * @param encrypted 被公钥加密后的aesKey
     * @param aesKey aesKey
     * @param requestData 请求数据
     * @return 解密后的数据
     */
    @PostMapping("/decrypt")
    public Result decrypt(@RequestParam(value = "encrypted",required = false) String encrypted,
                          @RequestParam(value = "aseKey",required = false) String aesKey,
                          @RequestParam("requestData") String requestData) throws Exception {

        String aesK="";
        if(null!=encrypted){
            aesK = RSAUtils.decryptDataOnJava(encrypted, privateKey);
        }else if(null!=aesKey){
            aesK = aesKey;
        }

        String decryptedData = AesEncryptUtils.decrypt(requestData, aesK).replaceAll("\r\n  ","");
        System.out.println("解密："+decryptedData);
        return Result.success(decryptedData);
    }
}
