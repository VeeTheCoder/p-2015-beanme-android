package com.beanmeapp.beanme.helpers;

import com.beanmeapp.beanme.R;
import com.parse.Parse;
import com.parse.ParseInstallation;

import android.app.Application;
/**
 * Initializes for Parse to work.
 */
public class Initializer extends Application {


    public void onCreate() {
        Parse.initialize(this, getString(R.string.parse_app_id),
                getString(R.string.parse_client_id));
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
