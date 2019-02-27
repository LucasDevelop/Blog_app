package com.goume.brush;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @package com.goume.brush
 * @anthor luan
 * @date 2019/2/25
 * @des
 */
public class JavaTest {

    @Test
    public void test1() {
        //判断两个日期是否是同一天
        System.out.println(isSameDay(new Date(System.currentTimeMillis()+99999999),new Date()));
    }

    public boolean isSameDay(Date date1, Date date2) {
        Calendar instance1 = Calendar.getInstance();
        instance1.setTime(date1);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        return instance1.get(Calendar.YEAR) == instance2.get(Calendar.YEAR) &&
                instance1.get(Calendar.MONTH) == instance2.get(Calendar.MONTH) &&
                instance1.get(Calendar.DAY_OF_MONTH) == instance2.get(Calendar.DAY_OF_MONTH);
    }
}
