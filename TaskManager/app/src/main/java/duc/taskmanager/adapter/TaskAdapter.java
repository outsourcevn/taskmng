package duc.taskmanager.adapter;

/**
 * Created by Duc on 5/12/2016.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import duc.taskmanager.R;
import duc.taskmanager.database.Doing;
import duc.taskmanager.database.Done;
import duc.taskmanager.database.Todo;
import duc.taskmanager.util.Constant;
import duc.taskmanager.util.Utility;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.RecyclerViewHolder> {
    private ArrayList list;
    private Context context;
    private String taskType, comper;
    private boolean expandClick = false;

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
    public void onBindViewHolder(RecyclerViewHolder viewHolder, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        int getPriority, timeStartToCurrent, timeCurrentToEnd, timeStatToEnd, timeLine;

        if (taskType.equals(Constant.TODO)) {
            Todo todo = (Todo) list.get(position);
            getPriority = Integer.parseInt(todo.getPriority());
            Utility.setBackgoundPriority(viewHolder.imvPriority, getPriority);

            viewHolder.txtStart.setText(todo.getStart().substring(0, todo.getStart().length() - 5));
            viewHolder.txtEnd.setText(todo.getEnd().substring(0, todo.getEnd().length() - 5));
            viewHolder.txtTaskTopic.setText(todo.getTopic());
            viewHolder.txtTaskName.setText(todo.getName());
            viewHolder.txt_TaskDetail.setText(todo.getDetail());

            Utility.setBackgoundTopic(viewHolder.imvTaskTopic, todo.getTopic());

            timeStartToCurrent = Utility.getCountOfDay(todo.getStart(), Utility.getCurrentDate());
            timeCurrentToEnd = Utility.getCountOfDay(Utility.getCurrentDate(), todo.getEnd());
            timeStatToEnd = Utility.getCountOfDay(todo.getStart(), todo.getEnd());
            timeLine = 0;

            viewHolder.txtTask.setText(Integer.toString(timeCurrentToEnd) + "d");
            if (timeStartToCurrent / 2 < timeCurrentToEnd) {
                if (timeStartToCurrent <= timeCurrentToEnd) {
                    viewHolder.txtTask.setTextColor(context.getResources().getColor(R.color.green));
                } else
                    viewHolder.txtTask.setTextColor(context.getResources().getColor(R.color.orange));
            } else {
                viewHolder.txtTask.setTextColor(Color.RED);
            }

            if (timeCurrentToEnd <= 0) {
                viewHolder.progressBarTimeLine.setProgress(100);
            } else if (timeStartToCurrent <= 0) {
                viewHolder.progressBarTimeLine.setProgress(0);
                viewHolder.txtTask.setText(Integer.toString(Utility.getCountOfDay(todo.getStart(), todo.getEnd())) + "d");
            } else {
                timeLine = (100 * timeStartToCurrent / timeStatToEnd);
                viewHolder.progressBarTimeLine.setProgress(timeLine);
            }

            if (todo.getComper() != null) {
                viewHolder.seekBarComPer.setProgress(Integer.parseInt(todo.getComper()));
                viewHolder.txtComPer.setText(todo.getComper() + " %");
                if (timeLine != 0) {
                    float rateTimeComper = (float) Integer.parseInt(todo.getComper()) / timeLine;
                    if (rateTimeComper >= 1) {
                        viewHolder.seekBarComPer.setProgressDrawable(context.getResources().getDrawable(R.drawable.theme_seekbar_green));
                    } else if (rateTimeComper < 0.5f) {
                        viewHolder.seekBarComPer.setProgressDrawable(context.getResources().getDrawable(R.drawable.theme_seekbar_red));
                    } else {
                        viewHolder.seekBarComPer.setProgressDrawable(context.getResources().getDrawable(R.drawable.theme_seekbar_orange));
                    }
                }
            }

            if (timeStartToCurrent >= 0) {
                builder.setTitle(todo.getName());
                builder.setMessage(R.string.notice_switch_doing);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.status(position);
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

        } else if (taskType.equals(Constant.DOING)) {
            Doing doing = (Doing) list.get(position);
            viewHolder.txtTask.setText("Doing");
            getPriority = Integer.parseInt(doing.getPriority());
            Utility.setBackgoundPriority(viewHolder.imvPriority, getPriority);

            viewHolder.txtStart.setText(doing.getStart().substring(0, doing.getStart().length() - 5));
            viewHolder.txtEnd.setText(doing.getEnd().substring(0, doing.getEnd().length() - 5));
            viewHolder.txtTaskName.setText(doing.getName());
            viewHolder.txtTaskTopic.setText(doing.getTopic());
            viewHolder.txt_TaskDetail.setText(doing.getDetail());
            Utility.setBackgoundTopic(viewHolder.imvTaskTopic, doing.getTopic());

            timeStartToCurrent = Utility.getCountOfDay(doing.getStart(), Utility.getCurrentDate());
            timeCurrentToEnd = Utility.getCountOfDay(Utility.getCurrentDate(), doing.getEnd());
            timeStatToEnd = Utility.getCountOfDay(doing.getStart(), doing.getEnd());
            timeLine = 0;

            viewHolder.txtTask.setText(Integer.toString(timeCurrentToEnd) + "d");
            if (timeStartToCurrent / 2 < timeCurrentToEnd) {
                if (timeStartToCurrent <= timeCurrentToEnd) {
                    viewHolder.txtTask.setTextColor(context.getResources().getColor(R.color.green));
                } else
                    viewHolder.txtTask.setTextColor(context.getResources().getColor(R.color.orange));
            } else {
                viewHolder.txtTask.setTextColor(Color.RED);
            }


            if (timeCurrentToEnd <= 0) {
                viewHolder.progressBarTimeLine.setProgress(100);
            } else if (timeStartToCurrent <= 0) {
                viewHolder.progressBarTimeLine.setProgress(0);
                viewHolder.txtTask.setText(Integer.toString(Utility.getCountOfDay(doing.getStart(), doing.getEnd())) + "d");
            } else {
                timeLine = (100 * timeStartToCurrent / timeStatToEnd);
                viewHolder.progressBarTimeLine.setProgress(timeLine);
            }

            if (doing.getComper() != null) {
                viewHolder.seekBarComPer.setProgress(Integer.parseInt(doing.getComper()));
                viewHolder.txtComPer.setText(doing.getComper() + " %");
                if (timeLine != 0) {
                    float rateTimeComper = (float) Integer.parseInt(doing.getComper()) / timeLine;
                    if (rateTimeComper >= 1) {
                        viewHolder.seekBarComPer.setProgressDrawable(context.getResources().getDrawable(R.drawable.theme_seekbar_green));
                    } else if (rateTimeComper < 0.5f) {
                        viewHolder.seekBarComPer.setProgressDrawable(context.getResources().getDrawable(R.drawable.theme_seekbar_red));
                    } else {
                        viewHolder.seekBarComPer.setProgressDrawable(context.getResources().getDrawable(R.drawable.theme_seekbar_orange));
                    }
                }
            }

            if (doing.getComper().equals("100")) {
                builder.setTitle(doing.getName());
                builder.setMessage(R.string.notice_switch_done);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.status(position);
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

        } else if (taskType.equals(Constant.DONE)) {
            Done done = (Done) list.get(position);
            viewHolder.txtTask.setText("Done");
            getPriority = Integer.parseInt(done.getPriority());
            Utility.setBackgoundPriority(viewHolder.imvPriority, getPriority);
            if (done.getComper() != null) {
                viewHolder.seekBarComPer.setProgress(Integer.parseInt(done.getComper()));
                viewHolder.txtComPer.setText(done.getComper() + " %");
            }
            viewHolder.txtStart.setText(done.getStart().substring(0, done.getStart().length() - 5));
            viewHolder.txtEnd.setText(done.getEnd().substring(0, done.getEnd().length() - 5));
            viewHolder.txtTaskName.setText(done.getName());
            viewHolder.txtTaskTopic.setText(done.getTopic());
            viewHolder.txt_TaskDetail.setText(done.getDetail());

            Utility.setBackgoundTopic(viewHolder.imvTaskTopic, done.getTopic());

            timeStartToCurrent = Utility.getCountOfDay(done.getStart(), Utility.getCurrentDate());
            timeCurrentToEnd = Utility.getCountOfDay(Utility.getCurrentDate(), done.getEnd());
            timeStatToEnd = Utility.getCountOfDay(done.getStart(), done.getEnd());
            timeLine = 0;

            viewHolder.txtTask.setText(Integer.toString(timeCurrentToEnd) + "d");
            if (timeStartToCurrent / 2 < timeCurrentToEnd) {
                if (timeStartToCurrent <= timeCurrentToEnd) {
                    viewHolder.txtTask.setTextColor(context.getResources().getColor(R.color.green));
                } else
                    viewHolder.txtTask.setTextColor(context.getResources().getColor(R.color.orange));
            } else {
                viewHolder.txtTask.setTextColor(Color.RED);
            }


            if (timeCurrentToEnd <= 0) {
                viewHolder.progressBarTimeLine.setProgress(100);
            } else if (timeStartToCurrent <= 0) {
                viewHolder.progressBarTimeLine.setProgress(0);
                viewHolder.txtTask.setText(Integer.toString(Utility.getCountOfDay(done.getStart(), done.getEnd())) + "d");
            } else {
                timeLine = (100 * timeStartToCurrent / timeStatToEnd);
                viewHolder.progressBarTimeLine.setProgress(timeLine);
            }

            if (done.getComper() != null) {
                viewHolder.seekBarComPer.setProgress(Integer.parseInt(done.getComper()));
                viewHolder.txtComPer.setText(done.getComper() + " %");
                if (timeLine != 0) {
                    float rateTimeComper = (float) Integer.parseInt(done.getComper()) / timeLine;
                    if (rateTimeComper >= 1) {
                        viewHolder.seekBarComPer.setProgressDrawable(context.getResources().getDrawable(R.drawable.theme_seekbar_green));
                    } else if (rateTimeComper < 0.5f) {
                        viewHolder.seekBarComPer.setProgressDrawable(context.getResources().getDrawable(R.drawable.theme_seekbar_red));
                    } else {
                        viewHolder.seekBarComPer.setProgressDrawable(context.getResources().getDrawable(R.drawable.theme_seekbar_orange));
                    }
                }
            }
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTask, txtComPer, txtStart, txtEnd, txtTaskName, txtTaskTopic, txt_TaskDetail;
        public ImageView imvPriority, imvTaskTopic;
        public Button btnMenu, btnExpand;
        public SeekBar seekBarComPer;
        public ProgressBar progressBarTimeLine;
        public LinearLayout layoutExpand;

        // for show menu edit and delete
        private PopupMenu popupMenu;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            layoutExpand = (LinearLayout) itemView.findViewById(R.id.layout_Expand);

            imvPriority = (ImageView) itemView.findViewById(R.id.imv_Priority);
            imvTaskTopic = (ImageView) itemView.findViewById(R.id.imv_TaskTopic);

            txtTask = (TextView) itemView.findViewById(R.id.txt_Task);
            txtComPer = (TextView) itemView.findViewById(R.id.txt_CompletePercent);
            txtStart = (TextView) itemView.findViewById(R.id.txt_Start);
            txtEnd = (TextView) itemView.findViewById(R.id.txt_End);
            txtTaskName = (TextView) itemView.findViewById(R.id.txt_TaskName);
            txtTaskTopic = (TextView) itemView.findViewById(R.id.txt_TaskTopic);
            txt_TaskDetail = (TextView) itemView.findViewById(R.id.txt_TaskDetail);
            btnMenu = (Button) itemView.findViewById(R.id.btn_Menu);
            btnExpand = (Button) itemView.findViewById(R.id.btn_Expand);

            seekBarComPer = (SeekBar) itemView.findViewById(R.id.seekBar_CompletePercent);
            progressBarTimeLine = (ProgressBar) itemView.findViewById(R.id.progressBar_TimeLine);
            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                final float scale = context.getResources().getDisplayMetrics().density;
                int pixels = (int) (16 * scale + 0.5f);
                seekBarComPer.getLayoutParams().height = pixels;
                seekBarComPer.requestLayout();
            }
            seekBarComPer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    comper = String.valueOf(progress);
                    txtComPer.setText(comper + " %");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    listener.save(getAdapterPosition(), comper);
                }
            });

            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMenu.show();
                }
            });
            setPopup(itemView);

            btnExpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandClick) {
                        layoutExpand.setVisibility(View.VISIBLE);
                        expandClick = false;
                    } else {
                        layoutExpand.setVisibility(View.GONE);
                        expandClick = true;
                    }
                }
            });
        }

        /*
        * when show popup menu, we can click edit or delete menu item.
        * so we need create a interface to sent position for main activity process
        * */
        private void setPopup(View view) {
            popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.menu_item_task, popupMenu.getMenu());
            if (taskType.equals(Constant.TODO)) {
                popupMenu.getMenu().findItem(R.id.menu_status).setTitle(R.string.status_todo);
            } else if (taskType.equals(Constant.DOING)) {
                popupMenu.getMenu().findItem(R.id.menu_status).setTitle(R.string.status_doing);
            } else {
                popupMenu.getMenu().findItem(R.id.menu_status).setTitle(R.string.status_done);
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    switch (item.getItemId()) {
                        case R.id.menu_edit:
                            if (listener != null) {
                                listener.edit(getAdapterPosition());
                            }
                            break;
                        case R.id.menu_status:
                            if (listener != null) {

                                builder.setTitle(R.string.switch_task);
                                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        listener.status(getAdapterPosition());
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
                            break;
                        case R.id.menu_delete:
                            if (listener != null) {
                                builder.setTitle(R.string.delete_task);
                                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        listener.del(getAdapterPosition());
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

        void save(int position, String data);
    }

    private TaskAdapterListener listener;

    public TaskAdapter setListener(TaskAdapterListener listener) {
        this.listener = listener;
        return this;
    }
}
