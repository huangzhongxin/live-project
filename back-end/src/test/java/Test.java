
import function.DataHandler;
import java.util.Set;

public class Test {

    @org.junit.Test
    public void run() throws Exception{
        DataHandler.init();
        for(int i=0;i<DataHandler.personEntityList.size();i++)
            System.out.println(DataHandler.personEntityList.get(i).toString());
        System.out.println(DataHandler.personEntityList.size());

        System.out.println("-----------------------");

        Set keys1=DataHandler.map1.keySet();
        for (Object key : keys1) {
            System.out.println(key+"="+DataHandler.map1.get(key));
        }
        System.out.println(DataHandler.map1.size());

        System.out.println("-----------------------");

        Set keys2=DataHandler.map2.keySet();
        for (Object key : keys2) {
            System.out.println(key+"="+DataHandler.map2.get(key));
        }
        System.out.println(DataHandler.map2.size());

    }
}
