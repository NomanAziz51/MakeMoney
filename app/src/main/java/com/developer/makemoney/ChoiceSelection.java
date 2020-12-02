package com.developer.makemoney;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChoiceSelection extends AppCompatActivity implements RewardedVideoAdListener {

    private TextView coins2;
    private boolean connected;
    public SharedPreferences coins;
    private String currentCoins;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mRef;
    private RewardedVideoAd mRewardedVideoAd;
    private InterstitialAd mInterstitialAd;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_selection);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
                if (null != activeNetwork) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) { }
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) { }
                } else {

                    Intent intent = new Intent(ChoiceSelection.this, NoInternetActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();

                }
                handler.postDelayed(this, 1000);
            }
        };

        mAuth = FirebaseAuth.getInstance();

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }

        final Handler handler2 = new Handler();
        final int delay = 1000; //milliseconds
        handler2.postDelayed(new Runnable(){
            public void run(){
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    connected = true;
                }
                else
                    connected = false;
                handler2.postDelayed(this, delay);
            }
        }, delay);




        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user =  mAuth.getCurrentUser();
        String userId = user.getUid();
        String email = user.getEmail();
      //  mRef =  database.getReference().child("Users").child(userId);
       /* mRef =  database.getReference().child("Users").child(userId);
        mRef.child("Coins").setValue(currentCoins);*/
        coins = getSharedPreferences("Rewards", MODE_PRIVATE);
        coins2 = (TextView) findViewById(R.id.textViewCoins);
       /* mRef = database.getReference("Users").child(userId).child("Email");
        mRef.child("Email").setValue(user.getEmail());*/

        //
        mRef = database.getReference("Users").child(userId).child("Coins");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentCoins=(dataSnapshot.getValue(String.class));
                SharedPreferences.Editor coinsEdit = coins.edit();
                coinsEdit.putString("Coins", String.valueOf(currentCoins));
                coinsEdit.apply();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //
       // coins = getSharedPreferences("Rewards", MODE_PRIVATE);
       // currentCoins = coins.getString("Coins", "0");
        ImageView settingbtn = (ImageView) findViewById(R.id.imageView9);
        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentActivity(v);
            }
        });
        coins2 = (TextView) findViewById(R.id.textViewCoins);
        coins2.setText(currentCoins);
        //


        CardView cardsmallads = findViewById(R.id.smallads);
        cardsmallads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Toast.makeText(ChoiceSelection.this, "The Ads wasn't loaded yet. Try again later", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "The interstitial wasn't loaded yet.");}
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                int coinCount = Integer.parseInt(coins.getString("Coins", "0"));
                coinCount = coinCount + 20;
                SharedPreferences.Editor coinsEdit = coins.edit();
                coinsEdit.putString("Coins", String.valueOf(coinCount));
                coinsEdit.apply();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String userId = user.getUid();
                mRef = database.getReference().child("Users").child(userId);
                mRef.child("Coins").setValue(coinCount);
            }
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        final Handler handler1 = new Handler();
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                int coinCount = Integer.parseInt(coins.getString("Coins", "0"));
                coins2.setText(String.valueOf(coinCount));
                Log.d("Handlers", "Called on main thread");
                handler1.postDelayed(this, 2000);
            }
        };
        handler.post(runnableCode);
    }
    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(SettingsActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(SettingsActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
    public void startVideo(View view) {
        if(mRewardedVideoAd.isLoaded()){
            mRewardedVideoAd.show();
        }  else {
            Toast.makeText(ChoiceSelection.this, "The Video wasn't loaded yet. Try again later", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "The mRewardedVideoAd wasn't loaded yet.");}

    }
    public void instruction(View view) {
        Intent openInstructions = new Intent(getApplicationContext(), Instructions.class);
        startActivity(openInstructions);
    }
    public void redeem(View view) {
        Intent openRedeem = new Intent(getApplicationContext(), Redeem.class);
        startActivity(openRedeem);
    }
    public void aboutus(View view) {
        String url = "https://www.google.com/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void contact(View view) {
        Intent contact = new Intent(Intent.ACTION_SENDTO);
        contact.setData(Uri.parse("mailto:nomanaziz518@gmail.com"));
        startActivity(contact);
    }
    public void dailyCheck(View view) {
        Intent openDailyChecks = new Intent(getApplicationContext(), DailyCheckins.class);
        startActivity(openDailyChecks);
    }
    public void luckyWheel(View view) {
        Intent openLuckyWheel = new Intent(getApplicationContext(), LuckyWheel.class);
        startActivity(openLuckyWheel);
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
    @Override
    public void onRewarded(RewardItem reward) {
        int coinCount = Integer.parseInt(coins.getString("Coins", "0"));
        coinCount = coinCount + 50;
        SharedPreferences.Editor coinsEdit = coins.edit();
        coinsEdit.putString("Coins", String.valueOf(coinCount));
        coinsEdit.apply();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        mRef = database.getReference().child("Users").child(userId);
        mRef.child("Coins").setValue(coinCount);
    }
    private void loadRewardedVideoAd() {
        if (!mRewardedVideoAd.isLoaded()){
            mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
        }
    }
    @Override
    public void onRewardedVideoAdLeftApplication() {}
    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }
    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {}
    @Override
    public void onRewardedVideoAdLoaded() {}
    @Override
    public void onRewardedVideoAdOpened() {}
    @Override
    public void onRewardedVideoStarted() {}
    @Override
    public void onRewardedVideoCompleted() {
        loadRewardedVideoAd();
    }
    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();

    }
    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }
    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
