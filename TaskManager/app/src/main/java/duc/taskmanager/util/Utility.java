package duc.taskmanager.util;

import android.content.Context;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import duc.taskmanager.R;

/**
 * Created by Duc on 5/18/2016.
 */
public class Utility {

    public static boolean isValidFormatDate(String value) {
        boolean checkDate = false;
        String datePattern = "\\d{1,2}/\\d{1,2}/\\d{4}";
        if (value.matches(datePattern)) checkDate = true;
        return checkDate;
    }

    public static String getCurrentDate() {
        int mDay, mMonth, mYear;
        Calendar c = Calendar.getInstance();
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth = c.get(Calendar.MONTH) + 1;
        mYear = c.get(Calendar.YEAR);

        return String.valueOf(mDay) + "/" + String.valueOf(mMonth) + "/" + String.valueOf(mYear);
    }


    public static int SoNgayTrongThang(int thang, int nam) {
        switch (thang) {
            case 2:
                if (((nam % 4) == 0 && (nam % 100) != 0) || nam % 400 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            default:
                return 0;
        }
    }

    public static int getCountOfDay(String Created_date_String, String Expire_date_String) {
        int day1, month1, year1;
        int day2, month2, year2;
        int soNgay1, soNgay2;
        int i;
        String[] time1 = Created_date_String.split("/");
        day1 = Integer.parseInt(time1[0]);
        month1 = Integer.parseInt(time1[1]);
        year1 = Integer.parseInt(time1[2]);

        String[] time2 = Expire_date_String.split("/");
        day2 = Integer.parseInt(time2[0]);
        month2 = Integer.parseInt(time2[1]);
        year2 = Integer.parseInt(time2[2]);

        soNgay1 = day1; /* bắt đầu từ ngày và cộng số ngày của các tháng trước đó */
        for (i = 1; i < month1; i++) soNgay1 += SoNgayTrongThang(i, year1);
        soNgay2 = day2; /* bắt đầu từ ngày và cộng số ngày của các tháng trước đó */
        for (i = 1; i < month2; i++) soNgay2 += SoNgayTrongThang(i, year2);
        int soNgayN1N2 = 0;


        // This case for getWeekOfYear when Created_date_String> Expire_date_String
        if (year1 > year2) {
            for (i = year1; i < year2; i++) soNgayN1N2 += -365 + SoNgayTrongThang(2, i) - 28;
        } else {
            for (i = year1; i < year2; i++) soNgayN1N2 += 365 + SoNgayTrongThang(2, i) - 28;
        }

        int khoangCach = soNgay2 - soNgay1 + soNgayN1N2;
        return khoangCach;
    }

    // Nhập vào ngày, tính ngày đó là tuần thứ mấy trong năm
    public static int getWeekofYear() {
        String date = "12/12/2015";
        int mWeekCurrent, mDayCurrent;
        int mWeekInput, mDayInput;
        int result;
        Calendar c = null;
        c.set(2015, 12, 12);
        // Lấy ra tuần của ngày hiện tại
        mWeekCurrent = c.get(Calendar.WEEK_OF_YEAR);
        // Lấy ra ngày đó là thứ mấy của tuần
        mDayCurrent = c.get(Calendar.DAY_OF_WEEK);

        // Lấy ra tuần của ngày nhập vào
        mWeekInput = (getCountOfDay(getCurrentDate(), date) / 7);
        // Lấy ra ngày của ngày nhập vào
        mDayInput = Math.abs(getCountOfDay(getCurrentDate(), date) % 7);

        result = Math.abs(mWeekCurrent + mWeekInput);
        if (result < 0 && result > 52) {
            int cache = result % 52;
            result = 52 - Math.abs(cache);
        }
        if (mDayCurrent + mDayInput > 7) result++;

        return result;
    }

    public static void getDayOfWeek() {
        String userInput = "15/24/1/1/2015";
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("hh/mm/dd/MM/yyyy");
        try {
            date = format.parse(userInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calender = new GregorianCalendar();
        calender.setTime(date);
        int t = calender.get(Calendar.WEEK_OF_YEAR);
        int t1 = calender.get(Calendar.MINUTE);
        int t2 = calender.get(Calendar.HOUR_OF_DAY);
    }

    public static void setBackgoundPriority(View view, int getPriority) {
        switch (getPriority) {
            case 99:
                view.setBackgroundResource(R.mipmap.imv_priority_10);
                break;
            case 9:
                view.setBackgroundResource(R.mipmap.imv_priority_9);
                break;
            case 8:
                view.setBackgroundResource(R.mipmap.imv_priority_8);
                break;
            case 7:
                view.setBackgroundResource(R.mipmap.imv_priority_7);
                break;
            case 6:
                view.setBackgroundResource(R.mipmap.imv_priority_6);
                break;
            case 5:
                view.setBackgroundResource(R.mipmap.imv_priority_5);
                break;
            case 4:
                view.setBackgroundResource(R.mipmap.imv_priority_4);
                break;
            case 3:
                view.setBackgroundResource(R.mipmap.imv_priority_3);
                break;
            case 2:
                view.setBackgroundResource(R.mipmap.imv_priority_2);
                break;
            case 1:
                view.setBackgroundResource(R.mipmap.imv_priority_1);
                break;
        }
    }

    public static void setBackgoundTopic(View view, String getTopic) {

        if (Constant.itemsTopic[0].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_head_work);
        } else if (Constant.itemsTopic[1].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_labour_work);
        } else if (Constant.itemsTopic[2].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_study);
        } else if (Constant.itemsTopic[3].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_computer);
        } else if (Constant.itemsTopic[4].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_book);
        } else if (Constant.itemsTopic[5].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_cooperation);
        } else if (Constant.itemsTopic[6].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_game);
        } else if (Constant.itemsTopic[7].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_shoping);
        } else if (Constant.itemsTopic[8].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_junket);
        } else if (Constant.itemsTopic[9].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_sport);
        } else {
            view.setBackgroundResource(R.mipmap.imv_topic_global);
        }
    }
}
