package com.example.friendaffinityfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class HomeActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener,FriendsFragment.OnFragmentInteractionListener,SettingsFragment.OnFragmentInteractionListener{

    BottomNavigationView navigation;
    Toolbar toolbar;
    TextView title;
    TwitterLoginButton twitterLoginButton;
    String check="profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        twitterLoginButton =  findViewById(R.id.default_twitter_login_button);


            toolbar = findViewById(R.id.toolbar1);
        title = findViewById(R.id.titletoolbar);
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:

                        if (!check.equals("profile")){
                            check="profile";
                            toolbar.setVisibility(View.GONE);
                            title.setText("Home");
                            loadFragment(new ProfileFragment());
                        }

                        return true;
                    case R.id.friends:
                        if (!check.equals("friends")){
                            check="friends";
                            title.setText("Friends");
                            toolbar.setVisibility(View.VISIBLE);
                            loadFragment(new FriendsFragment());
                        }
                        return true;
                    case R.id.settings:
                        if (!check.equals("settings")){
                            check="settings";
                            toolbar.setVisibility(View.VISIBLE);
                            title.setText("Settings");
                            loadFragment(new SettingsFragment());
                        }

                        return true;
                }

                return false;
            }
        };
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = new ProfileFragment();
        toolbar.setVisibility(View.GONE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, fragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }





    private void accessPermission(){

    }
}

