package com.babel.mybabelapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public class CreateListActivity extends ActionBarActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

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
    }
}
