package com.developer.makemoney;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SplashScreen extends AppCompatActivity {


    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_splash_screen);


        new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(SplashScreen.this.getApplicationContext(), LoginActivity.class);
                    intent.putExtras(bundle);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                   SplashScreen.this.startActivity(intent);
                   SplashScreen.this.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Catch block", Log.getStackTraceString(e));
                }
            }
        }.start();
        if (((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
            Intent intent = new Intent(this, NoInternetActivity.class);
            startActivity(intent);
        }
    }

    public void onStart() {
        super.onStart();

    }

    public void onStop() {
        super.onStop();
    }
}
