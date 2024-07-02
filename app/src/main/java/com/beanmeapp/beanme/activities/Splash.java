package com.beanmeapp.beanme.activities;

import com.beanmeapp.beanme.R;
import com.beanmeapp.beanme.parsehelper.ParseToolbox;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;


public class Splash extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Typeface chalkdusterFont = Typeface.createFromAsset(getAssets(), "fonts/chalkduster.ttf");
        TextView logoP1Text = (TextView) findViewById(R.id.logoOne);
        logoP1Text.setTypeface(chalkdusterFont);


        //If the user is not is logged in then start the home screen
        if (ParseUser.getCurrentUser() != null) {
            ParseToolbox.addInstallationToCurrentUser();
            ParseToolbox.subscribeToInstallation();
            ParsePush.subscribeInBackground(
                    ParseInstallation.getCurrentInstallation().getObjectId());
            startActivity(new Intent(this, MainMenu.class));

            //Otherwise go to the login and signup activity
        } else {
            startActivity(new Intent(this, Sign_In.class));
        }

    }
}
