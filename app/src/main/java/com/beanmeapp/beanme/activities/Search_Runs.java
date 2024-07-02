package com.beanmeapp.beanme.activities;

import com.beanmeapp.beanme.R;
import com.beanmeapp.beanme.parsehelper.ParseToolbox;
import com.parse.ParseUser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Search_Runs extends ActionBarActivity {

    MyCustomAdapter dataAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Create Group");

        displayListView();
        checkButtonClick();

    }

    private void displayListView() {

        //Array list of countries
        ArrayList<Country> countryList = new ArrayList<Country>();
        List<String> users = ParseToolbox.getUsers();
        for (String user : users) {
            Country country = new Country(user, user, false);
            countryList.add(country);
        }

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.country_info, countryList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Country country = (Country) parent.getItemAtPosition(position);
            }
        });
    }

    private class MyCustomAdapter extends ArrayAdapter<Country> {

        private ArrayList<Country> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                ArrayList<Country> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Country>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {

            TextView code;

            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.country_info, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Country country = (Country) cb.getTag();
                        country.setSelected(cb.isChecked());
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Country country = countryList.get(position);
            holder.code.setText(country.getName());
            holder.name.setText(country.getCode());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            return convertView;

        }

    }

    private void checkButtonClick() {

        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                AddGroups asyncAddGroups = new AddGroups();
                asyncAddGroups.execute();

                /*
                for (int i = 0; i < countryList.size(); i++) {
                    Country country = countryList.get(i);
                    if (country.isSelected()) {
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
                */
            }
        });

    }
    //TODO: query using searchStr and return the ID of the run, whatever that is.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public class Country {

        String code = null;

        String name = null;

        boolean selected = false;

        public Country(String code, String name, boolean selected) {
            super();
            this.code = code;
            this.name = name;
            this.selected = selected;
        }

        public String getCode() {
            return "";
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }


    }

    private class AddGroups extends AsyncTask<Void,Void, Void> {
        ProgressDialog dialog;
        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<Country> countryList = dataAdapter.countryList;
            ArrayList<String> userNames = new ArrayList<>();
            userNames.add(ParseUser.getCurrentUser().getUsername());
            for (int i = 0; i < countryList.size(); i++) {
                Country country = countryList.get(i);
                if (country.isSelected()) {
                    userNames.add(country.getName());
                }
            }

            String groupStuff = ParseToolbox.createGroup(((EditText)findViewById(R.id.groupNameEditText)).getText().toString());
            String groupName = groupStuff.split(ParseToolbox.GROUP_NAME_DELMITER)[1];
            String groupID = groupStuff.split(ParseToolbox.GROUP_NAME_DELMITER)[0];
            ParseToolbox.subscribeToGroup(groupID, groupName);
            ParseToolbox.addUsersToGroup(groupID, userNames, groupName);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Search_Runs.this, "Initializing group", "Just a moment please...", true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(Search_Runs.this, "Group created.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            startActivity(new Intent(Search_Runs.this, MainMenu.class));
        }
    }
}