package com.jetbluedataanalytics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jetbluedataanalytics.data.FlightData;

/**
 * Created by Bilawal on 11/7/2015.
 */
public class Card extends RelativeLayout {

    private TextView mainText;
    private TextView priceText;
    private int backgroundColor;
    private FlightData data;

    public Card(Context context, FlightData flightData) {
        super(context);
        data = flightData;
        init();
    }

    public Card(Context context, String from, String to, double fare) {
        super(context);
        data = new FlightData();
        data.from = from;
        data.to = to;
        data.dollarFare = fare;
        init();
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init(){
        inflate(getContext(), R.layout.card, this);
        mainText = (TextView)findViewById(R.id.airport_dest_text);
        priceText = (TextView)findViewById(R.id.airport_dest_price);
        backgroundColor = Color.rgb((int)(Math.random()*256),(int)(Math.random()*256) ,(int)(Math.random()*256) );

        ((RelativeLayout)this.getChildAt(0)).getChildAt(0).setBackgroundColor(backgroundColor);
        mainText.setText(data.from + " to " + data.to);
        priceText.setText(String.format("Lowest Fare: $%.2f", data.dollarFare));

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FlightDataActivity.class);
                intent.putExtra("TITLE", mainText.getText() + "");
                intent.putExtra("ORIGIN_AIRPORT", mainText.getText().toString().split(" ")[0]);
                intent.putExtra("DEST_AIRPORT", mainText.getText().toString().split(" ")[1]);
                getContext().startActivity(intent);
            }
        });
    }



}
