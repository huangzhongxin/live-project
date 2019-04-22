package project.echo.lottery.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
}
