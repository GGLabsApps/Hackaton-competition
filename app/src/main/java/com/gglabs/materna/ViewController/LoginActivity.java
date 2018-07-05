package com.gglabs.materna.ViewController;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gglabs.materna.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private final int REQUEST_CODE_SIGN_UP = 55;

    private ProgressBar pbLogin;
    private View rootLayout, layRegister;

    protected TextInputEditText etEmail, etPassword;
    protected Button btnSign, btnRegister;
    protected TextView tvSignUp;

    protected ProgressDialog progressDialog;
//    protected FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    private DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rootLayout = (View) findViewById(R.id.rootLayout);
        layRegister = (View) findViewById(R.id.lay_fields);
        pbLogin = (ProgressBar) findViewById(R.id.pb_login);

        layRegister.setVisibility(View.GONE);

        init();
    }

    private void loadCurrentUser() {
        //pbLogin.setVisibility(View.VISIBLE);
        //rootLayout.setVisibility(View.GONE);
        //firebaseRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void init() {
        etEmail = (TextInputEditText) findViewById(R.id.et_email);
        etPassword = (TextInputEditText) findViewById(R.id.et_password);

        btnSign = (Button) findViewById(R.id.btn_sign);
        btnRegister = (Button) findViewById(R.id.btn_register);
        tvSignUp = (TextView) findViewById(R.id.tv_sign_up);

        //TODO Note that this dialog may change to some other type of system message to the user
        progressDialog = new ProgressDialog(this);

        btnSign.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        String email;
        if ((email = getIntent().getStringExtra("email")) != null) etEmail.setText(email);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign:
                //signIn();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;

            case R.id.btn_register:
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), REQUEST_CODE_SIGN_UP);
//                startActivity(new Intent(LoginActivity.this, ConnectionActivity.class));
                break;
        }
    }


    private void setUI(String phone) {
        etEmail.setText(phone);
        //etPassword.requestFocus();
    }
/*

    private void signIn() {
        Utils.hideKeyBoard(this);

        if (!Utils.checkFields(this, etEmail, etPassword)) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Fields cannot be empty !", Snackbar.LENGTH_LONG).show();
            return;
        }

        //TODO remove the progressDialog and replace with splashScreen
        progressDialog.setMessage("Signing in...");
        progressDialog.show();

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, SplashActivity.class));
                            finish();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content),
                                    "Could not sign in", Snackbar.LENGTH_LONG).show();
                        }

                        progressDialog.dismiss();
                    }
                });
    }
*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SIGN_UP) {
            if (resultCode == RESULT_OK) {

                setUI(data.getStringExtra("email"));
                Snackbar.make(findViewById(android.R.id.content),
                        "Registered successfully", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}
