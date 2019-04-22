package project.echo.lottery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class LotteryService {

    public Map<String, Object> lottery() throws Exception {
        DataHandler.init("C:\\Users\\KWM\\Desktop\\chatRecords.xlsx");
        LotteryFilter filter = new LotteryFilter(DataHandler.personEntityList, DataHandler.map1, DataHandler.map2);
        Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-08-20 00:00:00");
        Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-11-20 00:00:00");
        filter.filter(startTime, endTime, 0, true, "#我要红包#");
        return null;
    }
}
