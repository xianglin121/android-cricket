package com.onecric.live.util;

import android.content.Context;

import com.onecric.live.R;
import com.tencent.qcloud.tuicore.util.DateTimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static String timeConversion2(long time) {
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

        return hour !=0 ? ((hour + " hrs ")+(minutes != 0 ? (": "+minutes + " min") : "")) : ((minutes != 0 ? (minutes + " min : ") : "")+(sencond + " sec"));
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

    public static String[] weekDays = null;
    public static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul","Aug","Sept","Oct","Nov","Dec"};

    /**
     *
     * @param day yyyy-MM-dd
     * @return
     */
    public static String[] getDayInfo(String day, Context context){
        if(weekDays == null){
            weekDays = new String[]{context.getString(R.string.d_sunday), context.getString(R.string.d_monday), context.getString(R.string.d_tuesday),
                    context.getString(R.string.d_wednesday), context.getString(R.string.d_thursday), context.getString(R.string.d_friday),
                    context.getString(R.string.d_saturday)};
        }
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
                strings[2] = (count == 0 ? context.getString(R.string.today) : (count == -1?context.getString(R.string.yesterday):context.getString(R.string.tomorrow)));
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

    public static String[] getDayInfo(long day,Context context){
        if(weekDays == null){
            weekDays = new String[]{context.getString(R.string.d_sunday), context.getString(R.string.d_monday), context.getString(R.string.d_tuesday),
                    context.getString(R.string.d_wednesday), context.getString(R.string.d_thursday), context.getString(R.string.d_friday),
                    context.getString(R.string.d_saturday)};
        }
        String[] strings = new String[4];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//要转换的时间格式
        Date date;
        Calendar cal = Calendar.getInstance();
        try {
            date = new Date(day);
            sdf.format(date);
            cal.setTime(date);
            strings[0] = (cal.get(Calendar.DATE) < 9 ? "0" : "" )+(cal.get(Calendar.DATE));
            strings[1] = months[cal.get(Calendar.MONTH) < 0 ? 0 : cal.get(Calendar.MONTH)%11];
            int count = (int) (( date.getTime() - sdf.parse(sdf.format(new Date())).getTime() )) / (1000*3600*24);
            strings[3] = count+"";
            if(count > -2 && count < 2){
                strings[2] = (count == 0 ? context.getString(R.string.today) : (count == -1?context.getString(R.string.yesterday):context.getString(R.string.tomorrow)));
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

    //将时间字符串转为时间戳字符串
    public static Long getStringTimes(String time,String pattern) {
        Long longTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            longTime = sdf.parse(time).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime;
    }

    /**

     * 距离今天的绝对时间

     *

     * @param date

     * @return
     */
    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;
    public static Calendar calendar = Calendar.getInstance();

    public static String toToday(String date) {
        return toToday(DateTimeUtil.getStringToDate(date, "yyyy-MM-dd HH:mm:ss"));
    }
    public static String toToday(Date date) {
        return toToday(date.getTime());
    }
    public static String toToday(long date) {
        long time = date ;
        long now = new Date().getTime()/ 1000;
        long ago = now - time;
        if (ago <= ONE_HOUR)
            return (ago / ONE_MINUTE) + ((ago / ONE_MINUTE) < 2 ?" minute":" minutes") + " ago";
        else if (ago <= ONE_DAY)
//            return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE) + "分钟";
            return (ago / ONE_HOUR) + ((ago / ONE_HOUR) < 2 ?" hour":" hours") + " ago";
        else if (ago <= ONE_DAY * 2)
//            return "昨天" + (ago - ONE_DAY) / ONE_HOUR + "点" + (ago - ONE_DAY) % ONE_HOUR / ONE_MINUTE + "分";
            return "1 day ago";
        else if (ago <= ONE_DAY * 3) {
//            long hour = ago - ONE_DAY * 2;
//            return "前天" + hour / ONE_HOUR + "点" + hour % ONE_HOUR / ONE_MINUTE + "分";
            return "2 days ago";
        } else if (ago <= ONE_MONTH) {
            long day = ago / ONE_DAY;
//            long hour = ago % ONE_DAY / ONE_HOUR;
//            long minute = ago % ONE_DAY % ONE_HOUR / ONE_MINUTE;
//            return day + "天前" + hour + "点" + minute + "分";
            return day+" days ago";
        } else if (ago <= ONE_YEAR) {
//            long month = ago / ONE_MONTH;
//            long day = ago % ONE_MONTH / ONE_DAY;
//            long hour = ago % ONE_MONTH % ONE_DAY / ONE_HOUR;
//            long minute = ago % ONE_MONTH % ONE_DAY % ONE_HOUR / ONE_MINUTE;
//            return month + "个月" + day + "天" + hour + "点" + minute + "分前";
            return (ago / ONE_MONTH) + ((ago / ONE_MONTH) < 2 ?" month":" months") + " ago";
        } else {
//            long year = ago / ONE_YEAR;
//            long month = ago % ONE_YEAR / ONE_MONTH;
//            long day = ago % ONE_YEAR % ONE_MONTH / ONE_DAY;
//            return year + "年前" + month + "月" + day + "天";
            return (ago / ONE_YEAR) + ((ago / ONE_YEAR) < 2 ?" year":" years") + " ago";
        }

    }
}
