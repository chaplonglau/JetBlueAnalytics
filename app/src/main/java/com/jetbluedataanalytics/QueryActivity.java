package com.jetbluedataanalytics;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jetbluedataanalytics.data.Input;

/**
 * Created by Bilawal on 11/7/2015.
 */
public class QueryActivity extends AppCompatActivity {

    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        final String[] arr = getResources().getStringArray(R.array.airports);

        if(MainActivity.input == null){
            MainActivity.input = new Input();
        }

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.airports, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private int x = 0;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SplashScreenActivity.this, arr[position] + " clicked!", Toast.LENGTH_SHORT).show();
                if(x != 0){
                    MainActivity.input.originCode = arr[position];
                    Intent intent = new Intent(QueryActivity.this, PreferencesActivity.class);
                    startActivity(intent);
                }
                x++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Toast.makeText(SplashScreenActivity.this, "Nothing clicked!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
