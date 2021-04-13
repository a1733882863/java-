package com.date;

import cn.hutool.core.lang.Assert;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestDate {

    public static void main(String[] args) throws InterruptedException, ParseException {
        //输入想要查询的日期范围, startDate  endDate
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date startDate1 = sdf.parse("2021-02-01");
        java.util.Date endDate1 = sdf.parse("2021-02-05");
        String startDate = sdf.format(startDate1);
        String endDate = sdf.format(endDate1);
        int diffrenceNum = differentDays(startDate1, endDate1);
        Calendar calendar = new GregorianCalendar();

        String basedir = "C:\\Users\\xz\\Desktop\\报表\\";
        //java.util.Date tempDate = new Date(startDate.getTime());
        java.util.Date tempDate = sdf.parse(String.valueOf(startDate));
        //先for日期
        for (int i = 0;i <= diffrenceNum; i++){

            //根据日期创建文件夹
            String finaldir = basedir + sdf.format(tempDate) + "\\";
            File file = new File(finaldir);
            if (!file.exists()) {
                file.mkdirs();
            }
            calendar.setTime(tempDate);
            calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
            tempDate = calendar.getTime();
        }
        calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
        tempDate = calendar.getTime();
        System.out.println("日期操作正确否?" + tempDate.compareTo(endDate1));
    }
    public static int differentDays(java.util.Date date1,java.util.Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

}
