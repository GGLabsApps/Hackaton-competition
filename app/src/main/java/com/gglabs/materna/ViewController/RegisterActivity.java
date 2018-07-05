package com.gglabs.materna.ViewController;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.TextView;

import com.gglabs.materna.Helper.CloudantDbHandler;
import com.gglabs.materna.Helper.CloudantOnCompleteListener;
import com.gglabs.materna.Model.User;
import com.gglabs.materna.R;
import com.gglabs.materna.Utils;

/**
 * Created by GG on 16/02/2018.
 */

public class RegisterActivity extends LoginActivity {

    private CloudantDbHandler dbHandler = CloudantDbHandler.getInstance();

    private View layRegister;
    private TextView tvHeader;
    private TextInputEditText etFirstName, etLastName,
            etPhone, etEmail, etCity, etAddress, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        tvHeader = (TextView) findViewById(R.id.tv_header);
        layRegister = (View) findViewById(R.id.lay_fields);

        etFirstName = (TextInputEditText) findViewById(R.id.et_first_name);
        etLastName = (TextInputEditText) findViewById(R.id.et_last_name);
        etEmail = (TextInputEditText) findViewById(R.id.et_email);
        etPassword = (TextInputEditText) findViewById(R.id.et_password);
        etPhone = (TextInputEditText) findViewById(R.id.et_phone);
        etCity = (TextInputEditText) findViewById(R.id.et_city);
        etAddress = (TextInputEditText) findViewById(R.id.et_address);

        btnSign.setVisibility(View.GONE);
        tvSignUp.setVisibility(View.GONE);
        layRegister.setVisibility(View.VISIBLE);
        tvHeader.setText(getResources().getString(R.string.register));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        Utils.hideKeyBoard(this);
        if (!Utils.checkFields(this,
                etFirstName, etLastName, etPhone, etEmail, etCity, etAddress, etPassword)) {
            Snackbar.make(findViewById(android.R.id.content),
                    "All fields must be filled !", Snackbar.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        final User user = new User(firstName, lastName, phone, city, address, email, password);

        dbHandler.setOnCompleteListener(new CloudantOnCompleteListener() {
            @Override
            public void onComplete(boolean isComplete) {
                if (isComplete) {
                    Intent i = new Intent();
                    i.putExtra("email", user.getEmail());
                    setResult(RESULT_OK, i);
                    finish();
                } else {
                    Snackbar.make(findViewById(android.R.id.content),
                            "Could not register", Snackbar.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
        dbHandler.save(user, "users");

/*        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            task.setId(firebaseAuth.getCurrentUser().getUid());
                            task.saveToFirebase(dbReference);

                            Intent i = new Intent();
                            i.putExtra("email", task.getEmail());
                            setResult(RESULT_OK, i);
                            finish();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content),
                                    "Could not register", Snackbar.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });*/
    }
}
/*

    private void registerUser() {
        Utils.hideKeyBoard(this);
        if (!Utils.checkFields(this, etFirstName, etPhone, etEmail, etPassword)) {
            Snackbar.make(findViewById(android.R.id.content),
                    "All fields must be filled !", Snackbar.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        String name = etFirstName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        final FirebaseUser user = new FirebaseUser(name, phone, email, password);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.setId(firebaseAuth.getCurrentUser().getUid());
                            user.saveToFirebase(dbReference);

                            Intent i = new Intent();
                            i.putExtra("email", user.getEmail());
                            setResult(RESULT_OK, i);
                            finish();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content),
                                    "Could not register", Snackbar.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
*/
