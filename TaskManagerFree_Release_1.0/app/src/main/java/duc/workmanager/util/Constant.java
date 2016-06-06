package duc.workmanager.util;

import android.view.View;

/**
 * Created by Duc on 5/12/2016.
 */
public class Constant {
    public static final String RADIO_DATASET_CHANGED = "listener event send data for all tap";
    public static final String DETAIL_PROCESS_KEY = "!#~";

    public static String[] itemsPriority;
    public static String[] itemsTopic;

    public static final String TODO = "todo";
    public static final String DOING = "doing";
    public static final String DONE = "done";

    public static final String PRIORITY = "PRIORITY";
    public static final String COMPLETEPERCENT = "COMPLETEPERCENT";
    public static final String START = "START";
    public static final String END = "END";
    public static final String TOPIC = "TOPIC";
    public static final String NAME = "NAME";
    public static final String DETAIL = "DETAIL";
    public static final String MEDIA = "MEDIA";

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
