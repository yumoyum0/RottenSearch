package searchEngine.controller.star;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import searchEngine.entity.Star;
import searchEngine.pojo.Result;
import searchEngine.service.StarService;

import java.util.List;

/**
 * @Author xun
 * @create 2022/5/18 17:11
 */
@Slf4j
@Controller
@RequestMapping(value = "/star")
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
    String createStars(Integer userId,String starname) {
        // mock userId
        userId = 1;
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

    /**
     * 修改收藏夹名
     * @param starName 收藏夹原名
     * @param newStarName 改后的收藏夹名
     * @return 修改结果
     * */
    @PutMapping("/modifyStar")
    public Boolean modifyStar (String starName, String newStarName){
        return starService.modifyStar(starName,newStarName);
    }

    /**
     * 收藏夹页面
     * @param userId 用户id
     * @return 收藏夹页面
     * */
    @GetMapping("/getStars")
    public String getStars (Integer userId,Model model){
        List<Star> starList = starService.getStars(userId);
        model.addAttribute("starList",starList);
        return "star";
    }



}
