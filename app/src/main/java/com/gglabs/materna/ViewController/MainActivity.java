package com.gglabs.materna.ViewController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gglabs.materna.Model.Story;
import com.gglabs.materna.R;
import com.gglabs.materna.Utils;
import com.gglabs.materna.ViewController.Adapter.StoriesAdapter;
import com.gglabs.materna.ViewController.Donate.DonateActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final String TAG = "MainActivity";

    private Toolbar toolbar;
    private RecyclerView rvStories;
    private StoriesAdapter storiesAdapter;

    private View ivDonate, layDonate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setDummyData();
    }

    private void setDummyData() {
        for (int i = 0; i < 10; i++) {
            storiesAdapter.add(new Story(
                    "Story title here",
                    getString(R.string.some_story_short),
                    getString(R.string.some_story_long)), false);
        }
    }

    private void init() {
        ivDonate = (View) findViewById(R.id.iv_donate);
        layDonate = (View) findViewById(R.id.lay_donate);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(getString(R.string.dashboard));
        setSupportActionBar(toolbar);

        rvStories = (RecyclerView) findViewById(R.id.recyclerView);

        storiesAdapter = new StoriesAdapter(this, rvStories);

        rvStories.setLayoutManager(new LinearLayoutManager(this));
        rvStories.setAdapter(storiesAdapter);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DonateActivity.class));
            }
        };

        layDonate.setOnClickListener(onClickListener);
        ivDonate.setOnClickListener(onClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        Utils.paintToolbarIcons(this, menu, android.R.color.white);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_profile:
                //
                break;

            case R.id.action_sign_out:
                signOut();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    private void signOut() {
        finish();
    }

}
