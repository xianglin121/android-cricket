package com.onecric.live.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

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
        return ((hour != 0 ? ((hour + "h ")): "")) + (minutes < 10 ? ("0" + minutes) : minutes) + "m " + (sencond < 10 ? ("0" + sencond) : sencond) + "s";
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

}
