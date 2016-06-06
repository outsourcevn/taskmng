package duc.workmanager.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import duc.workmanager.R;
import duc.workmanager.util.Constant;

public class BaseActivity extends AppCompatActivity {
    public Button btnAddTask;
    public TextView txtSlogan;
    private static float mPrevX, mPrevY, mPrevRawX, mPrevRawY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
//        systemUiVisibility(this);
        String[] itemsPriority = new String[4];
        itemsPriority[0] = getResources().getString(R.string.highest);
        itemsPriority[1] = getResources().getString(R.string.high);
        itemsPriority[2] = getResources().getString(R.string.medium);
        itemsPriority[3] = getResources().getString(R.string.low);
        Constant.itemsPriority = itemsPriority;

        String[] itemsTopic = new String[12];
        itemsTopic[0] = getResources().getString(R.string.assign);
        itemsTopic[1] = getResources().getString(R.string.head_work);
        itemsTopic[2] = getResources().getString(R.string.labour_work);
        itemsTopic[3] = getResources().getString(R.string.study);
        itemsTopic[4] = getResources().getString(R.string.computer);
        itemsTopic[5] = getResources().getString(R.string.book);
        itemsTopic[6] = getResources().getString(R.string.cooperation);
        itemsTopic[7] = getResources().getString(R.string.game);
        itemsTopic[8] = getResources().getString(R.string.shoping);
        itemsTopic[9] = getResources().getString(R.string.junket);
        itemsTopic[10] = getResources().getString(R.string.sport);
        itemsTopic[11] = getResources().getString(R.string.other);
        Constant.itemsTopic = itemsTopic;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
            builder.setTitle(R.string.dialog_tittle_slogan);
            final EditText input = new EditText(BaseActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            builder.setView(input);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    txtSlogan.setText(input.getText().toString());
                    SharedPreferences pre = getSharedPreferences("slogan", MODE_PRIVATE);
                    SharedPreferences.Editor edit=pre.edit();
                    edit.putString("slogan", input.getText().toString());
                    edit.commit();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    };

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float currX, currY;
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    mPrevX = event.getX();
                    mPrevY = event.getY();

                    mPrevRawX = event.getRawX();
                    mPrevRawY = event.getRawY();
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    currX = event.getRawX();
                    currY = event.getRawY();

                    ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(btnAddTask.getLayoutParams());
                    marginParams.setMargins((int) (currX - mPrevX), (int) (currY - mPrevY - getStatusBarHeight()), 0, 0);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    btnAddTask.setLayoutParams(layoutParams);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    float mEndRawX = event.getRawX();
                    float mEndRawY = event.getRawY();
                    final float scale = BaseActivity.this.getResources().getDisplayMetrics().density;
                    int pixels = (int) (5 * scale + 0.5f);
                    if (Math.abs(mEndRawX - mPrevRawX) < pixels && Math.abs(mEndRawY - mPrevRawY) < pixels) {
                        startActivityForResult(new Intent(BaseActivity.this, UpdateTaskActivity.class), Constant.REQUEST_CODE_ADD);
                    }
                    break;
                }
            }
            return true;
        }
    };

    //Kich thuoc man hinh
    public void sizeScreen() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        Constant.widthScreen = displaymetrics.widthPixels;
        Constant.heightScreen = displaymetrics.heightPixels + navigationBarHeight();
    }

    public int navigationBarHeight() {
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    // Hide status and navigation bar
    public void systemUiVisibility(Activity activity) {
//         This work only for android 4.4+
        final View decorView = activity.getWindow().getDecorView();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setSystemUiVisibility(Constant.flagsKitkat);

            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(Constant.flagsKitkat);
                    }
                }
            });
        }
    }
}