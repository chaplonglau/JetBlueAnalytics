package com.jetbluedataanalytics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.jetbluedataanalytics.data.FlightData;
import com.jetbluedataanalytics.data.JetBlueGraph;

import java.util.ArrayList;

/**
 * Created by Bilawal on 11/7/2015.
 */
public class FlightDataActivity extends AppCompatActivity{

    private String title;
    private String originAirport, destAirport;
    private ArrayList<Card2> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_data_activity);

        ArrayList<FlightData> data = null;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("TITLE");
            originAirport = extras.getString("ORIGIN_AIRPORT");
            destAirport = extras.getString("DEST_AIRPORT");
            data = JetBlueGraph.getFlightDataForOriginAirport(originAirport, destAirport);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        cards = new ArrayList<>();

        if(data != null){
            for(int i = 0 ; i < data.size() ; i++){
                FlightData dat = data.get(i);
                Card2 c = new Card2(this, dat);
                cards.add(c);
            }
        }

        updateScrollView();

    }

    private void updateScrollView(){
        LinearLayout layout = (LinearLayout)findViewById(R.id.infinite_scroll_container2);
        if(layout.getChildCount() > 1){
            for(int i = 1; i < layout.getChildCount(); i++){
                layout.removeViewAt(i);
            }
        }
        for(int i = 0 ; i < cards.size() ; i++){
            layout.addView(cards.get(i));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
