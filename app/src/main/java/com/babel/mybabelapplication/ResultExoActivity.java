package com.babel.mybabelapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.VocList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class ResultExoActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @BindView(R.id.text_view_comment)
    protected TextView textViewComment;

    @BindView(R.id.text_view_percent_response)
    protected TextView textViewPercentResponse;

    @BindView(R.id.button_back_to_lists)
    protected Button buttonBackToLists;

    @BindView(R.id.button_retry_exo)
    protected Button buttonRetryExo;
    private VocListDAO vocListDAO;
    private String listVocId;
    private String exoType;
    private int[] listOfIndex;
    private int[] listOfSuccess;
    private VocList vocList;
    private int totalPoint;
    private int resultPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_exo);

        Realm.init(this);
        ButterKnife.bind(this);

        // Show  toolbar and title
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        vocListDAO = new VocListDAO();
        listVocId = getIntent().getStringExtra("LIST_VOC_ID");
        exoType = getIntent().getStringExtra("EXO_TYPE");
        listOfIndex = getIntent().getIntArrayExtra("RANDOM_INDEXING");
        listOfSuccess = getIntent().getIntArrayExtra("SUCCESS_LIST");

        vocList = vocListDAO.getVocList(listVocId);
        totalPoint = 0;
        for( int i : listOfSuccess) {
            totalPoint += i;
        }
        resultPercent = ((totalPoint * 100) / listOfIndex.length);

        toolbar.setTitle(vocList.getName());
        if(resultPercent < 33) {
            textViewComment.setText("Dommage, recommence !");
        } else if (resultPercent < 66) {
            textViewComment.setText("Pas mal !");
        } else if (resultPercent == 100) {
            textViewComment.setText("Parfait !");
        } else {
            textViewComment.setText("Bravo !");
        }

        textViewPercentResponse.setText(resultPercent + "% de bonnes réponses");
    }
}
