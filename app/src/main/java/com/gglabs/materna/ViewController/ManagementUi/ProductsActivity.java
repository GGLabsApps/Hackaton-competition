package com.gglabs.materna.ViewController.ManagementUi;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cloudant.client.api.views.AllDocsResponse;
import com.gglabs.materna.Helper.CloudantDbHandler;
import com.gglabs.materna.Helper.CloudantOnCompleteListener;
import com.gglabs.materna.Helper.CloudantOnResponseListener;
import com.gglabs.materna.Model.Product;
import com.gglabs.materna.R;
import com.gglabs.materna.ViewController.Adapter.ProductsAdapter;

import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private CloudantDbHandler cloudantDb = CloudantDbHandler.getInstance();
    private RecyclerView rvProducts;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        init();
        cloudantDb.setOnResponseListener(new CloudantOnResponseListener() {
            @Override
            public void onObjectArrival(AllDocsResponse allDocsResponse) {
                //pbMain.setVisibility(View.GONE);

                List<Product> products = allDocsResponse.getDocsAs(Product.class);
                for (Product p : products) productsAdapter.add(p, false);
            }
        });
        cloudantDb.loadAll("products");
    }

    private void init() {
        rvProducts = (RecyclerView) findViewById(R.id.recyclerView);

        productsAdapter = new ProductsAdapter(this, rvProducts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.setAdapter(productsAdapter);
    }

    private void addProduct() {

        cloudantDb.setOnCompleteListener(new CloudantOnCompleteListener() {
            @Override
            public void onComplete(boolean isComplete) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(ContextCompat.getColor(ProductsActivity.this, R.color.default_text_color_black));

                if (isComplete) {
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(ProductsActivity.this, R.color.green));
                    snackbar.setText("Request successfully sent !").show();
                } else {
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(ProductsActivity.this, R.color.red));
                    snackbar.setText("Error sending request").show();
                }
            }
        });
    }
}
