package com.developer.makemoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NoInternetActivity extends AppCompatActivity {
    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_no_internet);
        ((Button) findViewById(R.id.button3)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NoInternetActivity.this.startActivity(new Intent(NoInternetActivity.this, SplashScreen.class));
            }
        });
    }
}
