package com.beanmeapp.beanme.activities;

import com.beanmeapp.beanme.R;
import com.beanmeapp.beanme.databaseclasses.Drink;
import com.beanmeapp.beanme.databaseclasses.DrinkAttributes;
import com.beanmeapp.beanme.parsehelper.ParseToolbox;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class DrinkDetails extends ActionBarActivity {
    
    Spinner drinkField;
    Spinner sizeField;
    Spinner flavorField;
    Spinner tempField;
    Spinner blendField;
    Spinner milkField;
    Spinner shotsField;
    
    CheckBox foamCheck;
    CheckBox carCheck;
    CheckBox whipCheck;
    CheckBox chocCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_details);

        drinkField = (Spinner) findViewById(R.id.selDrink);
        drinkField.setAdapter(new ArrayAdapter<DrinkAttributes.Name>(this, android.R.layout.simple_spinner_item, DrinkAttributes.Name.values()));

        sizeField = (Spinner) findViewById(R.id.selSize);
        sizeField.setAdapter(new ArrayAdapter<DrinkAttributes.Size>(this, android.R.layout.simple_spinner_item, DrinkAttributes.Size.values()));

        flavorField = (Spinner) findViewById(R.id.selFlavor);
        flavorField.setAdapter(new ArrayAdapter<DrinkAttributes.Flavor>(this, android.R.layout.simple_spinner_item, DrinkAttributes.Flavor.values()));

        tempField = (Spinner) findViewById(R.id.selTemp);
        tempField.setAdapter(new ArrayAdapter<DrinkAttributes.Temperature>(this, android.R.layout.simple_spinner_item, DrinkAttributes.Temperature.values()));

        blendField = (Spinner) findViewById(R.id.selBlend);
        blendField.setAdapter(new ArrayAdapter<DrinkAttributes.Blend>(this, android.R.layout.simple_spinner_item, DrinkAttributes.Blend.values()));

        milkField = (Spinner) findViewById(R.id.selMilk);
        milkField.setAdapter(new ArrayAdapter<DrinkAttributes.Milk>(this, android.R.layout.simple_spinner_item, DrinkAttributes.Milk.values()));

        shotsField = (Spinner) findViewById(R.id.selShots);
        //TODO: fix magic numbers maybe
        Integer[] shotsArr = new Integer[6];
        for (int i=0; i<6; i++) { shotsArr[i] = i; }

        shotsField.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, shotsArr));

        foamCheck = (CheckBox) findViewById(R.id.foamCB);
        carCheck = (CheckBox) findViewById(R.id.carCB);
        whipCheck = (CheckBox) findViewById(R.id.whipCB);
        chocCheck = (CheckBox) findViewById(R.id.chocCB);

        final EditText commentsText = (EditText) findViewById(R.id.commentsText);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Create a Drink");

        findViewById(R.id.sendDrink).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DrinkAttributes.Name name;
                DrinkAttributes.Size size;
                DrinkAttributes.Flavor flavor;
                DrinkAttributes.Temperature temperature;
                DrinkAttributes.Blend blend;
                DrinkAttributes.Milk milk;
                int numShots;
                List<DrinkAttributes.Addon> addonList = new ArrayList<>();
                List<String> comments = new ArrayList<>();

                name = (DrinkAttributes.Name) drinkField.getSelectedItem();
                size = (DrinkAttributes.Size) sizeField.getSelectedItem();
                flavor = (DrinkAttributes.Flavor) flavorField.getSelectedItem();
                temperature = (DrinkAttributes.Temperature) tempField.getSelectedItem();
                blend = (DrinkAttributes.Blend) blendField.getSelectedItem();
                milk = (DrinkAttributes.Milk) milkField.getSelectedItem();
                numShots = (Integer) shotsField.getSelectedItem();
                if (foamCheck.isChecked()) { addonList.add(DrinkAttributes.Addon.FOAM); }
                if (carCheck.isChecked()) { addonList.add(DrinkAttributes.Addon.CARAMEL); }
                if (whipCheck.isChecked()) { addonList.add(DrinkAttributes.Addon.WHIPPED_CREAM); }
                if (chocCheck.isChecked()) { addonList.add(DrinkAttributes.Addon.CHOCOLATE); }
                comments.add(commentsText.getText().toString());

                Drink drink = new Drink(name, size, flavor, temperature, blend, milk, numShots, addonList, comments);
                //Toast toast = Toast.makeText(getApplicationContext(), drink.toString(), Toast.LENGTH_LONG);
                //toast.show();

                String hostid = getIntent().getStringExtra("hostid");
                ParseToolbox.sendDrink(drink, hostid);
                
                startActivity(new Intent(getBaseContext(), MainMenu.class));  // Launch the NFC 4.0 (SDK 14) Example
            }

        });
        
        drinkField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PopulateDrinkDetails pdd = new PopulateDrinkDetails();
                pdd.delegate = DrinkDetails.this;
                DrinkAttributes.Name drink = DrinkAttributes.Name.values()[position];
                pdd.execute(drink);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Fills all of the details on the page with the specific attributes of this coffee.
     * @param drink Drink, the drink to fill out the fields with.
     */
    public void populateDrink(Drink drink) {
        sizeField.setSelection(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DrinkAttributes.Size.values()).getPosition(drink.getSize()), true);
        flavorField.setSelection(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DrinkAttributes.Flavor.values()).getPosition(drink.getFlavor()), true);
        tempField.setSelection(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DrinkAttributes.Temperature.values()).getPosition(drink.getTemperature()), true);
        blendField.setSelection(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DrinkAttributes.Blend.values()).getPosition(drink.getBlend()), true);
        milkField.setSelection(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DrinkAttributes.Milk.values()).getPosition(drink.getMilk()), true);
        shotsField.setSelection(drink.getNumShots(), true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drink_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addToFav) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    private class PopulateDrinkDetails extends AsyncTask<DrinkAttributes.Name, Void, Drink> {

        public DrinkDetails delegate = null;

        @Override
        protected Drink doInBackground(DrinkAttributes.Name... params) {
            return ParseToolbox.getDrink(params[0]);
        }
        
        @Override
        protected void onPostExecute(Drink drink) {
            delegate.populateDrink(drink);
        }
    }
}
