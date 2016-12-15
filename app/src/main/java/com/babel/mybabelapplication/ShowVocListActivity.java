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

import com.babel.mybabelapplication.adapter.VocAdapter;
import com.babel.mybabelapplication.dao.VocDAO;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.Voc;
import com.babel.mybabelapplication.model.VocList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
}
