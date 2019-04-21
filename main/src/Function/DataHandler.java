package Function;

import Entity.PersonEntity;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataHandler {

    public static List<PersonEntity> personEntityList = new ArrayList<PersonEntity>();
    public static File file;
    public static Map<String,Integer> map1=new HashMap<String, Integer>(); //QQ+类型  0学生  1助教/老师
    public static Map<String,Integer> map2=new HashMap<String, Integer>(); //QQ+发言次数

    public static void init() throws Exception {
        String filepath = "C:\\Users\\Ldq\\Desktop\\chatRecords.xlsx";
        file = new File(filepath);

        //Read Excel
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        // sheet.getRows()返回该页的总行数
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
            PersonEntity personEntity = new PersonEntity();
            List<String> cells=new ArrayList<String>();
            Row row=sheet.getRow(i);
            // sheet.getColumns()返回该页的总列数
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                Cell cellinfo = row.getCell(j);
                if(cellinfo==null)
                    cells.add("");
                else
                    cells.add(cellinfo.getStringCellValue());
            }
            if(cells.get(3).equals("系统消息"))
                continue;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d H:m:s");
            Date date=dateFormat.parse(cells.get(0)+" "+cells.get(1));
            personEntity.getCalendar().setTime(date);
            personEntity.setQq_number(cells.get(2));
            personEntity.setName(cells.get(3));
            personEntity.setContext(cells.get(4));
            personEntityList.add(personEntity);
        }

        //Map1
        for(int i=0;i<DataHandler.personEntityList.size();i++){
            String qq=personEntityList.get(i).getQq_number();
            String name=personEntityList.get(i).getName();
            if(name.contains("助教")||name.contains("教师")){
                map1.put(qq,1);
            }else
                map1.put(qq,0);
        }

        //Map2
        for(int i=0;i<DataHandler.personEntityList.size();i++){
            String qq=personEntityList.get(i).getQq_number();
            Integer count;
            if(map2.containsKey(qq))
                count=map2.get(qq)+1;
            else
                count=1;
            map2.put(qq,count);
        }

    }
}
