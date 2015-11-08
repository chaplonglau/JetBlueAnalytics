package com.jetbluedataanalytics;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.jetbluedataanalytics.data.JetBlueGraph;

/**
 * Created by Bilawal on 9/6/2015.
 */
public class LoadGraphDataTask extends AsyncTask<RelativeLayout, Void, Void> {


    private JetBlueGraph graph;
    private MainActivity context;


    public LoadGraphDataTask(JetBlueGraph graph, MainActivity mainActivity) {
        context = mainActivity;
        this.graph = graph;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(graph.airports != null){
            graph.airports.clear();

        }
        if(graph.flights != null){
            graph.flights.clear();

        }

    }

    @Override
    protected Void doInBackground(RelativeLayout... params) {

            MainActivity.getData(context);

        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
       // super.onPostExecute(aVoid);
        context.updateInfiniteScroll();
        if(context.viewFlipper != null){
            context.viewFlipper.showNext();
        }
    }
}
