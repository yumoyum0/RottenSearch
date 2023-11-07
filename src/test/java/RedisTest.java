import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import searchEngine.SearchEngineApplication;
import searchEngine.service.RedisService;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchEngineApplication.class)
public class RedisTest {
    @Autowired
    private RedisService redisService;
    @Test
    public void redisTest(){
        redisService.set("111",111);
        System.out.println(redisService.get("111"));
    }
}
