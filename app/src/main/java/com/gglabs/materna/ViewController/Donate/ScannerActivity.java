package com.gglabs.materna.ViewController.Donate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;

import com.gglabs.materna.Helper.BarcodeReader;
import com.gglabs.materna.Helper.OnDetectListener;
import com.gglabs.materna.Model.Product;
import com.gglabs.materna.R;
import com.gglabs.materna.Utils;
import com.gglabs.materna.ViewController.Adapter.ProductsAdapter;

import java.io.IOException;

public class ScannerActivity extends AppCompatActivity {

    private static final String TAG = "ScannerActivity";

    private Toolbar toolbar;

    private SurfaceView surfaceView;
    private final int REQUEST_CODE_PERMISSION_CAMERA = 50;

    private RecyclerView rvProducts;
    private ProductsAdapter productsAdapter;

    private BarcodeReader barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(getString(R.string.donation));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvProducts = (RecyclerView) findViewById(R.id.recyclerView);
        productsAdapter = new ProductsAdapter(this, rvProducts, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.setAdapter(productsAdapter);

        surfaceView = (SurfaceView) findViewById(R.id.surf_camera);

        barcodeReader = new BarcodeReader(this, surfaceView, 5);
        barcodeReader.setOnDetectListener(new OnDetectListener() {
            @Override
            public void onDetect(final String barcode) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (barcode != null) {

                            if (!productsAdapter.contains(barcode)) {
                                Log.d(TAG, "Detected new product:" + barcode);
                                productsAdapter.add(new Product(barcode, "Materna", 800, 59.9f), false);
                            }
                        }
                    }
                });
            }
        });

     /*if (barcode != null) {

        if (!productsAdapter.contains(barcode)) {
            Log.d(TAG, "Detected new product:" + barcode);
            productsAdapter.add(new Product(barcode, "Materna", 800, 59.9f), false);
        }
    }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage, menu);

        Utils.paintToolbarIcons(this, menu, android.R.color.white);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:

                String[] barcodes = new String[productsAdapter.getProducts().size()];
                int[] quantity = new int[productsAdapter.getProducts().size()];

                for (int i = 0; i < productsAdapter.getProducts().size(); i++) {
                    Product p = productsAdapter.getProducts().get(i);
                    barcodes[i] = p.getId();
                    quantity[i] = p.getQuantity();
                }

                Intent i = new Intent();
                i.putExtra("selected_products_barcodes", barcodes);
                i.putExtra("selected_products_quantity", quantity);
                setResult(RESULT_OK, i);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                barcodeReader.getCameraSource().start(surfaceView.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
