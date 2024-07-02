package com.beanmeapp.beanme.activities;

import com.beanmeapp.beanme.R;
import com.beanmeapp.beanme.parsehelper.ParseToolbox;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Groups extends ActionBarActivity {

    ListView listView ;
    List<String> groupNames;
    List<String> groupObjectIds;
    ArrayAdapter<Object> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Groups");

        groupNames = new ArrayList<>();
        groupObjectIds = new ArrayList<>();

        listView = (ListView) findViewById(R.id.lv_activegroups);

        //Keeps the name and the id separate to make displaying the group names prettier.
        List<String> groups = ParseToolbox.getGroups();
        for (String group : groups) {
            groupObjectIds.add(group.split(ParseToolbox.GROUP_NAME_DELMITER)[0]);
            groupNames.add(group.split(ParseToolbox.GROUP_NAME_DELMITER)[1]);
        }

        Object[] values = groupNames.toArray();


        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                final int itemPosition     = position;
                
                AlertDialog.Builder builder = new AlertDialog.Builder(Groups.this);
                builder.setMessage("Leave group " + groupNames.get(itemPosition))
                        .setTitle("Goodbye " + groupNames.get(itemPosition) + " :'(")
                        .setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new LeaveGroup().execute(itemPosition);
                            }
                        })
                        .setNegativeButton("Stay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Groups.this, "Whew, that was close", Toast.LENGTH_SHORT);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        findViewById(R.id.invtcntsbtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Search_Runs.class));  // Launch the NFC 4.0 (SDK 14) Example
            }

        });


    }


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.groupCreate) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class LeaveGroup extends AsyncTask<Integer,Void, Void> {
        int itemPosition;
        @Override
        protected Void doInBackground(Integer... params) {
            itemPosition = params[0];
            ParseToolbox.unsubscribeToGroup(groupObjectIds.get(itemPosition), groupNames.get(itemPosition));
            ParseToolbox.removeCurrentUserFromGroup(groupObjectIds.get(itemPosition));
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Groups.this, "Deleting group...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(Groups.this, "Group deleted.", Toast.LENGTH_SHORT).show();
            groupNames.remove(itemPosition);
            groupObjectIds.remove(itemPosition);
            adapter = null;
            adapter = new ArrayAdapter(Groups.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, groupNames.toArray());
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
    }
}
