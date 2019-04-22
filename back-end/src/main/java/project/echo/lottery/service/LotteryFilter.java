package project.echo.lottery.service;

import project.echo.lottery.pojo.PersonEntity;

import java.util.*;

public class LotteryFilter {
    static final Integer STUDENT_TYPE = 0;
    static final Integer TEACHER_TYPE = 1;

    /**
     * person information, including [name, chat time, qq number, context]
     */
    private List<PersonEntity> personList = new ArrayList<PersonEntity>();
    /**
     * the type of the person with specific qq number
     * qq as the key and type as the value
     * STUDENT_TYPE:0    TEACHER_TYPE:1
     */

    private Map<String, Integer> typeMap = new HashMap<String, Integer>();
    /**
     * the number count of chat times of person with specific qq
     * qq as the key and chat count as the value
     */
    private Map<String, Integer> chatCountMap = new HashMap<String, Integer>();

    /**
     * @param personList
     * @param typeMap
     * @param chatCountMap
     */
    public LotteryFilter(List<PersonEntity> personList, Map<String, Integer> typeMap, Map<String, Integer> chatCountMap) {
        this.chatCountMap = chatCountMap;
        this.personList = personList;
        this.typeMap = typeMap;
    }

    /**
     * filter those person who could take part in draw,
     * should be in time interval and be active ,
     * if the teacher could take part in draw id depend on you
     *
     * @param startTime     start chat time in draw interval
     * @param endTime       end chat time in draw interval
     * @param countLimit    activity limit, for example at least chat over 10 times
     * @param filterTeacher teacher can take part in draw or not
     *                      true:join     false:not join
     * @param keyWord       only if user enter keyWord could take part in draw
     * @return the filtered list of person who could take part in draw
     */
    public List<PersonEntity> filter(Date startTime, Date endTime, Integer countLimit, Boolean filterTeacher,String keyWord) {
        // find the start/end time position
        Map<String, Integer> durationChatCount = new HashMap<String, Integer>();
        for (int i = 0; i < personList.size(); ++i) {
            PersonEntity p = personList.get(i);

            // skip time out of time duration
            if (endTime.compareTo(p.getDate()) < 0) {
                continue;
            }
            if (startTime.compareTo(p.getDate()) > 0) {
                continue;
            }

            // filter teacher
            if (filterTeacher && typeMap.get(p.getQq_number()) == TEACHER_TYPE) {
                continue;
            }

            // filter keyWord
            if (!p.getContext().contains(keyWord)){
                continue;
            }

            // figure chat count
            // if appear first time, set count to 1
            if (!durationChatCount.containsKey(p.getQq_number())) {
                durationChatCount.put(p.getQq_number(), 1);
            }
            // already appear before, add count
            else {
                durationChatCount.put(p.getQq_number(), durationChatCount.get(p.getQq_number()) + 1);
            }
        }

        // filter chat count that less then count limit
        for (Iterator<Map.Entry<String, Integer>> iterator = durationChatCount.entrySet().iterator();
             iterator.hasNext(); ) {
            Map.Entry<String, Integer> item = iterator.next();
            if (item.getValue() < countLimit) {
                iterator.remove();
            }
        }

        // get available list including those who can take part in draw
        List<PersonEntity> filteredList = new LinkedList<PersonEntity>();
        for (int i = 0; i < personList.size(); ++i) {
            PersonEntity p = personList.get(i);

            if (endTime.compareTo(p.getDate()) < 0) {
                // if end position=-1 means first assignment, else not
//                endPosition = endPosition == -1 ? i : endPosition;
                continue;
            }
            if (startTime.compareTo(p.getDate()) > 0) {
//                startPosition = startPosition == -1 ? i : startPosition;
                continue;
            }

            if (durationChatCount.containsKey(p.getQq_number())) {
                filteredList.add(0, p);
            }
        }

        return filteredList;
    }
}
