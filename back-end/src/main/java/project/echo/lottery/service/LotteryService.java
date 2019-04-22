package project.echo.lottery.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.echo.lottery.pojo.Award;
import project.echo.lottery.pojo.PersonEntity;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LotteryService {
    @Value("${excel.path}")
    private String excelPath;
    @Value("${output.path}")
    private String outputPath;

    @Test
//    public Map<String, Object> lottery() throws Exception {
    public Map<String, Object> lottery(Map<String, Object> config) throws Exception {


//        DataHandler.init("C:\\Users\\KWM\\Desktop\\chatRecords.xlsx");
        DataHandler.init(excelPath);


        LotteryFilter filter = new LotteryFilter(DataHandler.personEntityList, DataHandler.map1, DataHandler.map2);
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
        List<Map<String, Object>> awards = (List<Map<String, Object>>) config.get("award");
        Integer totalAwardNumber = 0;
        for (Map award : awards) {
            totalAwardNumber += (Integer) award.get("number");
        }
        List<PersonEntity> winners = DepthFilter.deepScreen(filteredList, 0, totalAwardNumber);

        int winnerPosition = 0;
        for (Map award : awards) {
            award.put("list", new ArrayList<>());
            for (int i = 0; i < (Integer) award.get("number"); ++i) {
                Map<String, Object> winnerInfo = new HashMap<>();
                winnerInfo.put("name", winners.get(winnerPosition).getName());
                winnerInfo.put("qq", winners.get(winnerPosition).getQq_number());
                ++winnerPosition;
                ((List) award.get("list")).add(winnerInfo);
            }
        }
//        System.out.println(awards);
        Map<String, Object> result = new HashMap<>();
        result.put("file", outputPath);
        result.put("winners", awards);
        writeToFile("C:\\Users\\KWM\\Desktop\\winner.txt",awards);
        return result;
    }

    private void writeToFile(String filePath, List<Map<String, Object>> awards) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filePath, "UTF-8");
            for (Map award : awards) {
                for (int i = 0; i < (Integer) award.get("number"); ++i) {
                    writer.println(
                            award.get("name") + ","
                                    + ((List<Map<String, Object>>) award.get("list")).get(i).get("name") + "," +
                                    ((List<Map<String, Object>>) award.get("list")).get(i).get("qq"));
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
