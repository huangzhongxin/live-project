package project.echo.lottery.service;

import org.junit.Test;
import org.springframework.stereotype.Service;
import project.echo.lottery.pojo.PersonEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepthFilter {

    /**
     *
     * @param list filtered person list including those who can take part in draw
     * @param time repeat speak time interval
     * @param n total award number
     * @return winner list
     */
    public static List<PersonEntity> deepScreen(List<PersonEntity> list, int time,int n) {
        List<PersonEntity> drawList = new ArrayList<>();
        drawList.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < drawList.size(); j++) {
                if (list.get(i).getQq_number() == drawList.get(j).getQq_number() &&
                        list.get(i).getContext().equals(drawList.get(j).getContext()) &&
                        list.get(i).getCalendar().getTimeInMillis() - drawList.get(j).getCalendar().getTimeInMillis() < time * 1000) {
                    drawList.remove(j);
                }
            }
            drawList.add(list.get(i));
        }
//        return drawList;
        return luckyDraw(drawList,n);
    }

    public static List<PersonEntity> luckyDraw(List<PersonEntity> drawList, int n) {
        System.out.print(n);
        List<PersonEntity> winList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int random = (int) (Math.random() * drawList.size());
            boolean flag = true;
            PersonEntity user = new PersonEntity(
                    drawList.get(random).getCalendar(),
                    drawList.get(random).getName(),
                    drawList.get(random).getQq_number(),
                    drawList.get(random).getContext());

            for (int j = 0; j < winList.size() && flag; j++) {
                if (winList.get(j).getQq_number() == user.getQq_number()) {
                    flag = false;
                    i--;
                }
            }
            if (flag) {
                winList.add(user);
            }
        }
        return winList;
    }
}
