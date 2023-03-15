package com.onecric.live.util;

import com.onecric.live.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimeUtil {
    //将时间戳转化为对应的时间  日-时-分-秒
    public static String timeConversion(long time) {
        long day = 0;
        long hour = 0;
        long minutes = 0;
        long sencond = 0;
        long dayTimp = time % (3600 * 24);
        long hourTimp = time % 3600;

        if (time >= 86400) {
            day = time / (3600 * 24);
            if (dayTimp != 0) {
                time = time - (day * 24 * 60 * 60);
                if (time >= 3600 && time < 86400) {
                    hour = time / 3600;
                    if (hourTimp != 0) {
                        if (hourTimp >= 60) {
                            minutes = hourTimp / 60;
                            if (hourTimp % 60 != 0) {
                                sencond = hourTimp % 60;
                            }
                        } else if (hourTimp < 60) {
                            sencond = hourTimp;
                        }
                    }
                } else if (time < 3600) {
                    minutes = time / 60;
                    if (time % 60 != 0) {
                        sencond = time % 60;
                    }
                }
            }
        } else if (time >= 3600 && time < 86400) {
            hour = time / 3600;
            if (hourTimp != 0) {
                if (hourTimp >= 60) {
                    minutes = hourTimp / 60;
                    if (hourTimp % 60 != 0) {
                        sencond = hourTimp % 60;
                    }
                } else if (hourTimp < 60) {
                    sencond = hourTimp;
                }
            }
        } else if (time < 3600) {
            minutes = time / 60;
            if (time % 60 != 0) {
                sencond = time % 60;
            }
        }
//        return ((hour != 0 ? ((hour + "h ")): "")) + (minutes < 10 ? ("0" + minutes) : minutes) + "m " + (sencond < 10 ? ("0" + sencond) : sencond) + "s";
//        return (hour != 0 ? ((hour + "h ")) : "") + (minutes != 0 ? (minutes + "m ") : "") + (sencond + "s");

        return hour !=0 ? ((hour + "hr ")+(minutes != 0 ? (minutes + "m ") : "")) : ((minutes != 0 ? (minutes + "m ") : "")+(sencond + "s"));
    }

    //将时间字符串转为时间戳字符串
    public static Long getStringTimestamp(String time) {
        Long longTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            longTime = sdf.parse(time).getTime() / 1000;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime;
    }

    //将时间戳转换为时间
    public static String stampToTime(Long time,String p) throws Exception{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(p, Locale.ENGLISH);
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;

    }

    public static String[] weekDays = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul","Aug","Sept","Oct","Nov","Dec"};

    /**
     *
     * @param day yyyy-MM-dd
     * @return
     */
    public static String[] getDayInfo(String day){
        String[] strings = new String[4];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//要转换的时间格式
        Date date;
        Calendar cal = Calendar.getInstance();
        try {
            date = sdf.parse(day);
            sdf.format(date);
            cal.setTime(date);
            strings[0] = (cal.get(Calendar.DATE) < 9 ? "0" : "" )+(cal.get(Calendar.DATE));
            strings[1] = months[cal.get(Calendar.MONTH) < 0 ? 0 : cal.get(Calendar.MONTH)%11];
            int count = (int) (( date.getTime() - sdf.parse(sdf.format(new Date())).getTime() )) / (1000*3600*24);
            strings[3] = count+"";
            if(count > -2 && count < 2){
                strings[2] = (count == 0 ? "Today" : (count == -1?"Yesterday":"Tomorrow"));
            }else{
                strings[2] = weekDays[cal.get(Calendar.DAY_OF_WEEK) - 1 < 0 ? 0 : cal.get(Calendar.DAY_OF_WEEK) - 1];
            }
        }catch (Exception e){
            strings[0] = "";
            strings[1] = "";
            strings[2] = "";
            strings[3] = "0";
            e.printStackTrace();
        }
        return strings;
    }
}
