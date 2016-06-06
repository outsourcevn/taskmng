package duc.workmanager.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.activeandroid.query.Select;

import java.util.List;

import duc.workmanager.R;
import duc.workmanager.activity.TaskActivity;
import duc.workmanager.database.Doing;
import duc.workmanager.database.Todo;
import duc.workmanager.util.Utility;

public class NotificationService extends IntentService {
    private NotificationManager alarmNotificationManager;

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        String notice = "";
        int day, month, year;
        String[] currentDate = Utility.getCurrentDate().split("/");
        day = Integer.parseInt(currentDate[0]);
        month = Integer.parseInt(currentDate[1]);
        year = Integer.parseInt(currentDate[2]);

        List<Todo> lsTodo = new Select().from(Todo.class).execute();

        for (int i = 0; i < lsTodo.size(); i++) {
            int dayStart, monthStart, yearStart;
            String[] timeStart = lsTodo.get(i).getStart().split("/");
            dayStart = Integer.parseInt(timeStart[0]);
            monthStart = Integer.parseInt(timeStart[1]);
            yearStart = Integer.parseInt(timeStart[2]);
            if (year == yearStart && month == monthStart && day == dayStart) {
                notice += lsTodo.get(i).getName() + ":\n" + getString(R.string.notification_switch_doing)+ "\n";
            }
        }

        List<Doing> lsDoing = new Select().from(Doing.class).execute();

        for (int i = 0; i < lsDoing.size(); i++) {
            int dayEnd, monthEnd, yearEnd;
            String[] timeEnd = lsDoing.get(i).getEnd().split("/");
            dayEnd = Integer.parseInt(timeEnd[0]);
            monthEnd = Integer.parseInt(timeEnd[1]);
            yearEnd = Integer.parseInt(timeEnd[2]);

            if (year == yearEnd && month == monthEnd && day == dayEnd) {
                notice += lsDoing.get(i).getName() + ":\n" + getString(R.string.notification_switch_done)+ "\n";
            }
        }
        sendNotification(notice);
    }

    private void sendNotification(String msg) {
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, TaskActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle(getResources().getString(R.string.app_name)).setSmallIcon(R.mipmap.icon_workmanager)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        alamNotificationBuilder.setPriority(2);
        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }
}