package com.beanmeapp.beanme.activities;
import com.beanmeapp.beanme.R;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class InviteContacts extends ListActivity
{
    private static final String TAG = "ListViewActivity4";
    private static final Uri CONTACTS_URI = ContactsContract.Contacts.CONTENT_URI;
    private SimpleCursorAdapter adapter = null;
    private ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_contacts);

        lv = getListView();

        String[] projection = new String[]{ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME};
        Cursor c = managedQuery(CONTACTS_URI,
                projection, null, null, ContactsContract.Contacts.DISPLAY_NAME);

        String[] cols = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
        int[]   views = new int[]   {android.R.id.text1};

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_multiple_choice,
                c, cols, views);

        this.setListAdapter(adapter);

        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        findViewById(R.id.invt_sel_cnts_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Runs.class));  // Launch the NFC 4.0 (SDK 14) Example
            }

        });

    }

    public void doClick(View view) {
        if(!adapter.hasStableIds()) {
            Log.v(TAG, "Data is not stable");
            return;
        }
        long[] viewItems = lv.getCheckedItemIds();
        for(int i=0; i<viewItems.length; i++) {
            Uri selectedPerson = ContentUris.withAppendedId(
                    CONTACTS_URI, viewItems[i]);

            Log.v(TAG, selectedPerson.toString() + " is checked.");
        }
    }
}
