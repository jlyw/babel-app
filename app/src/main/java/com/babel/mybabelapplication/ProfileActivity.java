package com.babel.mybabelapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.babel.mybabelapplication.dao.UserDAO;
import com.babel.mybabelapplication.dao.VerbDAO;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.dao.VocDAO;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.User;
import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.Voc;
import com.eralp.circleprogressview.CircleProgressView;
import com.eralp.circleprogressview.ProgressAnimationListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends ActionBarActivity {
    Intent intent;
    private Toolbar toolbar;

    private VerbDAO verbDao;
    private VerbListDAO verbListDao;
    private VocDAO vocDAO;
    private VocListDAO vocListDao;
    private List<Voc> allVocs;
    private List<Verb> allVerbs;
    private UserDAO userDAO;
    private User user;

    private CircleProgressView circleGoodAnswers;

    @BindView(R.id.page_title)
    protected TextView pageTitle;
    private CircleProgressView circleMasteringVoc;
    private CircleProgressView circleMasteringVerbs;
    private CircleProgressView circleExoDone;
    private CircleProgressView circleCreatedLists;
    private CircleProgressView circleMasteredVoc;
    private CircleProgressView circleMasteredVerbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        // Fonts
        Typeface overlockBold = Typeface.createFromAsset(getAssets(),"fonts/OverlockBold.ttf");

        // Define fonts for elements
        pageTitle.setTypeface(overlockBold);

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

        // Good answers percent
        circleGoodAnswers = (CircleProgressView) findViewById(R.id.circle_good_answers);
        circleGoodAnswers.setTextEnabled(true);
        circleGoodAnswers.setInterpolator(new AccelerateDecelerateInterpolator());
        circleGoodAnswers.setStartAngle(45);
        circleGoodAnswers.setProgressWithAnimation(percentGoodAnswer, 2000);

        // Mastering voc percent
        circleMasteringVoc = (CircleProgressView) findViewById(R.id.circle_mastered_voc);
        circleMasteringVoc.setTextEnabled(true);
        circleMasteringVoc.setInterpolator(new AccelerateDecelerateInterpolator());
        circleMasteringVoc.setStartAngle(45);
        circleMasteringVoc.setProgressWithAnimation(percentMasteringVoc, 2000);

        // Mastering verbs percent
        circleMasteringVerbs = (CircleProgressView) findViewById(R.id.circle_mastered_verbs);
        circleMasteringVerbs.setTextEnabled(true);
        circleMasteringVerbs.setInterpolator(new AccelerateDecelerateInterpolator());
        circleMasteringVerbs.setStartAngle(45);
        circleMasteringVerbs.setProgressWithAnimation(percentMasteringVerb, 2000);

        // Exo done
        circleExoDone = (CircleProgressView) findViewById(R.id.circle_exo_done);
        circleExoDone.setTextEnabled(true);
        circleExoDone.setProgressWithAnimation(user.getExerciceDone(), 2000);

        // Created lists
        circleCreatedLists = (CircleProgressView) findViewById(R.id.circle_created_lists);
        circleCreatedLists.setTextEnabled(true);
        circleCreatedLists.setProgressWithAnimation(user.getCreatedList(), 2000);

        // Mastered voc
        circleMasteredVoc = (CircleProgressView) findViewById(R.id.circle_mastered_voc_nb);
        circleMasteredVoc.setTextEnabled(true);
        circleMasteredVoc.setProgressWithAnimation(masteringVoc, 2000);

        // Mastered verbs
        circleMasteredVerbs = (CircleProgressView) findViewById(R.id.circle_mastered_verb_nb);
        circleMasteredVerbs.setTextEnabled(true);
        circleMasteredVerbs.setProgressWithAnimation(masteringVerb, 2000);
    }
}
