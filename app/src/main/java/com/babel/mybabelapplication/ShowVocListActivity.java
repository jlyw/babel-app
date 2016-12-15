package com.babel.mybabelapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.babel.mybabelapplication.adapter.VocAdapter;
import com.babel.mybabelapplication.dao.VocDAO;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.Voc;
import com.babel.mybabelapplication.model.VocList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class ShowVocListActivity extends ActionBarActivity {
    Intent intent;
    private Toolbar toolbar;
    private String listVocId;
    private VocAdapter vocAdapter;

    private VocList vocList;
    private VocListDAO vocListDAO;
    private List<Voc> vocs;
    private VocDAO vocDAO;

    @BindView(R.id.list_view_voc)
    protected ListView listViewVoc;

    @BindView(R.id.english_text_list_voc)
    protected TextView englishTextListVoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_voc_list);

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
        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
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

        vocListDAO = new VocListDAO();
        vocDAO = new VocDAO();

        listVocId = getIntent().getStringExtra("LIST_VOC_ID");
        vocList = vocListDAO.getVocList(listVocId);
        toolbar.setTitle(vocList.getName());

        vocs = vocDAO.getAllVocsOffOneList(listVocId);

        vocAdapter = new VocAdapter(this);
        listViewVoc.setAdapter(vocAdapter);

        vocAdapter.refresh(vocs);
    }

    @OnClick(R.id.write_exo_voc_list_button)
    public void onStartListVocWriteExo() {
//        vocs = vocDAO.randomAllVocsOffOneList(vocs);

        intent = new Intent(getApplicationContext(), WriteExoVocListActivity.class);

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
//        englishTextListVoc.setText(String.valueOf(listOfIndex[0]));

        intent.putExtra("LIST_VOC_ID", listVocId);

//        intent.putExtra("ALL_VOC", (Serializable) vocs);
        intent.putExtra("RANDOM_INDEXING", listOfIndex);
        intent.putExtra("SUCCESS_LIST", listOfSuccess);

        intent.putExtra("INDEX", 0);
        startActivity(intent);
    }
}
