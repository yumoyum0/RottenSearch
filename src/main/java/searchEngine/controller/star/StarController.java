package searchEngine.controller.star;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import searchEngine.pojo.Result;
import searchEngine.service.StarService;

/**
 * @Author xun
 * @create 2022/5/18 17:11
 */
@Slf4j
@Controller
@RequestMapping(value = "/star")
@ResponseBody
public class StarController {
    private final StarService starService;

    public StarController(StarService starService) {
        this.starService = starService;
    }

    /**
     * 创建收藏夹
     * @param starname 收藏夹名
     * @return 返回操作信息
     */
    @PostMapping ("/createstars")
    String createStars(String starname) {
        return starService.createStars(starname);
    }


    /**
     * 将网页添加到收藏夹中
     * @param starname 要加入的收藏夹的名称
     * @param desc 网页描述
     */
    @PostMapping("/add")
    String add (String starname, String desc) {
        return starService.addS(starname, desc);
    }

    /**
     * 删除收藏夹
     * @param starname 需要删除收藏夹的名字
     * @return 返回操作信息
     */
    @PostMapping("/deletestar")
    String deleteStars (String starname) {
        return starService.deleteStars(starname);
    }

    @PostMapping("/deletedoc")
    String deleteDoc (String starname, String desc) {
        return starService.deleteDoc(starname, desc);
    }
}
