package duc.workmanager.util;

import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import duc.workmanager.R;

/**
 * Created by Duc on 5/18/2016.
 */
public class Utility {
    public static String fullDateFormat(String date){
        String arrDate[] = date.split("/");
        if(arrDate[0].length()<2) arrDate[0] = "0"+arrDate[0];
        if(arrDate[1].length()<2) arrDate[1] = "0"+arrDate[1];
        return arrDate[0]+"/"+arrDate[1]+"/"+arrDate[2];
    }

    public static String reverseDate(String date){
        String arrDate[] = date.split("/");
        return arrDate[2]+"/"+arrDate[1]+"/"+arrDate[0];
    }

    public static boolean isValidFormatDate(String value) {
        boolean checkDate = false;
        String datePattern = "\\d{1,2}/\\d{1,2}/\\d{4}";
        if (value.matches(datePattern)) checkDate = true;
        return checkDate;
    }

    public static int getWeek(String date) {
        Calendar calendar = Calendar.getInstance();
        String arrDate[] = date.split("/");
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrDate[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(arrDate[1])-1);
        calendar.set(Calendar.YEAR, Integer.parseInt(arrDate[2]));

        //Receive the day of the year for what you previously set
        int givenDateDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

////Receive current day of year
//        Calendar calendarCurrentDate = Calendar.getInstance();
//        int currentDateDayOfYear = calendarCurrentDate.get(Calendar.DAY_OF_YEAR);
//
////Get difference in number of years
//        int currentYear = calendarCurrentDate.get(Calendar.YEAR);
//        int givenYear = calendar.get(Calendar.YEAR);
//        int yearDifference = currentYear - givenYear;
//
////Find difference, divide by 7 (Round value down to get the difference in whole weeks)
//        double differenceDays = currentDateDayOfYear - givenDateDayOfYear + (365*yearDifference);
//        double differenceWeeks = Math.floor(differenceDays / 7);

        double differenceWeeks = Math.floor(givenDateDayOfYear / 7);
        return (int) differenceWeeks;
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

        for (i = year1; i < year2; i++) soNgayN1N2 += 365 + SoNgayTrongThang(2, i) - 28;
        int khoangCach = soNgay2 - soNgay1 + soNgayN1N2;
        return khoangCach;
    }

    public static void setBackgoundPriority(View view, String getPriority) {

        if (Constant.itemsPriority[0].equals(getPriority)) {
            view.setBackgroundResource(R.mipmap.imv_priority_4);
        } else if (Constant.itemsPriority[1].equals(getPriority)) {
            view.setBackgroundResource(R.mipmap.imv_priority_3);
        } else if (Constant.itemsPriority[2].equals(getPriority)) {
            view.setBackgroundResource(R.mipmap.imv_priority_2);
        } else {
            view.setBackgroundResource(R.mipmap.imv_priority_1);
        }
    }

    public static void setBackgoundTopic(View view, String getTopic) {

        if (Constant.itemsTopic[0].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_assign);
        } else if (Constant.itemsTopic[1].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_head_work);
        } else if (Constant.itemsTopic[2].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_labour_work);
        } else if (Constant.itemsTopic[3].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_study);
        } else if (Constant.itemsTopic[4].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_computer);
        } else if (Constant.itemsTopic[5].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_book);
        } else if (Constant.itemsTopic[6].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_cooperation);
        } else if (Constant.itemsTopic[7].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_game);
        } else if (Constant.itemsTopic[8].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_shoping);
        } else if (Constant.itemsTopic[9].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_junket);
        } else if (Constant.itemsTopic[10].equals(getTopic)) {
            view.setBackgroundResource(R.mipmap.imv_topic_sport);
        } else {
            view.setBackgroundResource(R.mipmap.imv_topic_global);
        }
    }
}
