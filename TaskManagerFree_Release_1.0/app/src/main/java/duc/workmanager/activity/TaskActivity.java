package duc.workmanager.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import duc.workmanager.notification.NotificationReceiver;
import duc.workmanager.R;
import duc.workmanager.adapter.MyFragmentPagerAdapter;
import duc.workmanager.database.Doing;
import duc.workmanager.database.Done;
import duc.workmanager.database.Todo;
import duc.workmanager.util.Constant;
import duc.workmanager.fragment.DoingFragment;
import duc.workmanager.fragment.DoneFragment;
import duc.workmanager.fragment.TodoFragment;
import duc.workmanager.util.Utility;

public class TaskActivity extends BaseActivity implements TodoFragment.PushDataToActivity, DoingFragment.PushDataToActivity, DoneFragment.PushDataToActivity, TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    private int position;
    private ArrayList list;
    private View tabView;
    private TabHost tabHost;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myViewPagerAdapter;
    private Button btnTabTodo, btnTabDoing, btnTabDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        // init tabhost
        initializeTabHost();
        // init ViewPager
        initializeViewPager();
        btnAddTask = (Button) findViewById(R.id.btn_AddTask);
        txtSlogan = (TextView) findViewById(R.id.txt_slogan);
        btnAddTask.bringToFront();
        Utility.getWeek("4/6/2016");
        btnAddTask.setOnTouchListener(onTouchListener);
        txtSlogan.setOnClickListener(onClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Todo todo = new Todo();
        Doing doing = new Doing();
        Done done = new Done();
        String comper = "0";
        switch (Constant.positionTask) {
            case 0:
                if (resultCode == Constant.RESULT_CODE) {
                    if (requestCode == Constant.REQUEST_CODE_EDIT) {
                        todo = (Todo) list.get(position);
                        comper = todo.getComper();
                    }

                    todo.setPriority(data.getStringExtra(Constant.PRIORITY)).setComper(comper).
                            setStart(data.getStringExtra(Constant.START)).setEnd(data.getStringExtra(Constant.END)).setTopic(data.getStringExtra(Constant.TOPIC)).
                            setName(data.getStringExtra(Constant.NAME)).setDetail(data.getStringExtra(Constant.DETAIL)).save();
                }
                break;
            case 1:
                if (resultCode == Constant.RESULT_CODE) {
                    if (requestCode == Constant.REQUEST_CODE_EDIT) {
                        doing = (Doing) list.get(position);
                        comper = doing.getComper();
                    }
                    doing.setPriority(data.getStringExtra(Constant.PRIORITY)).setComper(comper).
                            setStart(data.getStringExtra(Constant.START)).setEnd(data.getStringExtra(Constant.END)).setTopic(data.getStringExtra(Constant.TOPIC)).
                            setName(data.getStringExtra(Constant.NAME)).setDetail(data.getStringExtra(Constant.DETAIL)).save();
                }
                break;
            case 2:
                if (resultCode == Constant.RESULT_CODE) {
                    if (requestCode == Constant.REQUEST_CODE_EDIT) {
                        done = (Done) list.get(position);
                        comper = done.getComper();
                    }
                    done.setPriority(data.getStringExtra(Constant.PRIORITY)).setComper(comper).
                            setStart(data.getStringExtra(Constant.START)).setEnd(data.getStringExtra(Constant.END)).setTopic(data.getStringExtra(Constant.TOPIC)).
                            setName(data.getStringExtra(Constant.NAME)).setDetail(data.getStringExtra(Constant.DETAIL)).save();
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
    }

    private void initializeTabHost() {
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
        btnTabTodo = (Button) findViewById(R.id.btn_tab_Todo);
        btnTabDoing = (Button) findViewById(R.id.btn_tab_Doing);
        btnTabDone = (Button) findViewById(R.id.btn_tab_Done);
        btnTabTodo.setBackgroundResource(R.mipmap.tab_backgound_press);
    }

    @Override
    public void onTabChanged(String tabId) {


        final int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos);
//        tabView = tabHost.getCurrentTabView();
//        HorizontalScrollView hScrollView = (HorizontalScrollView) findViewById(R.id.hScrollView);
//        int scrollPos = tabView.getLeft() - (hScrollView.getWidth() - tabView.getWidth()) / 2;
//        hScrollView.smoothScrollTo(scrollPos, 0);
        if (pos == 0) {
            btnTabTodo.setBackgroundResource(R.mipmap.tab_backgound_press);
            btnTabDoing.setBackgroundResource(R.mipmap.tab_backgound);
            btnTabDone.setBackgroundResource(R.mipmap.tab_backgound);
        } else if (pos == 1) {
            btnTabTodo.setBackgroundResource(R.mipmap.tab_backgound);
            btnTabDoing.setBackgroundResource(R.mipmap.tab_backgound_press);
            btnTabDone.setBackgroundResource(R.mipmap.tab_backgound);
        } else {
            btnTabTodo.setBackgroundResource(R.mipmap.tab_backgound);
            btnTabDoing.setBackgroundResource(R.mipmap.tab_backgound);
            btnTabDone.setBackgroundResource(R.mipmap.tab_backgound_press);
        }

        btnTabTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos != 0) {
                    viewPager.setCurrentItem(0);
                }
            }
        });
        btnTabDoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos != 1) {
                    viewPager.setCurrentItem(1);
                }
            }
        });
        btnTabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos != 2) {
                    viewPager.setCurrentItem(2);
                }
            }
        });
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);
        Constant.positionTask = position;
    }

    @Override
    public void onResume() {
        super.onResume();
        //for slogan
        SharedPreferences pre = getSharedPreferences("slogan", MODE_PRIVATE);
        txtSlogan.setText(pre.getString("slogan", getResources().getString(R.string.app_name)));

        //for notification
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(TaskActivity.this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(TaskActivity.this, 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();

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
            if (year <= yearStart && month <= monthStart && day < dayStart) {
                calendar.set(Calendar.DAY_OF_MONTH, dayStart);
                calendar.set(Calendar.HOUR_OF_DAY, 7);
//                calendar.set(Calendar.MINUTE, 52);
//                calendar.set(Calendar.SECOND, 40);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }

        List<Doing> lsDoing = new Select().from(Doing.class).execute();

        for (int i = 0; i < lsDoing.size(); i++) {
            int dayEnd, monthEnd, yearEnd;
            String[] timeEnd = lsDoing.get(i).getEnd().split("/");
            dayEnd = Integer.parseInt(timeEnd[0]);
            monthEnd = Integer.parseInt(timeEnd[1]);
            yearEnd = Integer.parseInt(timeEnd[2]);

            if (year <= yearEnd && month <= monthEnd && day < dayEnd) {
                calendar.set(Calendar.DAY_OF_MONTH, dayEnd);
                calendar.set(Calendar.HOUR_OF_DAY, 7);
//        calendar.set(Calendar.MINUTE, 52);
//        calendar.set(Calendar.SECOND, 40);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }
}
