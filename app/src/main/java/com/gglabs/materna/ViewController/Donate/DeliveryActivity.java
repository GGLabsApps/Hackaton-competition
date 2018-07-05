package com.gglabs.materna.ViewController.Donate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gglabs.materna.Helper.CloudantDbHandler;
import com.gglabs.materna.Helper.CloudantOnCompleteListener;
import com.gglabs.materna.Model.Delivery;
import com.gglabs.materna.Model.User;
import com.gglabs.materna.R;
import com.gglabs.materna.Utils;
import com.gglabs.materna.ViewController.ManagementUi.ManagementActivity;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;

import java.util.HashMap;
import java.util.Map;

public class DeliveryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DeliveryActivity";
    private static final int REQUEST_CODE_SCANNER = 80;

    private User loggedUser;
    private CloudantDbHandler dbHandler = CloudantDbHandler.getInstance();
    private MFPPush push = MFPPush.getInstance();
    private MFPPushNotificationListener notificationListener;

    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private TextInputEditText etPhone, etAddress;
    private Button btnSend, btnPeek, btnAdd, btnClear;
    private TextView tvProductsAdded;

    private Map<String, Integer> selectedProducts = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        loggedUser = new User("Greg", "GG", "050-123-457",
                "Ramat Gan", "Shoham 5",
                "a@email.com", "pass123");
        loggedUser.setId("eba2a1de9b1b4945828555c2b90247b9");

        init();

        if (loggedUser != null) fillFields();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (push != null) {
            push.listen(notificationListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (push != null) {
            push.hold();
        }
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(getString(R.string.delivery));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etPhone = (TextInputEditText) findViewById(R.id.et_phone);
        etAddress = (TextInputEditText) findViewById(R.id.et_address);
        tvProductsAdded = (TextView) findViewById(R.id.tv_products_added);

        Button[] buttonArr = {
                btnSend = (Button) findViewById(R.id.btn_send),
                btnPeek = (Button) findViewById(R.id.btn_peek),
                btnAdd = (Button) findViewById(R.id.btn_add),
                btnClear = (Button) findViewById(R.id.btn_clear)
        };

        for (Button b : buttonArr) b.setOnClickListener(this);

        //TODO Note that this dialog may change to some other type of system message to the user
        progressDialog = new ProgressDialog(this);

        initPushNotification();
    }

    private void initPushNotification() {
        String APP_GUID = "7ef66fed-7981-4206-9ace-da8d6907c264";
        String CLIENT_SECRET = "969d39ab-ccd8-4b94-ad18-9793956b8dbf";

        // Initialize the SDK
        BMSClient.getInstance().initialize(this, BMSClient.REGION_US_SOUTH);

        //Initialize client Push SDK
        push.initialize(this, APP_GUID, CLIENT_SECRET);

        //Register Android devices
        push.registerDevice(new MFPPushResponseListener<String>() {

            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "Success registering push notification");
                //handle successful device registration here
            }

            @Override
            public void onFailure(MFPPushException ex) {
                Log.d(TAG, "Failed registering push notification");
                //handle failure in device registration here
            }
        });

        push.subscribe("Manager", new MFPPushResponseListener<String>() {
            @Override
            public void onFailure(MFPPushException ex) {
                //updateTextView("Failure" + ex.getMessage());
            }

            @Override
            public void onSuccess(String response) {
                //updateTextView(Success: "+ response);
            }
        });

        notificationListener = new MFPPushNotificationListener() {
            @Override
            public void onReceive(MFPSimplePushNotification message) {
                //Toast.makeText(DeliveryActivity.this, "Notification received:\n" + message, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Notification received: " + message);
            }
        };
    }

    private void sendDeliveryRequest() {
        if (!Utils.checkFields(this, etPhone, etAddress))
            return;

        progressDialog.setMessage(getString(R.string.sending_request));
        progressDialog.show();

        long timeCreated = System.currentTimeMillis();
        long timeToPick = timeCreated + 50400000;
        String address = etAddress.getText().toString();

        Delivery delivery = new Delivery(loggedUser.getId(), timeCreated, timeToPick, address, null);

        dbHandler.setOnCompleteListener(new CloudantOnCompleteListener() {
            @Override
            public void onComplete(boolean isComplete) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(ContextCompat.getColor(DeliveryActivity.this, R.color.default_text_color_black));

                if (isComplete) {
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(DeliveryActivity.this, R.color.green));
                    snackbar.setText("Request successfully sent !").show();

                    Utils.clearFields(etAddress, etPhone);
                } else {
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(DeliveryActivity.this, R.color.red));
                    snackbar.setText("Error sending request").show();
                }
                progressDialog.dismiss();
            }
        });

        dbHandler.save(delivery, "tasks");
        progressDialog.setMessage(getString(R.string.sending_request));
    }

    private void fillFields() {
        etPhone.setText(loggedUser.getPhone());
        etAddress.setText(loggedUser.getAddress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        Utils.paintToolbarIcons(this, menu, android.R.color.white);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_send:
                sendDeliveryRequest();
                break;

            case R.id.btn_peek:
                startActivity(new Intent(DeliveryActivity.this, ManagementActivity.class));
                break;

            case R.id.btn_add:
                startActivityForResult(new Intent(DeliveryActivity.this, ScannerActivity.class),
                        REQUEST_CODE_SCANNER);
                break;

            case R.id.btn_clear:
                selectedProducts.clear();
                updateProductsAdded();
                break;

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SCANNER) {
            if (resultCode == RESULT_OK) {
                String[] barcodes = (String[]) data.getSerializableExtra("selected_products_barcodes");
                int[] quantity = (int[]) data.getSerializableExtra("selected_products_quantity");

                for (int i = 0; i < barcodes.length; i++)
                    selectedProducts.put(barcodes[i], quantity[i]);

                updateProductsAdded();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateProductsAdded() {

        int totalProducts = 0;
        for (int q : selectedProducts.values())
            totalProducts += q;

        if (selectedProducts.size() > 0)
            tvProductsAdded.setText(totalProducts + " added");
        else tvProductsAdded.setText("Nothing added");
    }
}
