package com.babel.mybabelapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.babel.mybabelapplication.dao.UserDAO;
import com.babel.mybabelapplication.dao.VocDAO;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.Voc;
import com.babel.mybabelapplication.model.VocList;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class CreateListActivity extends ActionBarActivity {
    Intent intent;
    private Toolbar toolbar;
    private VocList vocList;
    private VocListDAO vocListDAO;
    private Voc voc;
    private VocDAO vocDAO;
    private ArrayList<Voc> vocs;
    private ArrayList<String> vocsInString;
    private ArrayAdapter<String> vocsAdapter;

    @BindView(R.id.edit_text_voc_list_name)
    protected EditText editTextVocListName;

    @BindView(R.id.edit_text_voc_french)
    protected EditText editTextVocFrench;

    @BindView(R.id.edit_text_voc_english)
    protected EditText editTextVocEnglish;

    @BindView(R.id.list_view_create_voc)
    protected ListView listViewVocs;

    @BindView(R.id.create_voc_list_button)
    protected Button createVocListButton;

    @BindView(R.id.create_voc_button)
    protected Button createVocButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        Realm.init(this);
        ButterKnife.bind(this);

        // Fonts
        Typeface overlockBold = Typeface.createFromAsset(getAssets(),"fonts/OverlockBold.ttf");

        // Define fonts for elements
        createVocListButton.setTypeface(overlockBold);
        createVocButton.setTypeface(overlockBold);

        // Show  toolbar and title
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // Bottom navigation
        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bnve.enableAnimation(true);
        bnve.setTextVisibility(true);
        bnve.setIconVisibility(true);
        bnve.setCurrentItem(1);

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

        vocs = new ArrayList<>();
        vocsInString = new ArrayList<>();
        vocsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vocsInString);
        listViewVocs.setAdapter(vocsAdapter);

        vocDAO = new VocDAO();
        vocList = new VocList();
        vocListDAO = new VocListDAO();
    }

    @OnClick(R.id.create_voc_button)
    public void onCreateVoc() {
        String vocFrench = editTextVocFrench.getText().toString().toLowerCase();
        String vocEnglish = editTextVocEnglish.getText().toString().toLowerCase();

        if(vocFrench.isEmpty() || vocEnglish.isEmpty()) {
            showAlert(R.string.error_title_voc_create, R.string.error_message_voc_create);
        } else {
            vocFrench = vocFrench.substring(0, 1).toUpperCase() + vocFrench.substring(1);
            vocEnglish = vocEnglish.substring(0, 1).toUpperCase() + vocEnglish.substring(1);

            voc = new Voc();
            voc.setFrench(vocFrench);
            voc.setEnglish(vocEnglish);
            voc.setId(UUID.randomUUID().toString());
            voc.setGrade(0);
            vocDAO.addVoc(voc);

            vocs.add(voc);
            vocsAdapter.add(voc.getFrench() + " - " + voc.getEnglish());

            editTextVocFrench.setText("");
            editTextVocEnglish.setText("");
        }
    }

    @OnClick(R.id.create_voc_list_button)
    public void onCreateVocList() {
        String vocListName = editTextVocListName.getText().toString();

        if(vocListName.isEmpty()) {
            showAlert(R.string.error_title_list_name_empty, R.string.error_message_list_name_empty);
        } else {
            vocList.setName(vocListName);
            vocList.setId(UUID.randomUUID().toString());
            vocListDAO.addVocList(vocList);
            UserDAO userDAO = new UserDAO();
            userDAO.upUserCreatedList();

            for (Voc item : vocs) {
                vocDAO.addVocToVocListId(item, vocList.getId());
            }

            intent = new Intent(getApplicationContext(), ShowVocListActivity.class);
            intent.putExtra("LIST_VOC_ID", vocList.getId());
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
