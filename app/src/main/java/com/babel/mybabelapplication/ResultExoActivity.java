package com.babel.mybabelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.babel.mybabelapplication.dao.VocDAO;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.Voc;
import com.babel.mybabelapplication.model.VocList;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private Intent intent;
    private VocDAO vocDAO;
    private List<Voc> vocs;

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
        vocDAO = new VocDAO();

        listVocId = getIntent().getStringExtra("LIST_VOC_ID");
        exoType = getIntent().getStringExtra("EXO_TYPE");
        listOfIndex = getIntent().getIntArrayExtra("RANDOM_INDEXING");
        listOfSuccess = getIntent().getIntArrayExtra("SUCCESS_LIST");

        vocList = vocListDAO.getVocList(listVocId);
        vocs = vocDAO.getAllVocsOffOneList(listVocId);

        toolbar.setTitle("Résultats - " + vocList.getName());

        totalPoint = 0;
        for( int i : listOfSuccess) {
            totalPoint += i;
        }
        resultPercent = ((totalPoint * 100) / listOfIndex.length);

        toolbar.setTitle(vocList.getName());
        if(resultPercent <= 33) {
            textViewComment.setText("Dommage, recommence !");
        } else if (resultPercent <= 66) {
            textViewComment.setText("Pas mal !");
        } else if (resultPercent == 100) {
            textViewComment.setText("Parfait !");
        } else {
            textViewComment.setText("Bravo !");
        }

        textViewPercentResponse.setText(resultPercent + "% de bonnes réponses");
    }

    @OnClick(R.id.button_back_to_lists)
    public void backToLists() {
        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_retry_exo)
    public void RetryExo() {
        if(Objects.equals(exoType, "VOC_WRITE") || Objects.equals(exoType, "VOC_SOUND")) {
            if(Objects.equals(exoType, "VOC_WRITE")) {
                intent = new Intent(getApplicationContext(), WriteExoVocListActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), SoundExoVocListActivity.class);
            }

            int[] listOfIndex = new int[vocs.size()];
            for (int i = 0; i < vocs.size(); ++i) {
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

            int[] listOfSuccess = new int[vocs.size()];
            for (int i = 0; i < vocs.size(); ++i) {
                listOfSuccess[i] = -1;
            }

            intent.putExtra("LIST_VOC_ID", listVocId);

            intent.putExtra("RANDOM_INDEXING", listOfIndex);
            intent.putExtra("SUCCESS_LIST", listOfSuccess);

            intent.putExtra("INDEX", 0);
            startActivity(intent);
        } else {
            /*intent = new Intent(getApplicationContext(), WriteExoVocListActivity.class);

            int[] listOfIndex = new int[vocs.size()];
            for (int i = 0; i < vocs.size(); ++i) {
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

            int[] listOfSuccess = new int[vocs.size()];
            for (int i = 0; i < vocs.size(); ++i) {
                listOfSuccess[i] = -1;
            }

            intent.putExtra("LIST_VOC_ID", listVocId);

            intent.putExtra("RANDOM_INDEXING", listOfIndex);
            intent.putExtra("SUCCESS_LIST", listOfSuccess);

            intent.putExtra("INDEX", 0);
            startActivity(intent);*/
        }

    }
}
