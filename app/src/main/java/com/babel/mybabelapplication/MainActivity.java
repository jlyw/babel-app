package com.babel.mybabelapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.babel.mybabelapplication.dao.VerbDAO;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.VerbList;
import com.babel.mybabelapplication.network.JsonTaskVerbSingle;
import com.babel.mybabelapplication.network.UrlBuilder;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends Activity {

    Verb verb1;
    Verb verb2;
    private VerbDAO verbDao;
    private VerbListDAO verbListDao;

    @BindView(R.id.test_text_view)
    protected TextView testTextView;

    @BindView(R.id.test_text_view2)
    protected TextView testTextView2;

    @BindView(R.id.test_text_view3)
    protected TextView testTextView3;

    @BindView(R.id.test_text_view4)
    protected TextView testTextView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        ButterKnife.bind(this);

        verb1 = new Verb();
        verb1.setFrench("etre");
        verb1.setId(UUID.randomUUID().toString());
        new JsonTaskVerbSingle(this, verb1).execute(UrlBuilder.getVerbUrl("be"));

        /* HANDLER for set number of adverts */
        int interval = 400;
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);

        verbDao = new VerbDAO();
        verbListDao = new VerbListDAO();
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            testTextView.setText(verb1.getInfinitive() + " " + verb1.getSimplePast() + " " + verb1.getPastParticiple());
            verbDao.addVerb(verb1);

            verb2 = verbDao.getVerb("7ed22ae7-5161-421e-99b9-45364f043375");
            assert verb2 != null;
            testTextView2.setText(verb2.getFrench() + " " + verb2.getInfinitive() + " " + verb2.getId());

            verbDao.updateVerbInfinitive(verb2, "Merci le Droide ? ._.");
            testTextView4.setText(verb2.getInfinitive());

            /*VerbList verbList1 = new VerbList();
            verbList1.setName("dat_list2");
            verbList1.setId(UUID.randomUUID().toString());
            verbListDao.addVerbList(verbList1);*/

//            verbDao.addVerbToVerbListId(verb1, verbList1.getId());
            /*
            List<VerbList> allVerbLists = verbListDao.getAllVerbLists();
            Verb verb3 = (Verb) allVerbLists.get(1).getVerbs().where().findAll().get(0);
            testTextView3.setText(verb3.getInfinitive());*/
        }
    };
}
