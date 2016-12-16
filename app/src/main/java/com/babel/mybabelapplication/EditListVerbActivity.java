package com.babel.mybabelapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.babel.mybabelapplication.dao.VerbDAO;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.VerbList;
import com.babel.mybabelapplication.network.JsonTaskVerbSingle;
import com.babel.mybabelapplication.network.UrlBuilder;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class EditListVerbActivity extends ActionBarActivity {

    @BindView(R.id.edit_text_voc_list_name)
    protected EditText editTextVerbListName;

    @BindView(R.id.edit_text_voc_french)
    protected EditText editTextVerbFrench;

    @BindView(R.id.edit_text_voc_english)
    protected EditText editTextVerbEnglish;

    @BindView(R.id.list_view_create_voc)
    protected ListView listViewVerbs;

    @BindView(R.id.edit_voc_list_button)
    protected Button editVerbListButton;

    @BindView(R.id.create_voc_button)
    protected Button createVerbButton;
    private Toolbar toolbar;
    private Intent intent;
    private String listVerbId;
    private ArrayList<Verb> verbs;
    private ArrayList<String> verbsInString;
    private VerbDAO verbDAO;
    private VerbListDAO verbListDAO;
    private ArrayAdapter<String> verbsAdapter;
    private VerbList verbList;
    private List<Verb> oldVerbs;
    private Verb verb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list_verb);

        Realm.init(this);
        ButterKnife.bind(this);

        // Fonts
        Typeface overlockBold = Typeface.createFromAsset(getAssets(),"fonts/OverlockBold.ttf");

        // Define fonts for elements
        editVerbListButton.setTypeface(overlockBold);
        createVerbButton.setTypeface(overlockBold);

        // Show  toolbar and title
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // Bottom navigation
        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bnve.enableAnimation(true);
        bnve.setTextVisibility(true);
        bnve.setIconVisibility(true);
        bnve.setCurrentItem(2);

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

        listVerbId = getIntent().getStringExtra("LIST_VOC_ID");

        verbs = new ArrayList<>();
        verbsInString = new ArrayList<>();
        verbDAO = new VerbDAO();
        verbListDAO = new VerbListDAO();

        verbsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, verbsInString);
        listViewVerbs.setAdapter(verbsAdapter);

        verbList = verbListDAO.getVerbList(listVerbId);
        oldVerbs = verbDAO.getAllVerbsOffOneList(listVerbId);

        editTextVerbListName.setText(verbList.getName());
        for (Verb item : oldVerbs) {
            verbsAdapter.add(item.getFrench() + " - " + item.getInfinitive() + " - " + item.getSimplePast() + " - " + item.getPastParticiple());
        }
    }

    @OnClick(R.id.create_voc_button)
    public void onCreateVerb() {
        String verbFrench = editTextVerbFrench.getText().toString().toLowerCase();
        String verbEnglish = editTextVerbEnglish.getText().toString().toLowerCase();

        if(verbFrench.isEmpty() || verbEnglish.isEmpty()) {
            showAlert(R.string.error_title_voc_create, R.string.error_message_voc_create);
        } else {
            verbFrench = verbFrench.substring(0, 1).toUpperCase() + verbFrench.substring(1);
            verbEnglish = verbEnglish.substring(0, 1).toUpperCase() + verbEnglish.substring(1);

            verb = new Verb();
            verb.setFrench(verbFrench);
            verb.setInfinitive(verbEnglish);
            verb.setId(UUID.randomUUID().toString());
            verb.setGrade(0);
            // Call to the API to get simplePast & pastParticiple
            new JsonTaskVerbSingle(this, verb).execute(UrlBuilder.getVerbUrl(verbEnglish.toLowerCase()));
            /* HANDLER for set number of adverts */
            int interval = 600;
            handler.postAtTime(runnable, System.currentTimeMillis() + interval);
            handler.postDelayed(runnable, interval);
        }
    }

    // Wait empty data answer back
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {

            if(!verb.getSimplePast().isEmpty() && !verb.getPastParticiple().isEmpty()) {
                verbDAO.addVerb(verb);

                verbs.add(verb);
                verbsAdapter.add(verb.getFrench() + " - " + verb.getInfinitive() + " - " + verb.getSimplePast() + " - " + verb.getPastParticiple());

                editTextVerbFrench.setText("");
                editTextVerbEnglish.setText("");
            }
        }
    };

    @OnClick(R.id.edit_voc_list_button)
    public void onEditVerbList() {
        String verbListName = editTextVerbListName.getText().toString();

        if(verbListName.isEmpty()) {
            showAlert(R.string.error_title_list_name_empty, R.string.error_message_list_name_empty);
        } else {
            verbListDAO.updateVerbListName(verbList, verbListName);

            for (Verb item : verbs) {
                verbDAO.addVerbToVerbListId(item, verbList.getId());
            }

            Intent intent = new Intent(this, ShowVerbListActivity.class);
            intent.putExtra("LIST_VOC_ID", listVerbId);
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
