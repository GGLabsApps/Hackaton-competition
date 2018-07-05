package com.gglabs.materna.ViewController.Donate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gglabs.materna.R;
import com.gglabs.materna.Utils;

public class DonateActivity extends AppCompatActivity implements View.OnClickListener {

    public final String TAG = "DonateActivity";

    //private VolleyJsonHandler volleyJsonHandler = VolleyJsonHandler.getInstance(this);

    private Toolbar toolbar;
    private ImageView ivDonateMoney, ivDonateFood, ivBuyFood;
    private TextView tvDonateMoney, tvDonateFood, tvBuyFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        init();
        //volleyJsonHandler.sendRequest("https://androidtutorialpoint.com/api/volleyJsonObject");
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(getString(R.string.donate));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View[] views = {
                ivDonateMoney = (ImageView) findViewById(R.id.iv_donate_money),
                ivDonateFood = (ImageView) findViewById(R.id.iv_donate_food),
                ivBuyFood = (ImageView) findViewById(R.id.iv_buy_food),
                tvDonateMoney = (TextView) findViewById(R.id.tv_donate_money),
                tvDonateFood = (TextView) findViewById(R.id.tv_donate_food),
                tvBuyFood = (TextView) findViewById(R.id.tv_buy_food),
        };

        for (View v : views) v.setOnClickListener(this);
    }

    private void donateMoney() {
        startActivity(new Intent(this, DonateMoneyActivity.class));
    }

    private void donateFood() {
        startActivity(new Intent(this, DonateFoodActivity.class));
    }

    private void buyFood() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_donate_money:
                donateMoney();
                break;

            case R.id.iv_donate_food:
                donateFood();
                break;

            case R.id.iv_buy_food:
                buyFood();
                break;

            case R.id.tv_donate_money:
                donateMoney();
                break;

            case R.id.tv_donate_food:
                donateFood();
                break;

            case R.id.tv_buy_food:
                buyFood();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        Utils.paintToolbarIcons(this, menu, android.R.color.white);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}