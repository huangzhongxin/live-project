package project.echo.lottery.controller;

import org.springframework.web.bind.annotation.*;
import project.echo.lottery.pojo.Award;

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

    @GetMapping("/hello")
    public Map<String,Object> sayHello(){
        Map<String,Object> result=new HashMap<>();
        result.put("content","hello world");
        return result;
    }

    @PostMapping
    public Map<String,Object> lottery(@RequestBody Map<String,Object> info){
        System.out.println(info);
        Map<String,Object> result =new HashMap<>();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date startTime= null;
        Date endTime= null;
        try {
            startTime = sdf.parse((String)info.get("startTime"));
            endTime = sdf.parse((String)info.get("endTime"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Boolean filterTeacher=(Boolean)info.get("filterTeacher");
        Integer countLimit=(Integer)info.get("countLimit");
        String keyWord=(String)info.get("keyWord");
        String wenan=(String)info.get("wenan");
        Boolean filterRepeat=(Boolean)info.get("filterRepeat");
        List<Award> jx=(List<Award>)info.get("jx");

        System.out.println("startTime=>"+startTime);
        System.out.println("endTime=>"+endTime);
        System.out.println("filterTeacher=>"+filterTeacher);
        System.out.println("countLimit=>"+countLimit);
        System.out.println("keyWord=>"+keyWord);
        System.out.println("wenan=>"+wenan);
        System.out.println("filterRepeat=>"+filterRepeat);
        System.out.println("jx=>"+jx);
        return result;
    }
}
