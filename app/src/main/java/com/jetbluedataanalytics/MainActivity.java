package com.jetbluedataanalytics;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.jetbluedataanalytics.data.Airport;
import com.jetbluedataanalytics.data.FlightData;
import com.jetbluedataanalytics.data.Input;
import com.jetbluedataanalytics.data.JetBlueGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static ArrayList<Card> cards;
    private static JetBlueGraph jetBlueGraph;
    public static Input input;
    public static ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewFlipper  =  (ViewFlipper)findViewById(R.id.view_flipper);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        cards = new ArrayList<>();

//        if((jetBlueGraph = FileManager.read(this)) == null ){
//
//            FileManager.write(this, jetBlueGraph);
//            Log.d("MYDATA", "LIVING IT NOT");
//        }else{
//            Log.d("MYDATA", "LIVING IT");
//        }
//
//        HashSet<Airport> airports = jetBlueGraph.airports;
//        HashMap<String, ArrayList<FlightData>> flights = jetBlueGraph.flights;
//        int x = 2;
        LoadGraphDataTask loadGraphDataTask = new LoadGraphDataTask(jetBlueGraph, this);
        loadGraphDataTask.execute();

    }

    public static void getData(Context context){
        jetBlueGraph = new JetBlueGraph(context);
        ArrayList<FlightData> flights = jetBlueGraph.query(input);
        ArrayList<Pair> pairs = new ArrayList<>();
        int prevIndex = -1;
        for(int i = 0 ; i < flights.size() ; i++){
            Pair p = new Pair();
            p.from = flights.get(i).from;
            p.to = flights.get(i).to;
            p.fare = flights.get(i).dollarFare;
            if(!pairs.contains(p)){
                pairs.add(p);
                prevIndex = pairs.size() - 1;
            }else{
                if(prevIndex != -1){
                    if(p.fare < pairs.get(prevIndex).fare){
                        pairs.get(prevIndex).fare = p.fare;
                    }
                }
            }

        }

        Iterator<Pair> it = pairs.iterator();
        while(it.hasNext()){
            Pair p = it.next();
            cards.add(new Card(context, p.from, p.to, p.fare));
        }

    }

    public static class Pair{
        String from, to;
        double fare;

        @Override
        public boolean equals(Object o) {
            if(o instanceof Pair){
                Pair p = (Pair)o;
                return (from.equals(p.from) && to.equals(p.to));
            }
            return false;
        }
    }

    public void updateInfiniteScroll(){
        LinearLayout layout = (LinearLayout)findViewById(R.id.infinite_scroll_container);

        if(cards.size() == 0){
            View.inflate(this, R.layout.nothing_to_show, layout);
        }else{
            layout.removeAllViews();
            for(int i = 0 ; i < cards.size() ; i++){
                layout.addView(cards.get(i));
            }
        }




    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
