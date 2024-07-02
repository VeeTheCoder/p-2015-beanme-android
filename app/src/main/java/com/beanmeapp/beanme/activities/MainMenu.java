package com.beanmeapp.beanme.activities;

import com.beanmeapp.beanme.R;
import com.beanmeapp.beanme.databaseclasses.Run;
import com.parse.ParseUser;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends ActionBarActivity {

    public static Run myRun;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Typeface muliRFont = Typeface.createFromAsset(getAssets(), "fonts/muli-r.ttf");
        TextView searchText = (TextView) findViewById(R.id.viewbtntext);
        TextView groupText = (TextView) findViewById(R.id.groupsbtntext);
        TextView hostText = (TextView) findViewById(R.id.hostbtntext);
        TextView nameText = (TextView) findViewById(R.id.uName);
        TextView welcomeText = (TextView) findViewById(R.id.textView4);

        searchText.setTypeface(muliRFont);
        groupText.setTypeface(muliRFont);
        hostText.setTypeface(muliRFont);
        nameText.setTypeface(muliRFont);
        welcomeText.setTypeface(muliRFont);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Home");

        findViewById(R.id.hostbtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myRun = new Run();
                startActivity(new Intent(getBaseContext(), GroupInvite.class));  // Launch the NFC 4.0 (SDK 14) Example
            }

        });

        findViewById(R.id.listbtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (myRun != null) {
                    startActivity(new Intent(getBaseContext(),
                            ViewDrinksList.class));  // Launch the NFC 4.0 (SDK 14) Example
                } else {
                    Toast.makeText(getApplicationContext(), "You need to host a run to view drinks.", Toast.LENGTH_LONG).show();
                }
            }

        });

        findViewById(R.id.groupsbtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //ParseUser.logOut();
                startActivity(new Intent(getBaseContext(), Groups.class));
                //startActivity(new Intent(getBaseContext(), Search_Runs.class));  // Launch the NFC 4.0 (SDK 14) Example
            }

        });

        final TextView textView1 = (TextView) findViewById(R.id.uName);
        if (ParseUser.getCurrentUser().getString("fName") == null || ParseUser.getCurrentUser().getString("lName") == null) {
            textView1.setText(ParseUser.getCurrentUser().getUsername());
        } else {
            textView1.setText(ParseUser.getCurrentUser().getString("fName") + " " + ParseUser.getCurrentUser().getString("lName"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logoutmenuaction) {
            ParseUser.logOut();
            startActivity(new Intent(getBaseContext(), Splash.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), Sign_In.class)); //Remove when done with app

    }

}
