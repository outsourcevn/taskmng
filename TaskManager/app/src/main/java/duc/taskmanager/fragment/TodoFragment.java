package duc.taskmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import duc.taskmanager.R;
import duc.taskmanager.adapter.TaskAdapter;
import duc.taskmanager.database.Doing;
import duc.taskmanager.database.Todo;
import duc.taskmanager.activity.UpdateTaskActivity;
import duc.taskmanager.util.Constant;

public class TodoFragment extends Fragment implements TaskAdapter.TaskAdapterListener {
    private TaskAdapter adapter;
    private ArrayList<Todo> listTodo;
    private int positionEdit;
    public RecyclerView recyclerView;
    PushDataToActivity pushDataToActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TaskAdapter(getActivity(), listTodo = new ArrayList<>(), Constant.TODO);
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
        Todo.delete(Todo.class, listTodo.get(position).getId());
        reloadData();
        Toast.makeText(getActivity(), R.string.delete_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void save(int position, String data) {
        new Update(Todo.class).set("COMPER = " + data).where("ID = ?", listTodo.get(position).getId()).execute();
        reloadData();
    }

    @Override
    public void edit(int position) {
        positionEdit = position;

        Intent intent = new Intent(getActivity(), UpdateTaskActivity.class);
        intent.putExtra(Constant.PRIORITY, listTodo.get(position).getPriority());
        intent.putExtra(Constant.START, listTodo.get(position).getStart());
        intent.putExtra(Constant.END, listTodo.get(position).getEnd());
        intent.putExtra(Constant.TOPIC, listTodo.get(position).getTopic());
        intent.putExtra(Constant.NAME, listTodo.get(position).getName());
        intent.putExtra(Constant.DETAIL, listTodo.get(position).getDetail());
        getActivity().startActivityForResult(intent, Constant.REQUEST_CODE_EDIT);
        pushDataToActivity.push(positionEdit, listTodo);
    }

    @Override
    public void status(int position) {
        Doing doing = new Doing();

        doing.setPriority(listTodo.get(position).getPriority()).setComper(listTodo.get(position).getComper()).setStart(listTodo.get(position).getStart()).
                setEnd(listTodo.get(position).getEnd()).setTopic(listTodo.get(position).getTopic()).setName(listTodo.get(position).getName()).
                setDetail(listTodo.get(position).getDetail()).save();
        Intent intent = new Intent(Constant.RADIO_DATASET_CHANGED);
        getActivity().getApplicationContext().sendBroadcast(intent);
        del(position);
    }

    public void reloadData() {
        List<Todo> ls = new Select().from(Todo.class).orderBy("PRIORITY ASC").execute();
        listTodo.clear();
        listTodo.addAll(ls);
        Collections.reverse(listTodo);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    public interface PushDataToActivity {
        void push(int position, ArrayList<Todo> listTodo);
    }
}

