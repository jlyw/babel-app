package com.babel.mybabelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.babel.mybabelapplication.dao.UserDAO;
import com.babel.mybabelapplication.dao.VerbDAO;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.dao.VocDAO;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.User;
import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.Voc;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends ActionBarActivity {
    Intent intent;
    private Toolbar toolbar;

    @BindView(R.id.text_view_profile)
    protected TextView textViewProfile;
    private VerbDAO verbDao;
    private VerbListDAO verbListDao;
    private VocDAO vocDAO;
    private VocListDAO vocListDao;
    private List<Voc> allVocs;
    private List<Verb> allVerbs;
    private UserDAO userDAO;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        verbDao = new VerbDAO();
        verbListDao = new VerbListDAO();
        vocDAO = new VocDAO();
        vocListDao = new VocListDAO();
        userDAO = new UserDAO();

        user = userDAO.getUser();
        int percentGoodAnswer;
        if (user.getAllAnswer() != 0) {
            percentGoodAnswer = (user.getGoodAnswer() * 100) / user.getAllAnswer();
        } else {
            percentGoodAnswer = 0;
        }

        allVocs = vocDAO.getAllVocs();
        allVerbs = verbDao.getAllVerbs();
        int masteringVoc = 0;
        for( Voc item : allVocs) {
            if(item.getGrade() == 2)
                masteringVoc ++;
        }
        int masteringVerb = 0;
        for( Verb item : allVerbs) {
            if(item.getGrade() == 2)
                masteringVerb ++;
        }

        int masteringWord = masteringVoc + masteringVerb;

        int percentMasteringVoc;
        if (allVocs.size() != 0) {
            percentMasteringVoc = (masteringVoc * 100) / allVocs.size();
        } else {
            percentMasteringVoc = 0;
        }
        int percentMasteringVerb;
        if (allVerbs.size() != 0) {
            percentMasteringVerb = (masteringVerb * 100) / allVerbs.size();
        } else {
            percentMasteringVerb = 0;
        }

        textViewProfile.setText(
            masteringWord + " mots maitrisés" + " --- " +
            percentMasteringVoc + "% de mots de vocabulaire maîtrisé" + " --- " +
            percentMasteringVerb + "% de verbes irréguliers maîtrisé" + " --- " +
            user.getCreatedList() + " listes créées" + " --- " +
            user.getExerciceDone() + " exercices faits" + " --- " +
            percentGoodAnswer + "% de bonnes réponses"
        );
    }
}
