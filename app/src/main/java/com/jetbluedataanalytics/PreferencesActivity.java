package com.jetbluedataanalytics;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.nio.channels.Selector;
import java.util.ArrayList;

/**
 * Created by Bilawal on 11/7/2015.
 */
public class PreferencesActivity extends AppCompatActivity {

    private RelativeLayout[] layouts;
    private ArrayList<RelativeLayout> selected;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);

        selected = new ArrayList<>();

        layouts = new RelativeLayout[]{
                (RelativeLayout)findViewById(R.id.img1),
                (RelativeLayout)findViewById(R.id.img2),
                (RelativeLayout)findViewById(R.id.img3),
                (RelativeLayout)findViewById(R.id.img4),
                (RelativeLayout)findViewById(R.id.img5)
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
                MainActivity.input.destinationTypes.clear();
                Intent intent = new Intent(PreferencesActivity.this, RegionsActivity.class);
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
                            MainActivity.input.destinationTypes.add(24);
                            break;
                        case 1:
                            MainActivity.input.destinationTypes.add(21);
                            break;
                        case 2:
                            MainActivity.input.destinationTypes.add(22);
                            break;
                        case 3:
                            MainActivity.input.destinationTypes.add(23);
                            break;
                        case 4:
                            MainActivity.input.destinationTypes.add(19);
                            break;
                    }

                }
                Log.d("SUM_OUTPUT", MainActivity.input.destinationTypes.toString());
                startActivity(intent);

            }
        });
    }
}
