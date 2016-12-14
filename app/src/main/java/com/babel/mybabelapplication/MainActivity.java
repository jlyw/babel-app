package com.babel.mybabelapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.babel.mybabelapplication.dao.VerbDAO;
import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.network.JsonTaskVerbSingle;
import com.babel.mybabelapplication.network.UrlBuilder;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends Activity {

    Verb verb1;
    Verb verb2;
    private VerbDAO verbDao;

    @BindView(R.id.test_text_view)
    protected TextView testTextView;

    @BindView(R.id.test_text_view2)
    protected TextView testTextView2;

    @BindView(R.id.test_text_view3)
    protected TextView testTextView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        ButterKnife.bind(this);

        verb1 = new Verb();
        verb1.setFrench("avoir");
        verb1.setId(UUID.randomUUID().toString());
        new JsonTaskVerbSingle(this, verb1).execute(UrlBuilder.getVerbUrl("have"));

        /* HANDLER for set number of adverts */
        int interval = 400;
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);

        verbDao = new VerbDAO();
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            testTextView.setText(verb1.getInfinitive() + " " + verb1.getSimplePast() + " " + verb1.getPastParticiple());
            verbDao.addVerb(verb1);

            verb2 = verbDao.getVerb(verb1.getId());
            assert verb2 != null;
            testTextView2.setText(verb2.getFrench() + verb1.getId());

            List<Verb> allVerbs = verbDao.getAllVerbs();
            testTextView3.setText(allVerbs.toString());
        }
    };
}
