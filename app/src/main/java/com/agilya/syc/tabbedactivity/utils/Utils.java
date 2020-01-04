package com.agilya.syc.tabbedactivity.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String convertDate(String pDate){
        SimpleDateFormat oldFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newFormatDate = new SimpleDateFormat("dd/MM/yyyy");

        pDate = pDate.substring(0,10);
        try {
            pDate = newFormatDate.format(oldFormatDate.parse(pDate));
        }
        catch(Exception e){
            pDate = newFormatDate.format(new Date());
        }finally {
            return pDate;
        }
    }
}
