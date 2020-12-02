package com.developer.makemoney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class Instructions extends AppCompatActivity {
    private ViewFlipper viewFlipper;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_instructions);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.toggleSoftInput(1, 0);
        }
        ((ImageView) findViewById(R.id.imageView14)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Instructions.this.onBackPressed();
            }
        });
        this.viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (Instructions.this.viewFlipper.getDisplayedChild() == 4) {
                    Button button = (Button) Instructions.this.findViewById(R.id.play4);
                    button.setText("CLOSE INSTRUCTIONS");
                    button.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                           Instructions.this.startActivity(new Intent(Instructions.this, ChoiceSelection_old.class));
                        }
                    });
                }
                handler.postDelayed(this, 100);
            }
        }, 100);
    }
/*17432579*/
    public void nextView(View view) {
        this.viewFlipper.setInAnimation(this, R.anim.abc_fade_in);
        this.viewFlipper.setOutAnimation(this, R.anim.abc_fade_out);
        this.viewFlipper.showNext();
    }

    public void onBackPressed() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
