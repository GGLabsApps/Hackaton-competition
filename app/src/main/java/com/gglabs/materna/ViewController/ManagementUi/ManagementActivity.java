package com.gglabs.materna.ViewController.ManagementUi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gglabs.materna.R;

public class ManagementActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnTasks, btnProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        init();
    }

    private void init() {
        btnTasks = (Button) findViewById(R.id.btn_tasks);
        btnProducts = (Button) findViewById(R.id.btn_products);

        btnTasks.setOnClickListener(this);
        btnProducts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tasks:
                startActivity(new Intent(ManagementActivity.this, TasksActivity.class));
                break;

            case R.id.btn_products:
                startActivity(new Intent(ManagementActivity.this, ProductsActivity.class));
                break;
        }
    }
}
