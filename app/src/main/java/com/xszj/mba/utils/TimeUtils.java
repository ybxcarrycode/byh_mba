package com.xszj.mba.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 格式化时间的工具
 * Created by pc on 2015/9/28.
 */
public class TimeUtils {

    public static String generateTime1(String position) {
        try {
            long vedioTime = Long.parseLong(position.substring(0,position.length()-2));

            int totalSeconds = (int) (vedioTime / 1000);

            int seconds = totalSeconds % 60;
            int minutes = (totalSeconds / 60) % 60;
            int hours = totalSeconds / 3600;

            if (hours > 0) {
                return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds).toString();
            } else {
                return String.format(Locale.US, "%02d:%02d", minutes, seconds).toString();
            }
        }catch (Exception e){
            return position;
        }
    }


    public static String longToData(long time) {

        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//你需要的时间格式
        String dateString = formatter.format(currentTime);//得到字符串类型的时间

        return dateString;
    }

    public static String nosLongToData(long time) {

        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//你需要的时间格式
        String dateString = formatter.format(currentTime);//得到字符串类型的时间

        return dateString;
    }

    public static long calTimeDiff(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date d1 = df.parse(time);
            Date d2 = df.parse(nosLongToData(System.currentTimeMillis()));
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别

            long days = diff / (1000 * 60 * 60 * 24);
            System.out.println("" + days + "天");
            return days;
        } catch (Exception e) {
        }
        return 0;
    }

    public static String ossLongToData(long time) {

        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH");//你需要的时间格式
        String dateString = formatter.format(currentTime);//得到字符串类型的时间

        String timeChuo = String.valueOf(time);
        dateString = dateString + "/" + timeChuo;
        return dateString;

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH");
//        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//        String str = formatter.format(curDate);

    }

}
