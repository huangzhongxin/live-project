package project.echo.lottery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.echo.lottery.pojo.Award;
import project.echo.lottery.pojo.Response;
import project.echo.lottery.service.LotteryFilter;
import project.echo.lottery.service.LotteryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/lottery")
public class LotteryController {

    @Autowired
    LotteryService lotteryService;

    @GetMapping("/hello")
    public Map<String,Object> sayHello(){
        Map<String,Object> result=new HashMap<>();
        result.put("content","hello world");
        return result;
    }

    @PostMapping
    public Response lottery(@RequestBody Map<String,Object> config){
        Map<String,Object> map=null;
        try {
            map=lotteryService.lottery(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Response(true,200,"",map);
    }
}
