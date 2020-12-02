package com.developer.makemoney;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DailyCheckins extends AppCompatActivity {
    private Calendar calendar;
    private SharedPreferences coins;
    private Button fri;
    private Button mon;
    private Button sat;
    private Button sun;
    private Button thu;
    private String todayString;
    private Button tue;
    private Button wed;
    private int weekday;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_daily_checkins);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.toggleSoftInput(1, 0);
        }
        ((ImageView) findViewById(R.id.imageView8)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DailyCheckins.this.onBackPressed();
            }
        });
        this.coins = getSharedPreferences("Rewards", 0);
        Calendar instance = Calendar.getInstance();
        this.calendar = instance;
        int i = instance.get(1);
        int i2 = this.calendar.get(2);
        int i3 = this.calendar.get(5);
        this.weekday = this.calendar.get(7);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        String str = "";
        stringBuilder.append(str);
        stringBuilder.append(i2);
        stringBuilder.append(str);
        stringBuilder.append(i3);
        this.todayString = stringBuilder.toString();
        this.sun = (Button) findViewById(R.id.btSun);
        this.mon = (Button) findViewById(R.id.btMon);
        this.tue = (Button) findViewById(R.id.btTue);
        this.wed = (Button) findViewById(R.id.btWed);
        this.thu = (Button) findViewById(R.id.btThu);
        this.fri = (Button) findViewById(R.id.btFri);
        this.sat = (Button) findViewById(R.id.btSat);
        this.sun.setEnabled(false);
        this.mon.setEnabled(false);
        this.tue.setEnabled(false);
        this.wed.setEnabled(false);
        this.thu.setEnabled(false);
        this.fri.setEnabled(false);
        this.sat.setEnabled(false);
        boolean z = getSharedPreferences("DAILYCHECKS", 0).getBoolean(this.todayString, false);
        i2 = this.weekday;
        if (i2 == 1) {
            if (z) {
                this.sun.setBackground(getResources().getDrawable(R.drawable.back11));
                return;
            }
            this.sun.setEnabled(true);
            this.sun.setAlpha(0.0f);
            this.sun.setBackground(getResources().getDrawable(R.drawable.back1now));
            this.sun.setTextColor(-1);
        } else if (i2 == 2) {
            if (z) {
                this.mon.setBackground(getResources().getDrawable(R.drawable.back11));
                return;
            }
            this.mon.setEnabled(true);
            this.mon.setAlpha(1.0f);
            this.mon.setBackground(getResources().getDrawable(R.drawable.back1now));
            this.mon.setTextColor(-1);
        } else if (i2 == 3) {
            if (z) {
                this.tue.setBackground(getResources().getDrawable(R.drawable.back11));
                return;
            }
            this.tue.setEnabled(true);
            this.tue.setAlpha(1.0f);
            this.tue.setBackground(getResources().getDrawable(R.drawable.back1now));
            this.tue.setTextColor(-1);
        } else if (i2 == 4) {
            if (z) {
                this.wed.setBackground(getResources().getDrawable(R.drawable.back11));
                return;
            }
            this.wed.setEnabled(true);
            this.wed.setAlpha(1.0f);
            this.wed.setBackground(getResources().getDrawable(R.drawable.back1now));
            this.wed.setTextColor(-1);
        } else if (i2 == 5) {
            if (z) {
                this.thu.setBackground(getResources().getDrawable(R.drawable.back1));
                return;
            }
            this.thu.setEnabled(true);
            this.thu.setAlpha(1.0f);
            this.thu.setBackground(getResources().getDrawable(R.drawable.back1now));
            this.thu.setTextColor(-1);
        } else if (i2 == 6) {
            if (z) {
                this.fri.setBackground(getResources().getDrawable(R.drawable.back11));
                return;
            }
            this.fri.setEnabled(true);
            this.fri.setAlpha(1.0f);
            this.fri.setBackground(getResources().getDrawable(R.drawable.back1now));
            this.fri.setTextColor(-1);
        } else if (i2 != 7) {
        } else {
            if (z) {
                this.sat.setBackground(getResources().getDrawable(R.drawable.back11));
                return;
            }
            this.sat.setEnabled(true);
            this.sat.setAlpha(1.0f);
            this.sat.setBackground(getResources().getDrawable(R.drawable.back1now));
            this.sat.setTextColor(-1);
        }
    }

    public void monCheck(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("DAILYCHECKS", 0);
        if (sharedPreferences.getBoolean(this.todayString, false)) {
            Toast.makeText(this, "Reward already recieved", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "10 Coins Recieved!", Toast.LENGTH_LONG).show();
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(this.todayString, true);
        edit.apply();
        String str = "Coins";
        int parseInt = Integer.parseInt(this.coins.getString(str, "0")) + 10;
        Editor edit2 = this.coins.edit();
        edit2.putString(str, String.valueOf(parseInt));
        edit2.apply();
    }

    public void tueCheck(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("DAILYCHECKS", 0);
        if (sharedPreferences.getBoolean(this.todayString, false)) {
            Toast.makeText(this, "Reward already recieved", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "10 Coins Recieved!", Toast.LENGTH_LONG).show();
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(this.todayString, true);
        edit.apply();
        String str = "Coins";
        int parseInt = Integer.parseInt(this.coins.getString(str, "0")) + 10;
        Editor edit2 = this.coins.edit();
        edit2.putString(str, String.valueOf(parseInt));
        edit2.apply();
    }

    public void wedCheck(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("DAILYCHECKS", 0);
        if (sharedPreferences.getBoolean(this.todayString, false)) {
            Toast.makeText(this, "Reward already recieved", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "20 Coins Recieved!", Toast.LENGTH_LONG).show();
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(this.todayString, true);
        edit.apply();
        String str = "Coins";
        int parseInt = Integer.parseInt(this.coins.getString(str, "0")) + 20;
        Editor edit2 = this.coins.edit();
        edit2.putString(str, String.valueOf(parseInt));
        edit2.apply();
    }

    public void thuCheck(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("DAILYCHECKS", 0);
        if (sharedPreferences.getBoolean(this.todayString, false)) {
            Toast.makeText(this, "Reward already recieved", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "20 Coins Recieved!", Toast.LENGTH_LONG).show();
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(this.todayString, true);
        edit.apply();
        String str = "Coins";
        int parseInt = Integer.parseInt(this.coins.getString(str, "0")) + 20;
        Editor edit2 = this.coins.edit();
        edit2.putString(str, String.valueOf(parseInt));
        edit2.apply();
    }

    public void friCheck(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("DAILYCHECKS", 0);
        if (sharedPreferences.getBoolean(this.todayString, false)) {
            Toast.makeText(this, "Reward already recieved", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "30 Coins Recieved!", Toast.LENGTH_LONG).show();
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(this.todayString, true);
        edit.apply();
        String str = "Coins";
        int parseInt = Integer.parseInt(this.coins.getString(str, "0")) + 30;
        Editor edit2 = this.coins.edit();
        edit2.putString(str, String.valueOf(parseInt));
        edit2.apply();
    }

    public void satCheck(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("DAILYCHECKS", 0);
        if (sharedPreferences.getBoolean(this.todayString, false)) {
            Toast.makeText(this, "Reward already recieved", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "30 Coins Recieved!", Toast.LENGTH_LONG).show();
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(this.todayString, true);
        edit.apply();
        String str = "Coins";
        int parseInt = Integer.parseInt(this.coins.getString(str, "0")) + 30;
        Editor edit2 = this.coins.edit();
        edit2.putString(str, String.valueOf(parseInt));
        edit2.apply();
    }

    public void sunCheck(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("DAILYCHECKS", 0);
        if (sharedPreferences.getBoolean(this.todayString, false)) {
            Toast.makeText(this, "Reward already recieved", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "50 Coins Recieved!", Toast.LENGTH_LONG).show();
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(this.todayString, true);
        edit.apply();
        String str = "Coins";
        int parseInt = Integer.parseInt(this.coins.getString(str, "0")) + 50;
        Editor edit2 = this.coins.edit();
        edit2.putString(str, String.valueOf(parseInt));
        edit2.apply();
    }

    public void onBackPressed() {
        startActivity(new Intent(this, ChoiceSelection_old.class));
    }
}
