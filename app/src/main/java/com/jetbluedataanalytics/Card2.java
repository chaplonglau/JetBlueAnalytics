package com.jetbluedataanalytics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jetbluedataanalytics.data.FlightData;

import org.w3c.dom.Text;

/**
 * Created by Bilawal on 11/7/2015.
 */
public class Card2 extends RelativeLayout {

    private TextView destText;
    private TextView dollarFare, dollarTax, pointsFare, pointsTax;
    private TextView isDomestic, isPrivate;
    private int backgroundColor;
    private FlightData data;

    public Card2(Context context, FlightData flightData) {
        super(context);
        data = flightData;
        init();
    }

    public Card2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Card2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init(){
        inflate(getContext(), R.layout.card2, this);
        destText = (TextView)findViewById(R.id.dest_text);
        dollarFare = (TextView)findViewById(R.id.dollar_fare);
        dollarTax = (TextView)findViewById(R.id.dollar_tax);
        pointsFare = (TextView)findViewById(R.id.points_fare);
        pointsTax = (TextView)findViewById(R.id.points_tax);
        isDomestic = (TextView)findViewById(R.id.is_domestic_route);
        isPrivate = (TextView)findViewById(R.id.is_private_fare);

        backgroundColor = Color.rgb((int)(Math.random()*256),(int)(Math.random()*256) ,(int)(Math.random()*256) );

        ((RelativeLayout)this.getChildAt(0)).getChildAt(0).setBackgroundColor(backgroundColor);
        destText.setText(data.from + " to " + data.to);
        dollarFare.setText(String.format("Dollar Fare: $%.2f", data.dollarFare));
        dollarTax.setText(String.format("Dollar Tax: $%.2f", data.dollarTax));
        pointsFare.setText(String.format("Points Fare: %.2f", data.pointsFare));
        pointsTax.setText(String.format("Points Tax: %.2f", data.pointsTax));
        isDomestic.setText("Is Domestic Flight: " + ((data.isDomesticRoute)? "Yes":"No"));
        isPrivate.setText("Is Private Flight: " + ((data.isPrivateFare)? "Yes":"No"));

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FlightDataActivity.class);
                intent.putExtra("TITLE", destText.getText() + "");
                intent.putExtra("ORIGIN_AIRPORT", destText.getText().toString().split(" ")[0]);
                getContext().startActivity(intent);
            }
        });
    }



}
