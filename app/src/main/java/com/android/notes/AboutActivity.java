package com.android.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView tv1a,tv1b,tv2a,tv2b,tv3a,tv3b,tv4a,tv4b;
        tv1a = findViewById(R.id.textView2a);
        tv1b = findViewById(R.id.textView2b);
        tv2a = findViewById(R.id.textView3a);
        tv2b = findViewById(R.id.textView3b);
        tv3a = findViewById(R.id.textView4a);
        tv3b = findViewById(R.id.textView4b);
        tv4a = findViewById(R.id.textView5a);
        tv4b = findViewById(R.id.textView5b);

        tv1a.setAlpha(0);
        tv1b.setAlpha(0);
        tv2a.setAlpha(0);
        tv2b.setAlpha(0);
        tv3a.setAlpha(0);
        tv3b.setAlpha(0);
        tv4a.setAlpha(0);
        tv4b.setAlpha(0);

        tv1a.animate().translationYBy(100).setDuration(500).setStartDelay(500).alphaBy(1).start();
        tv1b.animate().translationYBy(100).setDuration(500).setStartDelay(500).alphaBy(1).start();
        tv2a.animate().translationYBy(100).setDuration(500).setStartDelay(1000).alphaBy(1).start();
        tv2b.animate().translationYBy(100).setDuration(500).setStartDelay(1000).alphaBy(1).start();
        tv3a.animate().translationYBy(100).setDuration(500).setStartDelay(1500).alphaBy(1).start();
        tv3b.animate().translationYBy(100).setDuration(500).setStartDelay(1500).alphaBy(1).start();
        tv4a.animate().translationYBy(100).setDuration(500).setStartDelay(2000).alphaBy(1).start();
        tv4b.animate().translationYBy(100).setDuration(500).setStartDelay(2000).alphaBy(1).start();

    }
}
