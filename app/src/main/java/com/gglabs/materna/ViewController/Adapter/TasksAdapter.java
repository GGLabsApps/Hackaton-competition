package com.gglabs.materna.ViewController.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gglabs.materna.Model.Delivery;
import com.gglabs.materna.Model.Task;
import com.gglabs.materna.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GG on 18/02/2018.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private static final String TAG = "TasksAdapter";
    private Context context;

    private RecyclerView rv;
    private List<Task> tasks;

    private View.OnClickListener clickListener;

    public TasksAdapter(Context context, RecyclerView rv) {
        this.context = context;
        this.rv = rv;

        tasks = new ArrayList<>();
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_done:
                        //
                        break;
                }
            }
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.list_item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int position) {
        Task task = tasks.get(vh.getLayoutPosition());
        vh.onBind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void add(Task task, boolean scrollTo) {
        tasks.add(task);
        notifyItemInserted(tasks.size());
        //if (scrollTo) rv.smoothScrollToPosition(getItemCount());
    }

    public void add(int position, Task task) {
        tasks.add(task);
        notifyItemInserted(position);

        rv.scrollToPosition(getItemCount() - 1);
    }

    public void remove(Task task) {
        int position = tasks.indexOf(task);

        tasks.remove(position);
        notifyItemRemoved(position);
    }

    public void update(int position, Task task) {
        tasks.set(position, task);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View rootLayout, btnDone;
        private TextView tvId, tvTask;

        private ViewHolder(View itemView) {
            super(itemView);
            rootLayout = (View) itemView.findViewById(R.id.rootLayout);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
            tvTask = (TextView) itemView.findViewById(R.id.tv_task);

            btnDone = (View) itemView.findViewById(R.id.btn_done);
        }

        public void onBind(Task task) {
            tvId.setText("Db document id: " + task.getId());
            tvTask.setText("User created id: " + task.getUserCreatedId() +
                    "\n\nTask Completed: " + task.isComplete());

            if (task instanceof Delivery) {
                Delivery d = (Delivery) task;
                tvTask.append("\n\nType of Task: Delivery");
                tvTask.append("\n\nAddress: " + d.getAddress());
            }


        }
    }

}

