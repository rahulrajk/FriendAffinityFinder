package com.example.friendaffinityfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import okhttp3.internal.framed.FramedConnection;

public class HomeActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener{

    BottomNavigationView navigation;
    Toolbar toolbar;
    TextView title;
    ImageView logout;
    SharedPreferences sharedPreferences;
    String check=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar1);
        title = findViewById(R.id.titletoolbar);
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        check="profile";
                            toolbar.setVisibility(View.GONE);
                            title.setText("Home");
                            loadFragment(new ProfileFragment());

                        return true;
                    case R.id.friends:
                        check="friends";
                        toolbar.setVisibility(View.VISIBLE);
                        title.setText("Friends");
//                        loadFragment(new HistoryFragment());
                        return true;
                    case R.id.settings:
                        check="settings";
                        toolbar.setVisibility(View.VISIBLE);
                        title.setText("Settings");
//                        loadFragment(new ProfileFragment());
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
    public void onFragmentInteraction(Uri uri) {

    }


    private void accessPermission(){

    }
}

