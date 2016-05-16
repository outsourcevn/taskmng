package duc.taskmanager.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import duc.taskmanager.R;
import duc.taskmanager.adapter.TaskAdapter;
import duc.taskmanager.database.Doing;
import duc.taskmanager.activity.UpdateTaskActivity;
import duc.taskmanager.database.Done;
import duc.taskmanager.util.Constract;

public class DoingFragment extends Fragment implements TaskAdapter.TaskAdapterListener {
    private TaskAdapter adapter;
    private ArrayList<Doing> listDoing;
    private int positionEdit;
    public RecyclerView recyclerView;
    PushDataToActivity pushDataToActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doing, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TaskAdapter(getActivity(), listDoing = new ArrayList<>(), Constract.DOING);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            pushDataToActivity = (PushDataToActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void del(int position) {
        Doing.delete(Doing.class, listDoing.get(position).getId());
        reloadData();
        Toast.makeText(getActivity(), R.string.delete_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void edit(int position) {
        positionEdit = position;

        Intent intent = new Intent(getActivity(), UpdateTaskActivity.class);
        intent.putExtra(Constract.COMPLETEPERCENT, listDoing.get(position).getComper());
        intent.putExtra(Constract.START, listDoing.get(position).getStart());
        intent.putExtra(Constract.END, listDoing.get(position).getEnd());
        getActivity().startActivityForResult(intent, Constract.REQUEST_CODE_EDIT);
        pushDataToActivity.push(positionEdit, listDoing);
    }

    @Override
    public void status(int position) {
        Done done = new Done();
        done.setComper(listDoing.get(position).getComper()).setStart(listDoing.get(position).getStart()).setEnd(listDoing.get(position).getEnd()).save();
        Intent intent = new Intent(DoneFragment.RADIO_DATASET_CHANGED);
        getActivity().getApplicationContext().sendBroadcast(intent);
        del(position);
    }

    public void reloadData() {
        List<Doing> ls = new Select().from(Doing.class).orderBy("COMPER ASC").execute();
        listDoing.clear();
        listDoing.addAll(ls);
        Collections.reverse(listDoing);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
        IntentFilter filter = new IntentFilter();
        filter.addAction(RADIO_DATASET_CHANGED);
        radio = new Radio();
        getActivity().getApplicationContext().registerReceiver(radio, filter);
    }

    public interface PushDataToActivity {
        void push(int position, ArrayList<Doing> listDoing);
    }

    public static final String RADIO_DATASET_CHANGED = "com.yourapp.app.RADIO_DATASET_CHANGED";

    private Radio radio;

    private class Radio extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RADIO_DATASET_CHANGED)) {
                reloadData();
            }
        }
    }
}