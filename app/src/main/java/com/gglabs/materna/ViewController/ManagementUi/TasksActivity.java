package com.gglabs.materna.ViewController.ManagementUi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cloudant.client.api.views.AllDocsResponse;
import com.gglabs.materna.Helper.CloudantDbHandler;
import com.gglabs.materna.Helper.CloudantOnResponseListener;
import com.gglabs.materna.Model.Delivery;
import com.gglabs.materna.R;
import com.gglabs.materna.Utils;
import com.gglabs.materna.ViewController.Adapter.TasksAdapter;

import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private static final String TAG = "TaskActivity";

    private CloudantDbHandler dbHandler = CloudantDbHandler.getInstance();

    private ProgressBar pbMain;
    private Toolbar toolbar;
    private RecyclerView rvTasks;
    private TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        init();

        dbHandler.setOnResponseListener(new CloudantOnResponseListener() {
            @Override
            public void onObjectArrival(AllDocsResponse allDocsResponse) {
                pbMain.setVisibility(View.GONE);

                List<Delivery> tasks = allDocsResponse.getDocsAs(Delivery.class);
                for (Delivery d : tasks) tasksAdapter.add(d, false);

                Toast.makeText(TasksActivity.this, "Response Arrived,\nis null: " + (allDocsResponse == null), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "tasks.size()" + tasks.size());
            }
        });
        dbHandler.loadAll("tasks");
    }

    public void init() {
        pbMain = (ProgressBar) findViewById(R.id.pb_main);

        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tasks");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        rvTasks = (RecyclerView) findViewById(R.id.recyclerView);

        tasksAdapter = new TasksAdapter(this, rvTasks);

        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(tasksAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        Utils.paintToolbarIcons(this, menu, android.R.color.white);
        return true;
    }
}
