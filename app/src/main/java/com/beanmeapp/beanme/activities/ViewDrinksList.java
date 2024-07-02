package com.beanmeapp.beanme.activities;

import com.beanmeapp.beanme.R;
import com.beanmeapp.beanme.databaseclasses.Run;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class ViewDrinksList extends ActionBarActivity {

    ArrayList<String> al;
    ArrayList<String> allOrders;
    ArrayAdapter arrayAdapter;
    int i;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drinks_list);


        ActionBar ab = getSupportActionBar();
        ab.setTitle("Ordered Drinks");



        //add the view via xml or programmatically
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        al = new ArrayList<>();
        allOrders = new ArrayList<>();
        al.add("Orders: \nSwipe left or right.");

        Iterator<Run.Pair> it = MainMenu.myRun.iterator();

        while (it.hasNext()) {
            Run.Pair pair = it.next();
            allOrders.add(pair.getK().toString() + ":\n\t" + pair.getV().toString());
        }
        
        allOrders.add("End of orders.");

        //choose your favorite adapter
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, R.id.helloText, al );

        //set the listener and the adapter
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                //Log.d("LIST", "removed object!");
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                allOrders.add(al.remove(0));
                al.add(allOrders.remove(0));
                arrayAdapter.notifyDataSetChanged();
                //Toast.makeText(ViewDrinksList.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                al.add(0, allOrders.remove(allOrders.size() - 1));
                allOrders.add(0, al.remove(1));
                arrayAdapter.notifyDataSetChanged();
                //Toast.makeText(ViewDrinksList.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                /*al.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;*/
            }
            
            @Override
            public void onScroll(float x) {}
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                //makeToast(ViewDrinksList.this, "Clicked!");
            }
        });
        
        /*
        
        // Create a set of dummy strings to be displayed
        Iterator<Run.Pair> it = MainMenu.myRun.iterator();
        ArrayList<String> names = new ArrayList();
        ArrayList<String> drinks = new ArrayList();
        
        while (it.hasNext()) {
            Run.Pair pair = it.next();
            names.add(pair.getK().toString());
            drinks.add(pair.getV().toString());
        }
        

        final String[] items = Arrays.copyOf(names.toArray(), names.toArray().length, String[].class);
        final String[] items2 = Arrays.copyOf(drinks.toArray(), drinks.toArray().length, String[].class);

        // Create the adapter passing a reference to the XML layout for each row
        // and a reference to the EditText (or TextView) in the item XML layout
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.list_item, R.id.name, items){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = convertView;
                if(row == null){
                    //getting custom layout for the row
                    LayoutInflater inflater=getLayoutInflater();
                    row = inflater.inflate(R.layout.list_item, parent, false);
                }

                //get the reference to the EditText of your row.
                // find the item with row.findViewById()
                TextView nameField = (TextView)row.findViewById(R.id.name);
                nameField.setText(items[position]);

                // get the reference to the ImageView of your row
                //ImageView imageView = (ImageView)row.findViewById(R.id.image);
                //imageView.setImageResource(R.drawable.ic_launcher);

                TextView textView = (TextView)row.findViewById(R.id.name_white);
                textView.setText(items2[position]);

                return row; //the row that ListView draws
            }
        };

        // Get a reference to our ListView
        ListView listView1 = (ListView) findViewById(R.id.list_view);

        // Set the adapter on the List View
        listView1.setAdapter(adapter);
        */
    }

    public void makeToast(Activity act, String str) {
        Toast.makeText(ViewDrinksList.this, str, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_drinks_list, menu);
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
