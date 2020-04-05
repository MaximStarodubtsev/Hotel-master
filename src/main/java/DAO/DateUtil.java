package DAO;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateUtil {
    public LocalDateTime stringToDate(String s){
        LocalDateTime result = null;
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            result  = LocalDateTime.parse(s, formatter);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result ;
    }
}
