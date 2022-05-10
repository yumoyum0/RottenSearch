package searchEngine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import searchEngine.entity.Star;
import searchEngine.mapper.StarMapper;
import searchEngine.service.StarService;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/5/10 14:02
 **/
@Service
public class StarServiceImpl extends ServiceImpl<StarMapper,Star> implements StarService {
}
