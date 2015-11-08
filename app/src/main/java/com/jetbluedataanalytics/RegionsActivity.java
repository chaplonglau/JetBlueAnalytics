package com.jetbluedataanalytics;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.jetbluedataanalytics.data.Constants;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Bilawal on 11/8/2015.
 */
public class RegionsActivity extends AppCompatActivity {
    private RelativeLayout[] layouts;
    private ArrayList<RelativeLayout> selected;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions);

        selected = new ArrayList<>();

        layouts = new RelativeLayout[]{
                (RelativeLayout)findViewById(R.id.img6),
                (RelativeLayout)findViewById(R.id.img7),
                (RelativeLayout)findViewById(R.id.img8),
                (RelativeLayout)findViewById(R.id.img9),
                (RelativeLayout)findViewById(R.id.img10)
        };

        for(int i = 0 ; i < layouts.length; i++){
            final RelativeLayout rl = layouts[i];
            rl.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    if(!selected.contains(v)){
                        rl.setBackground(getResources().getDrawable(R.drawable.blue_border));
                        selected.add((RelativeLayout)v);
                    }else{
                        rl.setBackground(getResources().getDrawable(R.drawable.black_border));
                        selected.remove((RelativeLayout) v);
                    }
                }
            });
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                MainActivity.input.regions.clear();
                Intent intent = new Intent(RegionsActivity.this, MainActivity.class);

                for(int i = 0 ; i < selected.size() ; i++){
                    RelativeLayout temp = selected.get(i);
                    int index = -1;
                    for(int j = 0 ; j < layouts.length; j++){
                        if(layouts[j] == temp){
                            index = j;
                            break;
                        }
                    }

                    switch (index){
                        case 0:
                            MainActivity.input.regions.addAll(Arrays.asList(Constants.BOS_AREA, Constants.NYC, Constants.UPSTATE, Constants.NE_ISLANDS));
                            break;
                        case 1:
                            MainActivity.input.regions.addAll(Arrays.asList(Constants.SF_BAY, Constants.LA_AREA, Constants.WAS, Constants.MTN_WEST, Constants.DESERT_WEST, Constants.PAC_NW, Constants.ANC));
                            break;
                        case 2:
                            MainActivity.input.regions.addAll(Arrays.asList(Constants.OTHER_CARRIBEAN, Constants.PR, Constants.DR, Constants.COLOMBIA));
                            break;
                        case 3:
                            MainActivity.input.regions.addAll(Arrays.asList(Constants.THE_SOUTH, Constants.TEXAS, Constants.PBI, Constants.MCO, Constants.GULF, Constants.FLL));
                            break;
                        case 4:
                            MainActivity.input.regions.addAll(Arrays.asList(Constants.MIDWEST));
                            break;
                    }

                }
                Log.d("SUM_OUTPUT", MainActivity.input.regions.toString());

                startActivity(intent);

            }
        });
    }
}
