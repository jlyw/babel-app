package com.babel.mybabelapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.babel.mybabelapplication.adapter.VerbAdapter;
import com.babel.mybabelapplication.dao.VerbDAO;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.VerbList;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class ShowVerbListActivity extends ActionBarActivity {
    Intent intent;
    private Toolbar toolbar;

    @BindView(R.id.list_view_voc)
    protected ListView listViewVerb;

    @BindView(R.id.english_text_list_voc)
    protected TextView englishTextListVerb;
    private TextToSpeech ttobj;

    @BindView(R.id.icon_hide_french_words)
    protected ImageView iconHideFrenchWords;

    @BindView(R.id.icon_hide_english_words)
    protected ImageView iconHideEnglishWords;
    private VerbListDAO verbListDAO;
    private VerbDAO verbDAO;
    private String listVerbId;
    private VerbList verbList;
    private List<Verb> verbs;
    private VerbAdapter verbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_verb_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Realm.init(this);
        ButterKnife.bind(this);

        // Show  toolbar and title
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // Bottom navigation
        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bnve.enableAnimation(true);
        bnve.setTextVisibility(true);
        bnve.setIconVisibility(true);
        bnve.setCurrentItem(0);

        bnve.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_new_list:
                                intent = new Intent(getApplicationContext(), CreateListActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_new_list_verbs:
                                intent = new Intent(getApplicationContext(), CreateListVerbsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_profile:
                                intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                return onNavigationItemSelected(item);
                        }
                        return false;
                    }
                }
        );

        verbListDAO = new VerbListDAO();
        verbDAO = new VerbDAO();

        listVerbId = getIntent().getStringExtra("LIST_VOC_ID");
        verbList = verbListDAO.getVerbList(listVerbId);
        toolbar.setTitle(verbList.getName());

        verbs = verbDAO.getAllVerbsOffOneList(listVerbId);

        ttobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    ttobj.setLanguage(Locale.ENGLISH);
                }
            }
        });

        verbAdapter = new VerbAdapter(this, ttobj);
        listViewVerb.setAdapter(verbAdapter);

        iconHideFrenchWords.setImageResource(R.drawable.hide_words);
        iconHideEnglishWords.setImageResource(R.drawable.hide_words);

        verbAdapter.refresh(verbs);
    }

    @OnClick(R.id.write_exo_voc_list_button)
    public void onStartListVerbWriteExo() {
        if(verbs.size() == 0) {
            return;
        }

        intent = new Intent(getApplicationContext(), WriteExoVerbListActivity.class);

        int[] listOfIndex = new int[verbs.size()];
        for (int i = 0; i < verbs.size(); ++i) {
            listOfIndex[i] = i;
        }
        int index, temp;
        Random random = new Random();
        for (int i = listOfIndex.length - 1; i > 0; i--) {
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

    @OnClick(R.id.delete_voc_list_button)
    public void deleteVocList() {
        showAlertForDelete(R.string.error_title_voc_create, R.string.are_you_sure_delete_list);
    }

    private void showAlertForDelete(int title, int message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        verbListDAO.deleteVerbList(verbList);
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        alertDialog.show();
    }

    @OnClick(R.id.edit_voc_list_button)
    public void editVerbList() {
        intent = new Intent(getApplicationContext(), EditListVerbActivity.class);
        intent.putExtra("LIST_VOC_ID", listVerbId);
        startActivity(intent);
    }
}
