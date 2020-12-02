package com.developer.makemoney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Redeem extends AppCompatActivity {
    private TextView coins2;
    private FirebaseAuth firebaseAuth;
    private Integer integer;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private int usercoin;
    private float x;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_redeem);
        FirebaseAuth instance = FirebaseAuth.getInstance();
        this.firebaseAuth = instance;
        instance.getCurrentUser();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.toggleSoftInput(1, 0);
        }
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        if (((float) displayMetrics.heightPixels) / displayMetrics.density > 700.0f) {
            getWindow().setSoftInputMode(16);
        }
        ((ImageView) findViewById(R.id.imageView4)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Redeem.this.onBackPressed();
            }
        });
        final EditText editText = (EditText) findViewById(R.id.payTmmobile);
        editText.setTextSize(100.0f);
        editText.setMaxEms(10);
        Handler handler = new Handler();
        Handler finalHandler1 = handler;
        handler.postDelayed(new Runnable() {
            public void run() {
                if (editText.length() < 5) {
                    editText.setTextSize(100.0f);
                }
                if (editText.length() == 5) {
                    editText.setTextSize(90.0f);
                }
                if (editText.length() == 6) {
                    editText.setTextSize(80.0f);
                }
                if (editText.length() == 7) {
                    editText.setTextSize(70.0f);
                }
                if (editText.length() == 8) {
                    editText.setTextSize(60.0f);
                }
                if (editText.length() == 10) {
                    editText.setTextSize(50.0f);
                }
                if (editText.length() == 0) {
                    editText.setText("0");
                }
                finalHandler1.postDelayed(this, 10);
            }
        }, 10);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (view == editText) {
                    String str = "input_method";
                    if (z) {
                        RelativeLayout relativeLayout = (RelativeLayout) Redeem.this.findViewById(R.id.relativecoinbalance);
                        relativeLayout.getLayoutParams().height = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                        relativeLayout.getLayoutParams().width = 970;
                        relativeLayout.setBackgroundResource(R.drawable.backreedemkeyboard);
                        ((TextView)Redeem.this.findViewById(R.id.textView)).setPadding(0, 47, 0, 0);
                        TextView textView = (TextView)Redeem.this.findViewById(R.id.textViewCoins);
                        textView.setTextSize(24.0f);
                        textView.setPadding(520, -15, 10, 0);
                        textView = (TextView) Redeem.this.findViewById(R.id.Checkout);
                        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
                        layoutParams.setMargins(0, 3, 0, 0);
                        textView.setLayoutParams(layoutParams);
                        textView = (TextView) Redeem.this.findViewById(R.id.textView6);
                        layoutParams = (LayoutParams) textView.getLayoutParams();
                        layoutParams.setMargins(0, 5, 0, 0);
                        textView.setLayoutParams(layoutParams);
                        ((RelativeLayout) Redeem.this.findViewById(R.id.relative)).getLayoutParams().height = 650;
                        ImageView imageView = (ImageView) Redeem.this.findViewById(R.id.imageView5);
                        LayoutParams layoutParams2 = (LayoutParams) imageView.getLayoutParams();
                        layoutParams2.width = 150;
                        layoutParams2.height = 150;
                        layoutParams2.setMarginStart(27);
                        imageView.setPadding(35, 35, 35, 35);
                        imageView.setLayoutParams(layoutParams2);
                        ((InputMethodManager) Redeem.this.getApplicationContext().getSystemService(str)).showSoftInput(editText, 2);
                        return;
                    }
                    ((InputMethodManager) Redeem.this.getApplicationContext().getSystemService(str)).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }
        });
        this.coins2 = (TextView) findViewById(R.id.textViewCoins);
        final TextView textView = (TextView) findViewById(R.id.textView6);
        handler = new Handler();
        FirebaseDatabase instance2 = FirebaseDatabase.getInstance();
        FirebaseAuth instance3 = FirebaseAuth.getInstance();
        this.mAuth = instance3;
        DatabaseReference child = instance2.getReference().child("Users").child(instance3.getCurrentUser().getUid());
        this.mRef = child;
        child.child("RedeemCoins").removeValue();
        this.mRef.child("RedeemUSD").removeValue();
        this.mRef.child("Coins").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
               Redeem.this.usercoin = Integer.parseInt((String) dataSnapshot.getValue(String.class));
               Redeem.this.coins2.setText(String.valueOf(Redeem.this.usercoin));
            }
        });
        final EditText editText2 = (EditText) findViewById(R.id.payTmmobile);
        editText2.setText("0");
        Handler finalHandler = handler;
        handler.postDelayed(new Runnable() {
            public void run() {
                if (editText2.length() != 0) {
                    Redeem.this.integer = Integer.valueOf(editText2.getText().toString());
                    Redeem redeem = Redeem.this;
                    redeem.x = (float) (((double) redeem.integer.intValue()) * 1.0E-4d);
                    //TextView textView = text;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("It is ");
                    stringBuilder.append(Redeem.this.x);
                    stringBuilder.append("USD");
                    textView.setText(stringBuilder.toString());
                    FirebaseDatabase instance = FirebaseDatabase.getInstance();
                    Redeem.this.mAuth = FirebaseAuth.getInstance();
                    Redeem.this.mRef = instance.getReference().child("Users").child(Redeem.this.mAuth.getCurrentUser().getUid());
                }
                finalHandler.postDelayed(this, 1000);
            }
        }, 1000);
        ((Button) findViewById(R.id.button7)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Redeem.this.integer.intValue() >= Redeem.this.usercoin || Redeem.this.integer.intValue() <= 10) {
                    Toast.makeText(Redeem.this.getApplication(), "You don't have so many coins", 1).show();
                    return;
                }
                Redeem.this.mRef.child("RedeemUSD").setValue(String.valueOf(Redeem.this.x));
                Redeem.this.mRef.child("RedeemCoins").setValue(String.valueOf(Redeem.this.integer));
                Redeem.this.startActivity(new Intent(Redeem.this.getApplicationContext(), RedeemPayPal.class));
                Redeem.this.finish();
            }
        });
    }

    public void payTm(View view) {
        startActivity(new Intent(getApplicationContext(), RedeemPayPal.class));
    }

    public void onBackPressed() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
