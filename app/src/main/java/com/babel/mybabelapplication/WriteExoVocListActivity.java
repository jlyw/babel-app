package com.babel.mybabelapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.babel.mybabelapplication.dao.VocDAO;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.Voc;
import com.babel.mybabelapplication.model.VocList;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class WriteExoVocListActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private String listVocId;
    private VocListDAO vocListDAO;
    private VocDAO vocDAO;
    private VocList vocList;
    private Integer index;
    private List<Voc> vocs;
    private int[] listOfIndex;
    private boolean isFrench;
    private Intent intent;
    private int[] listOfSuccess;
    private Voc voc;

    @BindView(R.id.text_view_to_trad)
    protected TextView textViewToTrad;

    @BindView(R.id.text_view_result)
    protected TextView textViewResult;

    @BindView(R.id.text_view_indexing)
    protected TextView textViewIndexing;

    @BindView(R.id.edit_text_answer)
    protected EditText textEditToTrad;

    @BindView(R.id.valid_voc_exo_button)
    protected Button buttonValidAnswer;

    @BindView(R.id.next_voc_exo_button)
    protected Button buttonNextExo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_exo_voc_list);

        Realm.init(this);
        ButterKnife.bind(this);

        // Show  toolbar and title
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        isFrench = Math.random() < 0.5;

        vocListDAO = new VocListDAO();
        vocDAO = new VocDAO();

        listVocId = getIntent().getStringExtra("LIST_VOC_ID");
        index = getIntent().getIntExtra("INDEX", 0);
        listOfIndex = getIntent().getIntArrayExtra("RANDOM_INDEXING");
        listOfSuccess = getIntent().getIntArrayExtra("SUCCESS_LIST");

        vocList = vocListDAO.getVocList(listVocId);
        vocs = vocDAO.getAllVocsOffOneList(listVocId);

        toolbar.setTitle(vocList.getName());

        textViewToTrad.setText(isFrench ? vocs.get(listOfIndex[index]).getFrench() : vocs.get(listOfIndex[index]).getEnglish());
        /*textViewIndexing.setText(String.valueOf(index + 1) + " sur " + vocs.size() + "       " +
                String.valueOf(listOfSuccess[0]) + " " + String.valueOf(listOfSuccess[1]) + " " + String.valueOf(listOfSuccess[2]) +
                "       " + String.valueOf(vocs.get(listOfIndex[index]).getGrade())
        );*/
    }

    @OnClick(R.id.valid_voc_exo_button)
    public void validVocWriteExo() {
        String answer = textEditToTrad.getText().toString().toLowerCase();
        if(answer.isEmpty()) {
            showAlert(R.string.title_warning_exo_voc, R.string.message_warning_exo_voc);
        } else {
            voc = vocDAO.getVoc(vocs.get(listOfIndex[index]).getId());
            buttonValidAnswer.setVisibility(View.GONE);
            buttonNextExo.setVisibility(View.VISIBLE);

            if(isFrench) {
                if(Objects.equals(vocs.get(listOfIndex[index]).getEnglish().toLowerCase(), answer)) {
                    textViewResult.setText("Bien joué !");
                    listOfSuccess[index] = 1;
                    vocDAO.upVocGrade(voc);
                } else {
                    textViewResult.setText("Dommage ! La bonne réponse était " + vocs.get(listOfIndex[index]).getEnglish());
                    listOfSuccess[index] = 0;
                    vocDAO.downVocGrade(voc);
                }
            } else {
                if(Objects.equals(vocs.get(listOfIndex[index]).getFrench().toLowerCase(), answer)) {
                    textViewResult.setText("Bien joué !");
                    listOfSuccess[index] = 1;
                    vocDAO.upVocGrade(voc);
                } else {
                    textViewResult.setText("Dommage ! La bonne réponse était " + vocs.get(listOfIndex[index]).getFrench());
                    listOfSuccess[index] = 0;
                    vocDAO.downVocGrade(voc);
                }
            }
        }
    }

    @OnClick(R.id.next_voc_exo_button)
    public void nextVocExo() {
        if(index+1 == vocs.size()) {
            intent = new Intent(getApplicationContext(), ResultExoActivity.class);

            intent.putExtra("LIST_VOC_ID", listVocId);
            intent.putExtra("RANDOM_INDEXING", listOfIndex);
            intent.putExtra("SUCCESS_LIST", listOfSuccess);
            intent.putExtra("EXO_TYPE", "voc");

            startActivity(intent);
        } else {
            intent = new Intent(getApplicationContext(), WriteExoVocListActivity.class);

            intent.putExtra("LIST_VOC_ID", listVocId);
            intent.putExtra("RANDOM_INDEXING", listOfIndex);
            intent.putExtra("SUCCESS_LIST", listOfSuccess);
            intent.putExtra("INDEX", index+1);

            startActivity(intent);
        }
    }

    private void showAlert(int title, int message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();

        alertDialog.show();
    }
}
