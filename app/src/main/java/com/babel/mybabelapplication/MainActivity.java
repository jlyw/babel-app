package com.babel.mybabelapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.babel.mybabelapplication.dao.VerbDAO;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.model.Verb;
import com.babel.mybabelapplication.model.VerbList;
import com.babel.mybabelapplication.network.JsonTaskVerbSingle;
import com.babel.mybabelapplication.network.UrlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends ActionBarActivity {
    Intent intent;
    Verb verb1;
    Verb verb2;
    private VerbDAO verbDao;
    private VerbListDAO verbListDao;

    // Bind elements
    @BindView(R.id.test_text_view)
    protected TextView testTextView;

    @BindView(R.id.test_text_view2)
    protected TextView testTextView2;

    @BindView(R.id.test_text_view3)
    protected TextView testTextView3;

    @BindView(R.id.test_text_view4)
    protected TextView testTextView4;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.viewpager)
    protected ViewPager viewPager;

    @BindView(R.id.tabs)
    protected TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        ButterKnife.bind(this);

        // Show toolbar
        setSupportActionBar(toolbar);
        // Disabled back button on activity_main
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Setup viewPager and tab layout
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

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
                });

        // Get a verb - If remove those lines, remove textView on activity_main.xml
        verb1 = new Verb();
        verb1.setFrench("etre");
        verb1.setId(UUID.randomUUID().toString());
        new JsonTaskVerbSingle(this, verb1).execute(UrlBuilder.getVerbUrl("be"));
        /* HANDLER for set number of adverts */
        int interval = 400;
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);

        verbDao = new VerbDAO();
        verbListDao = new VerbListDAO();
    }

    // Used for the verb1
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            testTextView.setText(verb1.getInfinitive() + " " + verb1.getSimplePast() + " " + verb1.getPastParticiple());
            verbDao.addVerb(verb1);

            verb2 = verbDao.getVerb(verb1.getId());
            assert verb2 != null;
            testTextView2.setText(verb2.getFrench() + " " + verb2.getInfinitive() + " " + verb2.getId());
            /*
            verbDao.updateVerbInfinitive(verb2, "Merci le Droide ? ._.");
            testTextView4.setText(verb2.getInfinitive());
            */
            /*
            VerbList verbList1 = new VerbList();
            verbList1.setName("dat_list2");
            verbList1.setId(UUID.randomUUID().toString());
            verbListDao.addVerbList(verbList1);
            */
            /*
            verbDao.addVerbToVerbListId(verb1, verbList1.getId());
            /*
            List<VerbList> allVerbLists = verbListDao.getAllVerbLists();
            Verb verb3 = (Verb) allVerbLists.get(1).getVerbs().where().findAll().get(0);
            testTextView3.setText(verb3.getInfinitive());
            */
        }
    };

    // Setup for tabs and tabs Fragments
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Add tabs on tab layout
        adapter.addFragment(new VocabulaireFragment(), "Vocabulaire");
        adapter.addFragment(new VerbesIrreguliersFragment(), "Verbes irréguliers");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    } // END setup for tabs and tabs Fragments
}
