package com.example.dhrpande.mytweeter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    // private static final String TWITTER_KEY = "";
    // private static final String TWITTER_SECRET = "";
    TwitterLoginButton loginButton;
    TextView headline_text;
    TextView tailline_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Permission Crap
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        //

        Twitter.initialize(this);

        //TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger( Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("CONSUMER_KEY", "CONSUMER_SECRET"))
                .debug(true)
                .build();
        Twitter.initialize(config);


        //Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        headline_text = (TextView) findViewById(R.id.headline_txt);
        tailline_text = (TextView) findViewById(R.id.tailline_txt) ;
        headline_text.setText("Don't let your expressions limited to 140 characters");
        tailline_text.setText( "An Indian Jugaad" );

        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                String userName = result.data.getUserName();
                Toast.makeText(MainActivity.this,userName,Toast.LENGTH_LONG).show();
                Intent compose_intent = new Intent(MainActivity.this, ComposeTweet.class);
                startActivity(compose_intent);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(MainActivity.this,"Login Failed Bose-DK",Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "APP will not work : Permission denied to read your storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }



}
