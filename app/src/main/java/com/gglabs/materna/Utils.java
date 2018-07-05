package com.gglabs.materna;

/**
 * Created by GG on 22/12/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utils {

    private static final String TAG = "Utils";

   /* public static List<String> getUserIdsFromSnapshot(DataSnapshot dataSnapshot) {
        List<String> output = new ArrayList<>();

        for (DataSnapshot ds : dataSnapshot.child("calls").getChildren())
            output.add((ds.child("userOpened").getValue(String.class)));

        return output;
    }

    public static FirebaseUser getUserById(String userId, DataSnapshot dataSnapshot) {
        DataSnapshot ds = dataSnapshot.child("users").child(userId);

        Task user = ds.getValue(Task.class);

        if (ds.exists() && user != null) {
            FirebaseUser firebaseUser = new FirebaseUser(user, ds.child("password").getValue(String.class));
            firebaseUser.setId(ds.getKey());
            return firebaseUser;
        } else return null;
    }

    public static int getUserPrivilegeById(String userId, DataSnapshot dataSnapshot) {
        DataSnapshot ds = dataSnapshot.child("users").child(userId).child("privilege");
        if (ds.exists()) {
            return ds.getValue(Integer.class);
        } else return -1;
    }

    public static int getUserDepartmentById(String userId, DataSnapshot dataSnapshot) {
        DataSnapshot ds = dataSnapshot.child("users").child(userId).child("department");
        if (ds.exists()) {
            return ds.getValue(Integer.class);
        } else return -1;
    }

    public static List<Task> getUserFromFirebase(DataSnapshot dataSnapshot) {
        List<Task> output = new ArrayList<>();

        for (DataSnapshot ds : dataSnapshot.child("users").getChildren())
            output.add(composeUser(ds.getValue(Task.class), ds.getKey()));

        return output;
    }

    public static List<Call> getCallsFromFirebase(DataSnapshot dataSnapshot) {
        List<Call> output = new ArrayList<>();

        for (DataSnapshot ds : dataSnapshot.child("calls").getChildren())
            output.add(composeCallFromFirebase(ds));

        return output;
    }

    public static Task composeUser(Task user, String uid) {
        Task newUser = new Task();

        newUser.setId(uid);
        newUser.setName(user.getName());
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());
        newUser.setDepartment(user.getDepartment());
        newUser.setPrivilege(user.getPrivilege());
        Log.d(TAG, "composeUser(): getId(): " + newUser.getId());
        return newUser;
    }

    public static Call composeCallFromFirebase(DataSnapshot ds) {
        Call call = new Call();

        call.setId(ds.getKey());
        call.setForDepartment(ds.getValue(Call.class).getForDepartment());
        call.setAction(ds.getValue(Call.class).getAction());
        call.setLocation(ds.getValue(Call.class).getLocation());

        call.setUserOpened(ds.child("userOpened").getValue(String.class));
        call.setTimeOpened(ds.getValue(Call.class).getTimeOpened());

        boolean isClosed = ds.getValue(Call.class).isClosed();
        if (isClosed) {
            call.setClosed(isClosed);
            call.setTimeClosed(ds.getValue(Call.class).getTimeClosed());
            call.setUserClosed(ds.child("userClosed").getValue(String.class));
        }

        return call;
    }

    public static long getTimestamp(final DatabaseReference databaseReference){
        HashMap<String, Object> timestamp = new HashMap<>();
        databaseReference.setValue(ServerValue.TIMESTAMP).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getValue(Long.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        return (long) timestamp.get("timestamp");
    }*/

    public static void showAlertDialog(Context context, int messageStringId, int btnDoStringId, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(context.getResources().getString(messageStringId));
        alertDialogBuilder.setPositiveButton(context.getResources().getString(btnDoStringId), listener);

        alertDialogBuilder.setNegativeButton(context.getResources().getString(R.string.back), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.green));
        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.red));
    }

    public static int getRandomInt(int min, int max) {
        return new Random().nextInt(max) + min;
    }

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    public static void dial(Context context, String phone) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
    }

    public static boolean checkFields(Context context, TextInputEditText... fields) {
        boolean result = true;

        for (TextInputEditText et : fields)
            if (TextUtils.isEmpty(et.getText().toString().trim())) {
                TextInputLayout etLayout = (TextInputLayout) ((ViewGroup) et.getParent()).getParent();
                etLayout.setError(context.getResources().getString(R.string.field_cant_be_empty));
                //et.setError("Field cannot be empty");
                result = false;
            }

        return result;
    }

    public static boolean checkFields(Context context, EditText... fields) {
        boolean result = true;

        for (EditText et : fields)
            if (TextUtils.isEmpty(et.getText().toString().trim())) {
                et.setError(context.getResources().getString(R.string.field_cant_be_empty));
                result = false;
            }

        return result;
    }

    public static void clearFields(EditText... fields) {
        for (EditText et : fields)
            if (et != null) et.getText().clear();
    }

    public static void hideKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void paintToolbarIcons(Context context, Menu menu, int ColorId) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(ContextCompat.getColor(context, ColorId), PorterDuff.Mode.SRC_IN);
            }
        }
    }

    public static String getTime(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date currentDate = new Date(timeMillis);

        return sdf.format(currentDate);
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyy HH:mm");
        Date currentDate = new Date(System.currentTimeMillis());

        return sdf.format(currentDate);
    }
}
