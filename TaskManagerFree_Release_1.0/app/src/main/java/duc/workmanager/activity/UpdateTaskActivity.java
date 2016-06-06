package duc.workmanager.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import duc.workmanager.R;
import duc.workmanager.util.Constant;
import duc.workmanager.util.Utility;

public class UpdateTaskActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Context context;

    private Button btnPriority, btnStartTime, btnEndTime, btnStartDate, btnEndDate, btnTopic, btnOk, btnCancel;
    private EditText edtName, edtDetail;
    private boolean startTimeClick, startDateClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_update_task);
        connectViewUpdateTask();
    }

    public void connectViewUpdateTask() {
        btnPriority = (Button) findViewById(R.id.btn_Priority);
        btnTopic = (Button) findViewById(R.id.btn_Topic);
        btnStartTime = (Button) findViewById(R.id.btn_Start_Time);
        btnEndTime = (Button) findViewById(R.id.btn_End_Time);
        btnStartDate = (Button) findViewById(R.id.btn_Start_Date);
        btnEndDate = (Button) findViewById(R.id.btn_End_Date);
        edtName = (EditText) findViewById(R.id.edt_Name);
        edtDetail = (EditText) findViewById(R.id.edt_Detail);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            btnPriority.setText(bundle.getString(Constant.PRIORITY));
            btnTopic.setText(bundle.getString(Constant.TOPIC));
            btnStartDate.setText(bundle.getString(Constant.START));
            btnEndDate.setText(bundle.getString(Constant.END));
            edtName.setText(bundle.getString(Constant.NAME));
            edtDetail.setText(bundle.getString(Constant.DETAIL));
        }
        btnPriority.setOnClickListener(onClickListener);
        btnTopic.setOnClickListener(onClickListener);
        btnStartTime.setOnClickListener(onClickListener);
        btnEndTime.setOnClickListener(onClickListener);
        btnStartDate.setOnClickListener(onClickListener);
        btnEndDate.setOnClickListener(onClickListener);
        btnOk.setOnClickListener(onClickListener);
        btnCancel.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
            if (v == btnPriority) {
                builder.setTitle(R.string.dialog_tittle_priority);
                builder.setItems(Constant.itemsPriority, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), Constant.itemsPriority[item], Toast.LENGTH_SHORT).show();//Hiển thị item được lựa chọn
                        btnPriority.setText(Constant.itemsPriority[item].toString());
                    }
                });
                builder.show();
            }
            if (v == btnTopic) {
                builder.setTitle(R.string.dialog_tittle_Topic);
                builder.setItems(Constant.itemsTopic, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), Constant.itemsTopic[item], Toast.LENGTH_SHORT).show();//Hiển thị item được lựa chọn
                        if (Constant.itemsTopic[item].equals(Constant.itemsTopic[11])) {
                            inputOtherTopic();
                        } else {
                            btnTopic.setText(Constant.itemsTopic[item].toString());
                        }
                    }
                });
                builder.show();
            }

            if (v == btnStartTime) {
                startDateClick = true;
                showTimePickerDialog(v);
            }
            if (v == btnEndTime) {
                startDateClick = false;
                showTimePickerDialog(v);
            }
            if (v == btnStartDate) {
                startDateClick = true;
                showDatePickerDialog(v);
            }
            if (v == btnEndDate) {
                startDateClick = false;
                showDatePickerDialog(v);
            }
            if (v == btnOk) {
                update();
            }
            if (v == btnCancel) {
                finish();
            }
        }
    };

    public void inputOtherTopic() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
        builder.setTitle(R.string.dialog_tittle_Topic);
        final EditText input = new EditText(UpdateTaskActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btnTopic.setText(input.getText().toString());
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

    private void update() {
        String priority = btnPriority.getText().toString().trim();
        String topic = btnTopic.getText().toString().trim();
        String startTime = btnStartDate.getText().toString().trim();
        String endTime = btnEndDate.getText().toString().trim();
        String startDate = btnStartDate.getText().toString().trim();
        String endDate = btnEndDate.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String detail = edtDetail.getText().toString().trim();

        if (startDate.equals("")) {
            Toast.makeText(context, R.string.please_enter_start_date, Toast.LENGTH_SHORT).show();
        } else if (endDate.equals("")) {
            Toast.makeText(context, R.string.please_enter_end_date, Toast.LENGTH_SHORT).show();
        } else if (name.equals("")) {
            edtName.requestFocus();
            Toast.makeText(context, R.string.please_enter_name, Toast.LENGTH_SHORT).show();
        } else {
            int t = Utility.getCountOfDay(startDate, endDate);
            if (t < 0) {
                Toast.makeText(context, R.string.input_end_date_bigger_start_date, Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                intent.putExtra(Constant.PRIORITY, priority);
                intent.putExtra(Constant.START, startDate);
                intent.putExtra(Constant.END, endDate);
                intent.putExtra(Constant.TOPIC, topic);
                intent.putExtra(Constant.NAME, name);
                intent.putExtra(Constant.DETAIL, detail);
                setResult(Constant.RESULT_CODE, intent);
                finish();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Log.w("cho nay", "Date = " + year);
        if (startDateClick) {
            btnStartDate.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));
        } else {
            btnEndDate.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int minute, int hourOfDay) {
        if (startDateClick) {
            btnStartTime.setText(String.valueOf(minute) + " : " + String.valueOf(hourOfDay));
        } else {
            btnEndTime.setText(String.valueOf(minute) + " : " + String.valueOf(hourOfDay));
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), (UpdateTaskActivity) getActivity(), hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int minute, int hourOfDay) {
            // Do something with the time chosen by the user
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), (UpdateTaskActivity) getActivity(), year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Log.w("DatePicker", "Date = " + year);
        }
    }
}
