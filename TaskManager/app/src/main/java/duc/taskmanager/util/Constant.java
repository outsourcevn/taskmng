package duc.taskmanager.util;

import android.content.Context;
import android.view.View;

import duc.taskmanager.R;

/**
 * Created by Duc on 5/12/2016.
 */
public class Constant {
    public static final String RADIO_DATASET_CHANGED = "listener event send data for all tap";
    public static final String DETAIL_PROCESS_KEY = "!#~";

    public static String[] itemsPriority = {"10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};
    public static String[] itemsTopic;

    public static final String TODO = "todo";
    public static final String DOING = "doing";
    public static final String DONE = "done";

    public static final String PRIORITY = "priority";
    public static final String COMPLETEPERCENT = "comper";
    public static final String START = "start";
    public static final String END = "end";
    public static final String TOPIC = "topic";
    public static final String NAME = "name";
    public static final String DETAIL = "detail";
    public static final String MEDIA = "media";

    public static final int REQUEST_CODE_ADD = 1;
    public static final int REQUEST_CODE_EDIT = 2;
    public static final int RESULT_CODE = 3;
    public static int widthScreen;
    public static int heightScreen;

    public static int positionTask = 0;

    public static final int flagsKitkat = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
}
