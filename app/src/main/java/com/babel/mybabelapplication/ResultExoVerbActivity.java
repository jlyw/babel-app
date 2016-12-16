package com.babel.mybabelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.babel.mybabelapplication.dao.VerbDAO;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.VerbList;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class ResultExoVerbActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @BindView(R.id.text_view_comment)
    protected TextView textViewComment;

    @BindView(R.id.text_view_percent_response)
    protected TextView textViewPercentResponse;

    @BindView(R.id.button_back_to_lists)
    protected Button buttonBackToLists;

    @BindView(R.id.result_background)
    protected ImageView resultBackground;

    @BindView(R.id.button_retry_exo)
    protected Button buttonRetryExo;
    private VerbListDAO verbListDAO;
    private String listVerbId;
    private String exoType;
    private int[] listOfIndex;
    private int[] listOfSuccess;
    private VerbList verbList;
    private int totalPoint;
    private int resultPercent;
    private Intent intent;
    private VerbDAO verbDAO;
    private List<Verb> verbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_exo_verb);

        Realm.init(this);
        ButterKnife.bind(this);

        // Show  toolbar and title
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        verbListDAO = new VerbListDAO();
        verbDAO = new VerbDAO();

        listVerbId = getIntent().getStringExtra("LIST_VOC_ID");
        exoType = getIntent().getStringExtra("EXO_TYPE");
        listOfIndex = getIntent().getIntArrayExtra("RANDOM_INDEXING");
        listOfSuccess = getIntent().getIntArrayExtra("SUCCESS_LIST");

        verbList = verbListDAO.getVerbList(listVerbId);
        verbs = verbDAO.getAllVerbsOffOneList(listVerbId);

        toolbar.setTitle("Résultats - " + verbList.getName());

        totalPoint = 0;
        for( int i : listOfSuccess) {
            totalPoint += i;
        }
        resultPercent = ((totalPoint * 100) / (listOfIndex.length * 3));

        toolbar.setTitle(verbList.getName());
        if(resultPercent <= 33) {
            textViewComment.setText("Dommage, recommence !");
            resultBackground.setImageResource(R.drawable.bg_result_0);
        } else if (resultPercent <= 66) {
            textViewComment.setText("Pas mal !");
            resultBackground.setImageResource(R.drawable.bg_result_1);
        } else if (resultPercent == 100) {
            textViewComment.setText("Parfait !");
            resultBackground.setImageResource(R.drawable.bg_result_2);
        } else {
            textViewComment.setText("Bravo !");
            resultBackground.setImageResource(R.drawable.bg_result_2);
        }
        resultBackground.setScaleType(ImageView.ScaleType.FIT_XY);

        textViewPercentResponse.setText(resultPercent + "%");
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(getApplicationContext(), ShowVerbListActivity.class);
        intent.putExtra("LIST_VOC_ID", listVerbId);
        startActivity(intent);
    }

    @OnClick(R.id.button_back_to_lists)
    public void backToLists() {
        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_retry_exo)
    public void RetryExo() {
        intent = new Intent(getApplicationContext(), WriteExoVerbListActivity.class);

        int[] listOfIndex = new int[verbs.size()];
        for (int i = 0; i < verbs.size(); ++i) {
            listOfIndex[i] = i;
        }
        int index, temp;
        Random random = new Random();
        for (int i = listOfIndex.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = listOfIndex[index];
            listOfIndex[index] = listOfIndex[i];
            listOfIndex[i] = temp;
        }

        int[] listOfSuccess = new int[verbs.size()];
        for (int i = 0; i < verbs.size(); ++i) {
            listOfSuccess[i] = -1;
        }

        intent.putExtra("LIST_VOC_ID", listVerbId);

        intent.putExtra("RANDOM_INDEXING", listOfIndex);
        intent.putExtra("SUCCESS_LIST", listOfSuccess);

        intent.putExtra("INDEX", 0);
        startActivity(intent);
    }
}
