package duc.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import duc.taskmanager.R;
import duc.taskmanager.adapter.MyFragmentPagerAdapter;
import duc.taskmanager.database.Doing;
import duc.taskmanager.database.Done;
import duc.taskmanager.database.Todo;
import duc.taskmanager.util.Constract;
import duc.taskmanager.fragment.DoingFragment;
import duc.taskmanager.fragment.DoneFragment;
import duc.taskmanager.fragment.TodoFragment;
import duc.taskmanager.util.Updateable;

public class TaskActivity extends AppCompatActivity implements TodoFragment.PushDataToActivity, DoingFragment.PushDataToActivity, DoneFragment.PushDataToActivity, TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener{
    private Button btnAddTask;
    private int position;
    private ArrayList list;
    private View tabView;
    private TabHost tabHost;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myViewPagerAdapter;
    private float mPrevX;
    private float mPrevY;
    private boolean bl = false;
    Updateable updateable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        // init tabhost
        this.initializeTabHost(savedInstanceState);
        // init ViewPager
        this.initializeViewPager();

        btnAddTask = (Button) findViewById(R.id.btn_AddTask);
        btnAddTask.bringToFront();
        btnAddTask.setOnTouchListener(onTouchListener);
        btnAddTask.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityForResult(new Intent(TaskActivity.this, UpdateTaskActivity.class), Constract.REQUEST_CODE_ADD);
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
                    bl = false;
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    currX = event.getRawX();
                    currY = event.getRawY();
                    ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(btnAddTask.getLayoutParams());
                    marginParams.setMargins((int) (currX - mPrevX), (int) (currY - mPrevY), 0, 0);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
                    btnAddTask.setLayoutParams(layoutParams);
                    bl = true;
                    Log.e("ACTION_MOVE", Boolean.toString(bl));
                    break;
                }
            }
            return bl;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Todo todo = new Todo();
        Doing doing = new Doing();
        Done done = new Done();
        switch (Constract.positionTask) {
            case 0:
                if (resultCode == Constract.RESULT_CODE) {
                    if (requestCode == Constract.REQUEST_CODE_EDIT) {
                        todo = (Todo) list.get(position);
                    }
                    todo.setComper(data.getStringExtra(Constract.COMPLETEPERCENT)).setStart(data.getStringExtra(Constract.START)).setEnd(data.getStringExtra(Constract.END)).save();
                }
                break;
            case 1:
                if (resultCode == Constract.RESULT_CODE) {
                    if (requestCode == Constract.REQUEST_CODE_EDIT) {
                        doing = (Doing) list.get(position);
                    }
                    doing.setComper(data.getStringExtra(Constract.COMPLETEPERCENT)).setStart(data.getStringExtra(Constract.START)).setEnd(data.getStringExtra(Constract.END)).save();
                }
                break;
            case 2:
                if (resultCode == Constract.RESULT_CODE) {
                    if (requestCode == Constract.REQUEST_CODE_EDIT) {
                        done = (Done) list.get(position);
                    }
                    done.setComper(data.getStringExtra(Constract.COMPLETEPERCENT)).setStart(data.getStringExtra(Constract.START)).setEnd(data.getStringExtra(Constract.END)).save();
                }
                break;
        }
    }

    @Override
    public void push(int positionEdit, ArrayList listTask) {
        position = positionEdit;
        list = listTask;
    }

    // fake content for tabhost
    class FakeContent implements TabHost.TabContentFactory {
        private final Context mContext;

        public FakeContent(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }

    private void initializeViewPager() {
        List<Fragment> fragments = new Vector<>();
        fragments.add(new TodoFragment());
        fragments.add(new DoingFragment());
        fragments.add(new DoneFragment());

        this.myViewPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        this.viewPager = (ViewPager) super.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myViewPagerAdapter);
        this.viewPager.setOnPageChangeListener(this);
//        onRestart();
    }

    private void initializeTabHost(Bundle args) {
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        for (int i = 1; i <= 3; i++) {
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec("Tab " + i);
            tabSpec.setIndicator("Tab " + i);
            tabSpec.setContent(new FakeContent(this));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
        tabView = tabHost.getCurrentTabView();
        tabView.setBackgroundColor(Color.RED);
    }

    @Override
    public void onTabChanged(String tabId) {
        tabView.setBackgroundColor(Color.TRANSPARENT);
        tabView = tabHost.getCurrentTabView();
        int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos);
        HorizontalScrollView hScrollView = (HorizontalScrollView) findViewById(R.id.hScrollView);

        int scrollPos = tabView.getLeft() - (hScrollView.getWidth() - tabView.getWidth()) / 2;
        hScrollView.smoothScrollTo(scrollPos, 0);
        tabView.setBackgroundColor(Color.RED);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }
    public void addDownloadListener(Updateable capNhat) {
        this.updateable = capNhat;
    }
    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);
        Constract.positionTask = position;
    }
}