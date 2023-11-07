package searchEngine;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Author: yumo
 * @Description: 主运行类
 * @DateTime: 2022/5/9 22:27
 **/
@SpringBootApplication
@EnableCaching
public class SearchEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchEngineApplication.class, args);
    }

}

