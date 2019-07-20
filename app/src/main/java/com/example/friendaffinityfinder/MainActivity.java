package com.example.friendaffinityfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String EMAIL = "email";
    LoginButton loginButton;
    CallbackManager callbackManager;
    boolean isLoggedIn=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Shared Preferences
        sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //Callback object
        callbackManager = CallbackManager.Factory.create();

        //LoginButton object
        loginButton = findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("user_friends", "user_posts", "email","user_photos","public_profile"));
        //Logged in or not boolean
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
        loginMethod();
//        accessPermission();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("success","success");
                editor.putString("userid", loginResult.getAccessToken().getUserId());
                editor.apply();
                editor.commit();
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                isLoggedIn = accessToken != null && !accessToken.isExpired();
                Log.d("isloggedin", "log->" + isLoggedIn);
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                Log.d("response", object.toString());
                                JSONObject location = null;
                                String city = null;
                                try {
                                    location = object.getJSONObject("location");
                                    city = location.getString("name");
                                    Log.d("city", city);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                try {
                                    editor.putString("username", object.getString("name"));
                                    editor.putString("email", object.getString("email"));
                                    editor.putString("gender", object.getString("gender"));
                                    editor.putString("birthday", object.getString("birthday"));
                                    editor.putString("city", city);
                                    editor.apply();
                                    editor.commit();
                                    Log.d("applyihng", "applying");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,hometown,location");
                request.setParameters(parameters);
                request.executeAsync();
                loginMethod();
            }

            @Override
            public void onCancel() {
                Log.d("fbcancel","fbcancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("fberror","fberror");
            }
        });

//        if (sharedPreferences.contains("userid")&&isLoggedIn){
//            Log.d("contains","userid");
//            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
//            startActivity(intent);
//            finish();
////            String userid = sharedPreferences.getString("userid","");
////            String username = sharedPreferences.getString("username","");
////            String gender = sharedPreferences.getString("gender","");
////            String birthday = sharedPreferences.getString("birthday","");
////            String location = sharedPreferences.getString("city","");
////            String email = sharedPreferences.getString("email","");
////            Log.d("stored",userid+username+gender+birthday+location+email);
//        }else{
//            Toast.makeText(MainActivity.this,"OOPS! Something went wrong!!!Please login again!!!",Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode,  data);
    }

    public void accessPermission() {


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                if (!sharedPreferences.getString("userid", "").equals(loginResult.getAccessToken().getUserId()) || sharedPreferences.getString("userid", "").isEmpty() || !sharedPreferences.contains("userid")) {
                    editor.putString("userid", loginResult.getAccessToken().getUserId());
                    Log.d("userid", loginResult.getAccessToken().getUserId());
                    AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                    Log.d("isloggedin", "log->" + isLoggedIn);
                    GraphRequest request = GraphRequest.newMeRequest(
                            accessToken,
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(
                                        JSONObject object,
                                        GraphResponse response) {
                                    // Application code
                                    Log.d("response", object.toString());
                                    JSONObject location = null;
                                    String city = null;
                                    try {
                                        location = object.getJSONObject("location");
                                        city = location.getString("name");
                                        Log.d("city", city);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    try {
                                        editor.putString("username", object.getString("name"));
                                        editor.putString("email", object.getString("email"));
                                        editor.putString("gender", object.getString("gender"));
                                        editor.putString("birthday", object.getString("birthday"));
                                        editor.putString("city", city);
                                        editor.apply();
                                        editor.commit();
                                        Log.d("applyihng", "applying");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender,birthday,hometown,location");
                    request.setParameters(parameters);
                    request.executeAsync();
                }
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("fbcancel", "fbcancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("fberror", "fberror");
            }
        });
    }

    private void loginMethod(){
        if (sharedPreferences.contains("userid")&&isLoggedIn){
            Log.d("contains","userid");
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }else{
//            Toast.makeText(MainActivity.this,"Login to Continue",Toast.LENGTH_SHORT).show();
        }

    }
}















