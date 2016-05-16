package duc.taskmanager.adapter;

/**
 * Created by Duc on 5/12/2016.
 */

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import duc.taskmanager.R;
import duc.taskmanager.database.Doing;
import duc.taskmanager.database.Done;
import duc.taskmanager.database.Todo;
import duc.taskmanager.util.Constract;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.RecyclerViewHolder> {
    private ArrayList list;
    private Context context;
    private String taskType;

    public TaskAdapter(Context context, ArrayList list, String taskType) {
        this.context = context;
        this.list = list;
        this.taskType = taskType;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_task, viewGroup, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        if (taskType.equals(Constract.TODO)) {
            Todo todo = (Todo) list.get(position);
            viewHolder.txtTask.setText("To Do");
            viewHolder.txtComPer.setText(todo.getComper());
            viewHolder.txtStart.setText(todo.getStart());
            viewHolder.txtEnd.setText(todo.getEnd());
        } else if (taskType.equals(Constract.DOING)) {
            Doing doing = (Doing) list.get(position);
            viewHolder.txtTask.setText("Doing");
            viewHolder.txtComPer.setText(doing.getComper());
            viewHolder.txtStart.setText(doing.getStart());
            viewHolder.txtEnd.setText(doing.getEnd());
        } else {
            Done done = (Done) list.get(position);
            viewHolder.txtTask.setText("Done");
            viewHolder.txtComPer.setText(done.getComper());
            viewHolder.txtStart.setText(done.getStart());
            viewHolder.txtEnd.setText(done.getEnd());
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTask, txtComPer, txtStart, txtEnd;
        public ImageView imvPriority;
        public Button btnMenu;
        public SeekBar seekBarComPer;
        public ProgressBar progressBarTimeLine;

        // for show menu edit and delete
        private PopupMenu popupMenu;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            txtTask = (TextView) itemView.findViewById(R.id.txt_Task);
            txtComPer = (TextView) itemView.findViewById(R.id.txt_CompletePercent);
            txtStart = (TextView) itemView.findViewById(R.id.txt_Start);
            txtEnd = (TextView) itemView.findViewById(R.id.txt_End);

            imvPriority = (ImageView) itemView.findViewById(R.id.imv_Priority);
            btnMenu = (Button) itemView.findViewById(R.id.btn_Menu);

            seekBarComPer = (SeekBar) itemView.findViewById(R.id.seekBar_CompletePercent);
            seekBarComPer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    txtComPer.setText(String.valueOf(progress)+ " %");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMenu.show();
                }
            });
            setPopup(itemView);
        }

        /*
        * when show popup menu, we can click edit or delete menu item.
        * so we need create a interface to sent position for main activity process
        * */
        private void setPopup(View view) {
            popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.menu_item_task, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_edit:
                            if (listener != null) {
                                listener.edit(getAdapterPosition());
                            }
                            break;
                        case R.id.menu_status:
                            if (listener != null) {
                                listener.status(getAdapterPosition());
                            }
                            break;
                        case R.id.menu_delete:
                            if (listener != null) {
                                listener.del(getAdapterPosition());
                            }
                            break;
                    }
                    return true;
                }
            });
        }
    }

    public interface TaskAdapterListener {

        void edit(int position);

        void status(int position);

        void del(int position);
    }

    private TaskAdapterListener listener;

    public TaskAdapter setListener(TaskAdapterListener listener) {
        this.listener = listener;
        return this;
    }
}
