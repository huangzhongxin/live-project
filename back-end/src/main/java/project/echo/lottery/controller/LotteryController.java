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
//        System.out.println(config);
        Map<String,Object> result =new HashMap<>();

//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date startTime= null;
//        Date endTime= null;
//        try {
//            startTime = sdf.parse((String)config.get("startTime"));
//            endTime = sdf.parse((String)config.get("endTime"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Boolean filterTeacher=(Boolean)config.get("filterTeacher");
//        Integer countLimit=(Integer)config.get("countLimit");
//        String keyWord=(String)config.get("keyWord");
//        String writing=(String)config.get("writing");
//        Boolean filterRepeat=(Boolean)config.get("filterRepeat");
//        List<Award> jx=(List<Award>)config.get("award");
//
//        System.out.println("startTime=>"+startTime);
//        System.out.println("endTime=>"+endTime);
//        System.out.println("filterTeacher=>"+filterTeacher);
//        System.out.println("countLimit=>"+countLimit);
//        System.out.println("keyWord=>"+keyWord);
//        System.out.println("writing=>"+writing);
//        System.out.println("filterRepeat=>"+filterRepeat);
//        System.out.println("jx=>"+jx);

        List<Map<String,Object>> list=null;
        try {
            list=lotteryService.lottery(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Response(true,200,"",list);
    }
}
