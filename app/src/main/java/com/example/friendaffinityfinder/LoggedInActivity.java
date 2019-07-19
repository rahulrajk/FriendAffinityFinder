package com.example.friendaffinityfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoggedInActivity extends AppCompatActivity {

    TwitterLoginButton twitterLoginButton;
    SharedPreferences sharedPreferences;
    MainActivity mainActivity;
    TextView name_t,gender_t,birthday_t,email_t,location_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialising twitter login
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.consumer_key), getResources().getString(R.string.consumer_secret_key)))//pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true)//enable debug mode
                .build();

        //finally initialize twitter with created configs
        Twitter.initialize(config);
        setContentView(R.layout.activity_logged_in);

        sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid","");
        String username = sharedPreferences.getString("username","");
        String gender = sharedPreferences.getString("gender","");
        String birthday = sharedPreferences.getString("birthday","");
        String location = sharedPreferences.getString("city","");
        String email = sharedPreferences.getString("email","");

        name_t = findViewById(R.id.name_txt);
        gender_t = findViewById(R.id.gender_txt);
        birthday_t = findViewById(R.id.birthday_txt);
        location_t = findViewById(R.id.location_txt);
        email_t = findViewById(R.id.email_txt);

        name_t.setText(username);
        gender_t.setText(gender);
        birthday_t.setText(birthday);
        email_t.setText(email);
        location_t.setText(location);

        //ProfilePictureView


        //http://graph.facebook.com/" + facebookId + "/picture?type=square

        ProfilePictureView profilePictureView;

        profilePictureView =  findViewById(R.id.friendProfilePicture);

        profilePictureView.setProfileId(userid);

        //Twitter button
            twitterLoginButton =  findViewById(R.id.default_twitter_login_button);
            twitterLoginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    // Do something with result, which provides a TwitterSession for making API calls
                    Log.d("twittersuccess","twittersuccess");
                    Log.d("twitterresponse",result.data.getUserName());

                }

                @Override
                public void failure(TwitterException exception) {
                    // Do something on failure
                    Log.d("twitterfailure",exception.toString());

                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
