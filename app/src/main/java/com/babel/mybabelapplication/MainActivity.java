package com.babel.mybabelapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.network.JsonTaskVerbSingle;
import com.babel.mybabelapplication.network.UrlBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    Verb verb1;

    @BindView(R.id.test_text_view)
    protected TextView testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        verb1 = new Verb();
        new JsonTaskVerbSingle(this, verb1).execute(UrlBuilder.getVerbUrl("be"));

        /* HANDLER for set number of adverts */
        int interval = 400;
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            testTextView.setText(verb1.getInfinitive() + " " + verb1.getSimplePast() + " " + verb1.getPastParticiple());
        }
    };
}
