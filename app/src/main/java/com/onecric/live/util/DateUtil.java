package com.onecric.live.util;

import android.text.TextUtils;
import android.text.format.Time;


import com.coorchice.library.utils.LogUtils;
import com.qiniu.android.utils.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * 时间格式化工具类
 */
public class DateUtil {
    private static boolean isInEasternEightZones() {
        return TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08");
    }

    private static boolean isTodayYear(long time) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        cal.setTime(date);
        return cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR));
    }

    /**
     * 根据输入的时间和输入的格式获取时间
     *
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTimeFormatted(long time, String timeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        String dateStr = sdf.format(new Date(time * 1000));
        return dateStr;
    }

    private static Date transformTime(Date date, TimeZone oldZone, TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }

    public static Date formatDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(strDate, pos);
        return date;
    }

    public static Date defaultFormatDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(strDate, pos);
        return date;
    }

    public static String formatString(Date date, int type) {
        DateFormat df = null;
        if (type == 1) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }
        return df.format(date);
    }

    public static int compareToday(long when) {
        when = when * 1000;
        Time time = new Time();
        time.set(when);
        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;
        time.set(System.currentTimeMillis());
        if (thenYear < time.year) {
            return -1;
        } else if (thenYear > time.year) {
            return 1;
        } else {
            if (thenMonth < time.month) {
                return -1;
            } else if (thenMonth > time.month) {
                return 1;
            } else {
                if (thenMonthDay < time.monthDay) {
                    if (thenMonthDay == time.monthDay - 1) {
                        //昨天
                        return -2;
                    }
                    return -1;
                } else if (thenMonthDay > time.monthDay) {
                    return 1;
                } else {
                    //今天
                    return 0;
                }
            }
        }
    }

    //年月 xx年xx月
    public static String longToYMC(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String timeString = sdf.format(date);//Date-->String
        timeString = timeString.replace("-", "年") + "月";
        return timeString;
    }

    //xx年xx月xx
    public static String longToYMDC(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timeString = sdf.format(date);//Date-->String
        String[] split = timeString.split("-");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(split[0]);
        stringBuffer.append("年");
        stringBuffer.append(split[1]);
        stringBuffer.append("月");
        stringBuffer.append(split[2]);
        return stringBuffer.toString();
    }

    //年月日时分
    public static String longToYMDHM(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timeString = sdf.format(date);//Date-->String
        return timeString;
    }

    //年月日时分秒
    public static String longToYMDHMS(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeString = sdf.format(date);//Date-->String
        return timeString;
    }

    //时分
    public static String longToHM(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String timeString = sdf.format(date);//Date-->String
        return timeString;
    }

    // 秒- 时分
    public static String integerToHM(int time) {
        return timeAddZero(time / 3600) + ":" + (time % 3600 / 60 == 0 ? "00" : timeAddZero(time % 3600 / 60));
    }

    private static String timeAddZero(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return time + "";
    }

    //时分秒
    public static String longToHMS(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeString = sdf.format(date);//Date-->String
        return timeString;
    }

    //月日时分
    public static String longToMDHM(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String timeString = sdf.format(date);//Date-->String
        return timeString;
    }

    //月日时分
    public static String longToMDHMS(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        String timeString = sdf.format(date);//Date-->String
        return timeString;
    }

    public static String longToYMD(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timeString = sdf.format(date);//Date-->String
        return timeString;
    }

    public static String longToMD(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        String timeString = sdf.format(date);//Date-->String
        return timeString;
    }

    public static String longToYM(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String timeString = sdf.format(date);//Date-->String
        return timeString;
    }

    /**
     * 获取某月的最后一天
     */
    public static long getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //格式化日期
        cal.set(year, month - 1, lastDay, 23, 59, 59);
        Date date = cal.getTime();
        return date.getTime();
    }

    /**
     * 获取某月的最后一天
     */
    public static long getLastDayOfMonth(long time) {
        Date oriDate = new Date(time);
        int month = oriDate.getMonth();
        int year = oriDate.getYear() + 1900;
        Calendar cal = Calendar.getInstance();
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //格式化日期
        cal.set(year, month + 1, 1, 0, 0, 0);
        Date date = cal.getTime();
        return date.getTime();
    }

    /**
     * 获取某月月的第一天
     */
    public static long getFirstDayOfMonth(long time) {
        Date oriDate = new Date(time);
        int month = oriDate.getMonth();
        int year = oriDate.getYear() + 1900;
        Calendar cal = Calendar.getInstance();
        //格式化日期
        cal.set(year, month, 1, 0, 0, 0);
        Date date = cal.getTime();
        return date.getTime();
    }

    public static String longToProgressFormate(long time) {
        StringBuffer stringBuffer = new StringBuffer();
        if (compareToday(time) == 0) {
            stringBuffer.append("今天");
            stringBuffer.append("\n ");
            stringBuffer.append(longToHM(time));
            return stringBuffer.toString();
        } else if (compareToday(time) == -2) {
            stringBuffer.append("昨天");
            stringBuffer.append("\n ");
            stringBuffer.append(longToHM(time));
            return stringBuffer.toString();
        } else {
            String timeString = longToMDHM(time);
            timeString = timeString.replace("-", "月");
            timeString = timeString.replace(" ", "日\n ");
            stringBuffer.append(timeString);
            return stringBuffer.toString();
        }
    }

    public static String formatToYMD(int year, int month, int day) {
        String ymd = year + "-";
        if (month < 10) {
            ymd = ymd + "0" + month;
        } else {
            ymd = ymd + month;
        }
        if (day < 10) {
            ymd = ymd + "-0" + day;
        } else {
            ymd = ymd + "-" + day;
        }
        return ymd;
    }

    public static int stringToDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int timeInt = 0;
        Date dateStart = null;
        try {
            dateStart = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timeInt = (int) (dateStart.getTime() / 1000);
        return timeInt;
    }

    public static long getSelectEndDayTime(int year, int month, int day) {
        Calendar ca = Calendar.getInstance();
        ca.set(year, month, day, 23, 59, 59);
        Date time = ca.getTime();
        return time.getTime() / 1000;
    }

    public static String getTimeOfMonthStart() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DATE);
        String monthS = "";
        String dayS = "";
        if (month < 10) {
            monthS = "0" + month;
        } else {
            monthS = String.valueOf(month);
        }
        if (day < 10) {
            dayS = "0" + day;
        } else {
            dayS = String.valueOf(day);
        }
        return year + "-" + monthS + "-" + dayS;
    }

    public static String getTodayTimeYMD() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static String getTodayTimeYM() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static int getTodayTimeZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return (int) (zero.getTime() / 1000);
    }

    public static String getLastNMonthString(int lastNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) - lastNum,
                1, 0, 0, 0);
        LogUtils.e(calendar.getTimeInMillis() + "");
        LogUtils.e(getTimeFormatted((calendar.getTimeInMillis() / 1000), "yyyy年MM月"));
        return getTimeFormatted((calendar.getTimeInMillis() / 1000), "yyyy年MM月");
    }

    /*
     * 毫秒转化时分秒
     */
    public static String formatTimeHMS(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;
        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;//毫秒
        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            if (hour < 10) {
                sb.append("0");
            }
            sb.append(hour + "时");
        }
        if (minute > 0) {
            if (minute < 10) {
                sb.append("0");
            }
            sb.append(minute + "分");
        }
        if (second > 0) {
            if (second < 10) {
                sb.append("0");
            }
            sb.append(second + "秒");
        }
        return sb.toString();
    }

    //使用时长 超过小时去掉秒,超过天去掉分和秒,天数>99显示 99+天
    public static String longToUseTime(long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;
        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;//毫秒
        StringBuffer sb = new StringBuffer();
        if (day > 99) {
            sb.append("99+天");
            return sb.toString();
        } else if (day > 0) {
            sb.append(day + "天");
        }

        if (hour > 0) {
            if (hour < 10) {
                sb.append("0");
            }
            sb.append(hour + "时");
        }

        if (minute > 0 && day == 0) {
            if (minute < 10) {
                sb.append("0");
            }
            sb.append(minute + "分");
        }
        if (second > 0 && day == 0 && hour == 0) {
            if (second < 10) {
                sb.append("0");
            }
            sb.append(second + "秒");
        }
        if (TextUtils.isEmpty(sb.toString())) {
            sb.append("0秒");
        }
        return sb.toString();
    }

    /**
     * 将返回的印度时间转换成本地时间
     * @param time
     * @return
     */
    public static String getRelativeLocalDate(SimpleDateFormat format,long time){
        //时差
        int loc2 = TimeZone.getTimeZone("GMT+05:30").getRawOffset();
        Calendar nowCal = Calendar.getInstance();
        int loc1 = nowCal.getTimeZone().getRawOffset();
        long diffInMillisec = loc1-loc2;
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
        return format.format(new Date((time+diffInSec) * 1000));
    }
}

