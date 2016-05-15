package duc.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import duc.taskmanager.R;
import duc.taskmanager.util.Constract;

public class UpdateTaskActivity extends AppCompatActivity {
    private Context context;

    private EditText edtComPer, edtStart, edtEnd;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_update_task);
        connectViewUpdateTask();
    }

    public void connectViewUpdateTask() {

        edtComPer = (EditText) findViewById(R.id.edt_ComPer);
        edtStart = (EditText) findViewById(R.id.edt_Start);
        edtEnd = (EditText) findViewById(R.id.edt_End);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            edtComPer.setText(bundle.getString(Constract.COMPLETEPERCENT));
            edtStart.setText(bundle.getString(Constract.START));
            edtEnd.setText(bundle.getString(Constract.END));
        }

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    private void update() {
        String comPer = edtComPer.getText().toString().trim();
        String start = edtStart.getText().toString().trim();
        String end = edtEnd.getText().toString().trim();

        if (TextUtils.isEmpty(comPer)) {
            edtComPer.requestFocus();
            Toast.makeText(context, R.string.please_enter_name, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(start)) {
            edtStart.requestFocus();
            Toast.makeText(context, R.string.please_enter_phone, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(end)) {
            edtEnd.requestFocus();
            Toast.makeText(context, R.string.please_enter_phone, Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        intent.putExtra(Constract.COMPLETEPERCENT, comPer);
        intent.putExtra(Constract.START, start);
        intent.putExtra(Constract.END, end);
        setResult(Constract.RESULT_CODE, intent);
        finish();
    }
    @Override
    public void finish() {
        super.finish();
    }

}
