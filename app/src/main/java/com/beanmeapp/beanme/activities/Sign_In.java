package com.beanmeapp.beanme.activities;

import com.beanmeapp.beanme.R;
import com.beanmeapp.beanme.helpers.SignUp_Login_Helper;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Sign_In extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);

        Typeface chalkdusterFont = Typeface.createFromAsset(getAssets(), "fonts/chalkduster.ttf");
        TextView logoP1Text = (TextView) findViewById(R.id.logoOne);
        logoP1Text.setTypeface(chalkdusterFont);

        Typeface muliRFont = Typeface.createFromAsset(getAssets(), "fonts/muli-r.ttf");
        TextView emailText = (TextView) findViewById(R.id.email_address_input);
        TextView passwordText = (TextView) findViewById(R.id.password_input);
        TextView signInBtnText = (TextView) findViewById(R.id.sign_in_btn);
        TextView signUpBtnText = (TextView) findViewById(R.id.sign_up_btn);

        signInBtnText.setTypeface(muliRFont);
        signUpBtnText.setTypeface(muliRFont);
        passwordText.setTypeface(muliRFont);
        emailText.setTypeface(muliRFont);


        final EditText emailInput = (EditText) findViewById(R.id.email_address_input);
        final EditText passwordInput = (EditText) findViewById(R.id.password_input);



        ((Button) findViewById(R.id.sign_in_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //This is user to check if there is a local error with signing in and a message to display back
                boolean hasValidationError = false;
                StringBuilder validationErrorMessage = new StringBuilder("Please ");

                //Checks that a username has been entered
                if (SignUp_Login_Helper.isEmpty(emailInput)) {
                    hasValidationError = true;
                    validationErrorMessage.append("Enter a Username");
                }

                //Checks that a password has been entered
                if (SignUp_Login_Helper.isEmpty(passwordInput)) {
                    SignUp_Login_Helper.appendErrorMessage(validationErrorMessage, "Enter your password",
                            hasValidationError);
                    hasValidationError = true;
                }

                //If there is an error then display the error and get out of the method.
                if (hasValidationError) {
                    validationErrorMessage.append(".");
                    Toast.makeText(Sign_In.this, validationErrorMessage.toString(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //Display dialog, let user know we are working on it.
                final ProgressDialog progressDialog = new ProgressDialog(
                        Sign_In.this);
                progressDialog.setTitle("Please Wait.");
                progressDialog.setMessage("Logging in. Please wait.");
                progressDialog.show();

                //Parse will log in for us
                ParseUser.logInInBackground(emailInput.getText().toString(),
                        passwordInput.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser parseUser, ParseException e) {
                                progressDialog.dismiss();

                                //If there is an error, then display the error
                                if (e != null) {
                                    Toast.makeText(Sign_In.this, e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    //Start the DispatchActivity so we can login
                                    ParsePush.subscribeInBackground(ParseUser.getCurrentUser().getObjectId());
                                    Intent intent = new Intent(Sign_In.this,
                                            Splash.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
            }


        });

        findViewById(R.id.sign_up_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Sign_Up.class));  // Launch the NFC 4.0 (SDK 14) Example
            }

        });

    }

    @Override
    public void onBackPressed() {
    }


}
