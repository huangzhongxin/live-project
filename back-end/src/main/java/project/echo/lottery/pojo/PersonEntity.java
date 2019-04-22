package project.echo.lottery.pojo;

import java.util.Calendar;
import java.util.Date;

public class PersonEntity {
    Calendar calendar;
    String name;
    String qq_number;
    String context;

    public PersonEntity(Calendar calendar, String name, String qq_number, String context) {
        this.calendar = calendar;
        this.name = name;
        this.qq_number = qq_number;
        this.context = context;
    }

    public PersonEntity() {
        calendar=Calendar.getInstance();
        name=new String();
        qq_number=new String();
        context=new String();
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getName() {
        return name;
    }

    public String getQq_number() {
        return qq_number;
    }

    public String getContext() {
        return context;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQq_number(String qq_number) {
        this.qq_number = qq_number;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getDate(){
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "date=" + getDate().toString() +
                ", name='" + name + '\'' +
                ", qq_number='" + qq_number + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
