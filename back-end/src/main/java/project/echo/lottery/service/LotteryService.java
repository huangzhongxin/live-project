package project.echo.lottery.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.echo.lottery.pojo.Award;
import project.echo.lottery.pojo.PersonEntity;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LotteryService {

    @Test
//    public Map<String, Object> lottery() throws Exception {
    public List<Map<String, Object>> lottery(Map<String, Object> config) throws Exception {


        DataHandler.init("C:\\Users\\KWM\\Desktop\\chatRecords.xlsx");


        LotteryFilter filter = new LotteryFilter(DataHandler.personEntityList, DataHandler.map1, DataHandler.map2);
//        Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-08-20 00:00:00");
//        Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-11-20 00:00:00");
        Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) config.get("endTime"));
        Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((String) config.get("startTime"));


        List<PersonEntity> filteredList =
                filter.filter(
                        startTime,
                        endTime,
                        (Integer) config.get("countLimit"),
                        (Boolean) config.get("filterTeacher"),
                        "#" + config.get("keyWord") + "#");

        // figure total awards number
        List<Map<String,Object>> awards = (List<Map<String,Object>>) config.get("award");
        Integer totalAwardNumber=0;
        for (Map award:awards) {
            totalAwardNumber+=(Integer)award.get("number");
        }
        List<PersonEntity> winners = DepthFilter.deepScreen(filteredList, 0, totalAwardNumber);
        Map<String, Object> result = new HashMap<>();

        int winnerPosition=0;
        for (Map award:awards) {
            award.put("list",new ArrayList<>());
            for (int i=0;i<(Integer)award.get("number");++i){
                Map<String, Object> winnerInfo=new HashMap<>();
                winnerInfo.put("name",winners.get(winnerPosition).getName());
                winnerInfo.put("qq",winners.get(winnerPosition).getQq_number());
                ++winnerPosition;
                ((List)award.get("list")).add(winnerInfo);
            }
        }
//        System.out.println(awards);
        return awards;
    }
}
