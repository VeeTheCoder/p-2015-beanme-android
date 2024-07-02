package com.beanmeapp.beanme.activities;

import com.beanmeapp.beanme.R;
import com.beanmeapp.beanme.helpers.SignUp_Login_Helper;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Sign_Up extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Typeface muliRFont = Typeface.createFromAsset(getAssets(), "fonts/muli-r.ttf");
        TextView regText = (TextView) findViewById(R.id.register_btn);
        TextView fNameText = (TextView) findViewById(R.id.fName_inpt);
        TextView lNameText = (TextView) findViewById(R.id.lName_inpt);
        TextView emailText = (TextView) findViewById(R.id.email_inpt);
        TextView pwdText = (TextView) findViewById(R.id.pwd_inpt);
        TextView cfmpwdText = (TextView) findViewById(R.id.cnfpwd_inpt);

        regText.setTypeface(muliRFont);
        fNameText.setTypeface(muliRFont);
        lNameText.setTypeface(muliRFont);
        emailText.setTypeface(muliRFont);
        pwdText.setTypeface(muliRFont);
        cfmpwdText.setTypeface(muliRFont);


                final EditText fNameInput = (EditText) findViewById(R.id.fName_inpt);
                final EditText lNameInput = (EditText) findViewById(R.id.lName_inpt);
                final EditText emailInput = (EditText) findViewById(R.id.email_inpt);
                final EditText pwdInput = (EditText) findViewById(R.id.pwd_inpt);
                final EditText cnfPwdInput = (EditText) findViewById(R.id.cnfpwd_inpt);

                ((Button) findViewById(R.id.register_btn)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Checks if there is a local error, and a builder to store the error message
                        boolean hasValidationError = false;
                        StringBuilder validationErrorMessage = new StringBuilder("Please ");

                        //Checks if the username is empty
                        if (SignUp_Login_Helper.isEmpty(emailInput)) {
                            hasValidationError = true;
                            validationErrorMessage.append("Enter a Username");
                        }

                        //Checks if the password is empty
                        if (SignUp_Login_Helper.isEmpty(fNameInput)) {
                            SignUp_Login_Helper.appendErrorMessage(validationErrorMessage, "Enter a First Name", hasValidationError);
                            hasValidationError = true;
                        }

                        //Checks if the password is empty
                        if (SignUp_Login_Helper.isEmpty(lNameInput)) {
                            SignUp_Login_Helper.appendErrorMessage(validationErrorMessage, "Enter a Last Name", hasValidationError);
                            hasValidationError = true;
                        }

                        //Checks if the password is empty
                        if (SignUp_Login_Helper.isEmpty(pwdInput)) {
                            SignUp_Login_Helper.appendErrorMessage(validationErrorMessage, "Enter Both Password Fields", hasValidationError);
                            hasValidationError = true;
                        }

                        //Makes sure the two passwords match
                        if (!pwdInput.getText().toString().equals(cnfPwdInput.getText().toString())) {
                            SignUp_Login_Helper.appendErrorMessage(validationErrorMessage, "The Passwords you entered do not Match", hasValidationError);
                            hasValidationError = true;
                        }

                        //If there was an error, display error message and don't try to login
                        if (hasValidationError) {
                            validationErrorMessage.append(".");
                            Toast.makeText(Sign_Up.this, validationErrorMessage.toString(),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //No errors, display progress bar to let user know the app is working
                        final ProgressDialog progressDialog = new ProgressDialog(Sign_Up.this);
                        progressDialog.setTitle("Please Wait.");
                        progressDialog.setMessage("Signing up. Please wait.");
                        progressDialog.show();

                        //Gets the new user and sets the name and password
                        ParseUser user = new ParseUser();
                        user.setUsername(emailInput.getText().toString());
                        user.put("fName", fNameInput.getText().toString());
                        user.put("lName", lNameInput.getText().toString());
                        user.setPassword(pwdInput.getText().toString());

                        //Parse will sign up in the background
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                progressDialog.dismiss();

                                //If there is an error display it
                                if (e != null ) {
                                    Toast.makeText(Sign_Up.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {


                                    //Start the dispatch activity so we may login.
                                    Intent intent = new Intent(Sign_Up.this, Splash.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
                    }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign__up, menu);
        return true;
    }





}
