package com.developer.makemoney;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class RedeemPayPal extends AppCompatActivity {
    private SharedPreferences coins;
    private String currentCoins;
    private String currentMoney;
    private EditText editText;
    private String email;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private DatabaseReference mRefStatus;
    private TextView mobileno;
    private SharedPreferences money;
    private int usercoins;
    private float usermoney;
    private int usermoneyCoins;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_redeem_pay_tm);
        FirebaseAuth instance = FirebaseAuth.getInstance();
        this.firebaseAuth = instance;
        instance.getCurrentUser();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.toggleSoftInput(1, 0);
        }
        if (VERSION.SDK_INT >= 27) {
            getWindow().setSoftInputMode(16);
        }
        ((ImageView) findViewById(R.id.imageView12)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
               RedeemPayPal.this.onBackPressed();
            }
        });
        this.coins = getSharedPreferences("Rewards", 0);
        SharedPreferences sharedPreferences = getSharedPreferences("Cointomoney", 0);
        this.money = sharedPreferences;
        this.currentMoney = sharedPreferences.getString("Money", "0");
        ((Button) findViewById(R.id.button7)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RedeemPayPal.this.sendMessage();
            }
        });
        EditText editText = (EditText) findViewById(R.id.payTmmobile);
        this.editText = editText;
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (view == RedeemPayPal.this.editText) {
                    String str = "input_method";
                    if (z) {
                        TextView textView = (TextView) RedeemPayPal.this.findViewById(R.id.Checkout);
                        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
                        layoutParams.setMargins(0, 17, 0, 0);
                        textView.setLayoutParams(layoutParams);
                        ImageView imageView = (ImageView) RedeemPayPal.this.findViewById(R.id.imageView13);
                        layoutParams = (LayoutParams) imageView.getLayoutParams();
                        layoutParams.setMargins(0, 40, 0, 0);
                        imageView.setLayoutParams(layoutParams);
                        ((InputMethodManager) RedeemPayPal.this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(RedeemPayPal.this.editText, 2);
                        return;
                    }
                    ((InputMethodManager) RedeemPayPal.this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(RedeemPayPal.this.editText.getWindowToken(), 0);
                }
            }
        });
    }

    private void sendMessage() {
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        FirebaseAuth instance2 = FirebaseAuth.getInstance();
        this.mAuth = instance2;
        DatabaseReference child = instance.getReference().child("Users").child(instance2.getCurrentUser().getUid());
        this.mRef = child;
        child.child("RedeemUSD").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                RedeemPayPal.this.usermoney = Float.parseFloat((String) dataSnapshot.getValue(String.class));
            }
        });
        this.mRef.child("RedeemCoins").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
               RedeemPayPal.this.usermoneyCoins = Integer.parseInt((String) dataSnapshot.getValue(String.class));
            }
        });
        this.mRef.child("Coins").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
               RedeemPayPal.this.usercoins = Integer.parseInt((String) dataSnapshot.getValue(String.class));
            }
        });
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sending Email");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                new Thread(new Runnable() {
                    public void run() {
                        String str = ". ";
                        String str2 = "Redeem";
                        try {
                            RedeemPayPal.this.email = RedeemPayPal.this.editText.getText().toString();
                            progressDialog.dismiss();
                            int access$400 = RedeemPayPal.this.usercoins - RedeemPayPal.this.usermoneyCoins;
                            RedeemPayPal.this.mRef.child("RedeemCoins").removeValue();
                           RedeemPayPal.this.mRef.child("RedeemUSD").removeValue();
                            FirebaseDatabase instance = FirebaseDatabase.getInstance();
                           RedeemPayPal.this.mAuth = FirebaseAuth.getInstance();
                            String uid = RedeemPayPal.this.mAuth.getCurrentUser().getUid();
                           RedeemPayPal.this.mRefStatus = instance.getReference().child(str2).push();
                           RedeemPayPal.this.mRefStatus.child("Status").setValue("Review");
                           RedeemPayPal.this.mRefStatus.child("Email").setValue(RedeemPayPal.this.email);
                           RedeemPayPal.this.mRefStatus.child("MoneyUSD").setValue(String.valueOf(RedeemPayPal.this.usermoney));
                            DatabaseReference push = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child(str2).push();
                            HashMap hashMap = new HashMap();
                            hashMap.put("id", push.getKey());
                            hashMap.put("email", RedeemPayPal.this.email);
                            hashMap.put(str2, Float.valueOf(RedeemPayPal.this.usermoney));
                            Calendar instance2 = Calendar.getInstance();
                            int i = instance2.get(5);
                            int i2 = instance2.get(2);
                            int i3 = instance2.get(1);
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(i);
                            stringBuilder.append(str);
                            stringBuilder.append(i2);
                            stringBuilder.append(str);
                            stringBuilder.append(i3);
                            hashMap.put("Date", stringBuilder.toString());
                            push.setValue(hashMap);
                            Editor edit = RedeemPayPal.this.coins.edit();
                            edit.putString("Coins", String.valueOf(access$400));
                            edit.apply();
                           RedeemPayPal.this.startActivity(new Intent(RedeemPayPal.this, ChoiceSelection_old.class));
                           RedeemPayPal.this.finish();
                        } catch (Exception e) {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Error: ");
                            stringBuilder2.append(e.getMessage());
                            Log.e("mylog", stringBuilder2.toString());
                        }
                    }
                }).start();
            }
        }, 2500);
    }

    public void onBackPressed() {
        startActivity(new Intent(this, Redeem.class));
    }
}
