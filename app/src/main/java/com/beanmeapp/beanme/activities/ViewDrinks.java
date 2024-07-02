package com.beanmeapp.beanme.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.parse.ParseUser;
import android.view.MotionEvent;
import com.beanmeapp.beanme.R;

public class ViewDrinks extends ActionBarActivity{

    float x1,x2;
    float y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drinks);

        final TextView textView1 = (TextView) findViewById(R.id.uName);
        textView1.setText(ParseUser.getCurrentUser().getString("fName") + " " + ParseUser.getCurrentUser().getString("lName") + "'s" );
    }

    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();
                y2 = touchevent.getY();

                if (x1 < x2)
                {
                    final TextView textView4 = (TextView) findViewById(R.id.drinkBlend);
                    textView4.setText("crapalatte");
                }

                // if right to left sweep event on screen
                if (x1 > x2)
                {
                    final TextView textView4 = (TextView) findViewById(R.id.drinkBlend);
                    textView4.setText("Crapacinp");
                }
                break;
            }
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_drinks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
