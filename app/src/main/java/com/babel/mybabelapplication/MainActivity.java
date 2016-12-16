package com.babel.mybabelapplication;

import android.content.Intent;
import android.os.Bundle;

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

import com.babel.mybabelapplication.dao.UserDAO;
import com.babel.mybabelapplication.dao.VerbDAO;
import com.babel.mybabelapplication.dao.VerbListDAO;
import com.babel.mybabelapplication.dao.VocDAO;
import com.babel.mybabelapplication.dao.VocListDAO;
import com.babel.mybabelapplication.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends ActionBarActivity {
    Intent intent;
    private VerbDAO verbDao;
    private VerbListDAO verbListDao;
    private VocDAO vocDAO;
    private VocListDAO vocListDao;
    private UserDAO userDao;
    private User user;

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
        // Disabled back button_rectangle on activity_main
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
        );;

        // Create a user if no user present
        userDao = new UserDAO();
        if(!userDao.userIsPresentInRealm()) {
            user = new User();
            user.setId("USER_ID");
            user.setAllAnswer(0);
            user.setGoodAnswer(0);
            user.setExerciceDone(0);
            user.setCreatedList(0);

            userDao.addUser(user);

            intent = new Intent(getApplicationContext(), OnboardingActivity.class);
            startActivity(intent);
        }

        verbDao = new VerbDAO();
        verbListDao = new VerbListDAO();
        vocDAO = new VocDAO();
        vocListDao = new VocListDAO();
    }

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
