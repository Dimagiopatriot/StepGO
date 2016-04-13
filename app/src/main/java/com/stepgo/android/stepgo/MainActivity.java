package com.stepgo.android.stepgo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stepgo.android.stepgo.music.MusicActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Saving steps
    public static final String APP_PREFERENCES = "mysteps";
    //for statistic
    public static final String APP_MONDAY_STEP = "monStep";
    public static final String APP_TUESDAY_STEP = "tueStep";
    public static final String APP_WEDNESDAY_STEP = "wedStep";
    public static final String APP_THURSDAY_STEP = "thuStep";
    public static final String APP_FRIDAY_STEP = "friStep";
    public static final String APP_SATURDAY_STEP = "satStep";
    public static final String APP_SUNDAY_STEP = "sunStep";
    private SharedPreferences sharedPreferences;

    public static boolean isAppClosed;

    final static int NORMAL_STEPS = 10000;
    //check musicactivityexisting
    boolean isMusicActivityExist;
    //progress
    TextView steps;
    TextView percent;
    TextView motivationSpeech;
    ProgressBar progressBar;
    String getDateString;
    //statistic
    ArrayList<String> strList = new ArrayList<>();
    int[] dataArray;

    private float previousY;
    private float previousZ;
    private float currentY;
    private float currentZ;
    private int threshold;
    private int numSteps;

    private SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //saving block
        sharedPreferences=getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);

        //variables initializing
        isAppClosed = false;
        isMusicActivityExist = false;
        dataArray = new int[7];
        steps = (TextView)findViewById(R.id.textView3);
        percent = (TextView)findViewById(R.id.textView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        motivationSpeech = (TextView)findViewById(R.id.textView4);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //pedometer var`s initialize
        previousY=0;
        currentY=0;
        previousZ=0;
        currentZ=0;
        threshold=5;

        getDate();

        //pedometer method
        enableAccelerometerListening();

        //statistic block
        setDays(strList);
        setDayData(dataArray);


        //numStep initializing
        setNumStepsVariable();
    }

    //closing App
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isAppClosed = true;
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here
        View contentMain = findViewById(R.id.content_main);
        View statisticMain = findViewById(R.id.statistic_main);
        View recipeMain = findViewById(R.id.recipes_main);

        int id = item.getItemId();

        if (id == R.id.nav_walking) {
            // Handle the camera action
            contentMain.setVisibility(View.VISIBLE);
            statisticMain.setVisibility(View.INVISIBLE);
            recipeMain.setVisibility(View.INVISIBLE);

        } else if (id == R.id.nav_statistic) {
            contentMain.setVisibility(View.INVISIBLE);
            statisticMain.setVisibility(View.VISIBLE);
            recipeMain.setVisibility(View.INVISIBLE);

            //statistic build
            BarView barView = (BarView)findViewById(R.id.bar_view);
            barView.setBottomTextList(strList);
            barView.setDataList(setDataList(), 10000);
        } else if (id == R.id.nav_music) {
            //checking existing of music activity
            Intent intent = new Intent(this, MusicActivity.class);
            if (isMusicActivityExist == false){
                startActivity(intent);
                isMusicActivityExist = true;
            }else {
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }

        } else if (id == R.id.nav_recipes) {
            contentMain.setVisibility(View.INVISIBLE);
            statisticMain.setVisibility(View.INVISIBLE);
            recipeMain.setVisibility(View.VISIBLE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     * Arraylists of statistic name day initialize
     */
    public void setDays(ArrayList<String> days){
        days.add("Mon");
        days.add("Tue");
        days.add("Wed");
        days.add("Thu");
        days.add("Fri");
        days.add("Sat");
        days.add("Sun");
    }

    //Setting of dayData
    public void setDayData(int[] dayData){
            dayData[0] = loadInstanceSteps(APP_MONDAY_STEP);
            dayData[1] = loadInstanceSteps(APP_TUESDAY_STEP);
            dayData[2] = loadInstanceSteps(APP_WEDNESDAY_STEP);
            dayData[3] = loadInstanceSteps(APP_THURSDAY_STEP);
            dayData[4] = loadInstanceSteps(APP_FRIDAY_STEP);
            dayData[5] = loadInstanceSteps(APP_SATURDAY_STEP);
            dayData[6] = loadInstanceSteps(APP_SUNDAY_STEP);

    }

    private void enableAccelerometerListening(){
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener sensorEventListener =new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //var`s for accelerometer
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            currentZ=z;
            currentY=y;
            if (Math.abs(currentY-previousY)>threshold||Math.abs(currentZ-previousZ)>threshold){
                numSteps++;
                steps.setText(String.valueOf(numSteps) + " steps");
            }

            previousZ=z;
            previousY=y;

            savedInstanceSteps(numSteps);
            setDayData(dataArray);
            setPercent();
            setMotivationSpeech();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    //TextView and ProgressBar logic
    private void setPercent(){
        float stepPercent = (numSteps*100)/NORMAL_STEPS;
        percent.setText(String.valueOf((int)(stepPercent))+"%");
        progressBar.setProgress((int)(stepPercent));
    }

    //TextView under ProgressBar in your steps frame
    private void setMotivationSpeech(){
        if (progressBar.getProgress()<=25){
            motivationSpeech.setText("You should work harder!");
        }
        if (progressBar.getProgress()>25 && progressBar.getProgress()<=50){
            motivationSpeech.setText("Well done! But you can better!");
        }
        if (progressBar.getProgress()>50 && progressBar.getProgress()<=75){
            motivationSpeech.setText("Such a sportsman! Do it again!");
        }
        if (progressBar.getProgress()>75 && progressBar.getProgress()<100){
            motivationSpeech.setText("I can`t believe it! You are a full of health.");
        }
        if (progressBar.getProgress()>=100)
            motivationSpeech.setText("MONSTER!!!");
    }

    //take calendar date
    private String getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");
        return getDateString= dateFormat.format(calendar.getTime());
    }

    //Because bar.setDataList() don`t take int just ArrayList<Integer>()
    private ArrayList<Integer> setDataList(){
        ArrayList<Integer> endDataList = new ArrayList<>();
        for (int i: dataArray){
            endDataList.add(i);
        }
        return endDataList;
    }

    //saved instances of steps from different days of week
    private void savedInstanceSteps(int numSteps){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(getDateString.equals("Mon")||getDateString.equals("пн"))
            editor.putInt(APP_MONDAY_STEP,numSteps);
        if(getDateString.equals("Tue")||getDateString.equals("вт"))
            editor.putInt(APP_TUESDAY_STEP,numSteps);
        if(getDateString.equals("Wed")||getDateString.equals("ср"))
            editor.putInt(APP_WEDNESDAY_STEP,numSteps);
        if(getDateString.equals("Thu")||getDateString.equals("чт"))
            editor.putInt(APP_THURSDAY_STEP,numSteps);
        if(getDateString.equals("Fri")||getDateString.equals("пт"))
            editor.putInt(APP_FRIDAY_STEP,numSteps);
        if(getDateString.equals("Sat")||getDateString.equals("сб"))
            editor.putInt(APP_SATURDAY_STEP,numSteps);
        if(getDateString.equals("Sun")||getDateString.equals("вс"))
            editor.putInt(APP_SUNDAY_STEP,numSteps);
        editor.apply();
    }

    //load instances of steps from different days of week
    private int loadInstanceSteps(String APP_PREFERENCES){
        if (sharedPreferences.contains(APP_PREFERENCES)) {
            return sharedPreferences.getInt(APP_PREFERENCES, 0);
        }
        return 0;
    }

    //Setting of numSteps
    private void setNumStepsVariable(){
        if(getDateString.equals("Mon")||getDateString.equals("пн"))
            numSteps = loadInstanceSteps(APP_MONDAY_STEP);
        if(getDateString.equals("Tue")||getDateString.equals("вт"))
            numSteps = loadInstanceSteps(APP_TUESDAY_STEP);
        if(getDateString.equals("Wed")||getDateString.equals("ср"))
            numSteps = loadInstanceSteps(APP_WEDNESDAY_STEP);
        if(getDateString.equals("Thu")||getDateString.equals("чт"))
            numSteps = loadInstanceSteps(APP_THURSDAY_STEP);
        if(getDateString.equals("Fri")||getDateString.equals("пт"))
            numSteps = loadInstanceSteps(APP_FRIDAY_STEP);
        if(getDateString.equals("Sat")||getDateString.equals("сб"))
            numSteps = loadInstanceSteps(APP_SATURDAY_STEP);
        if(getDateString.equals("Sun")||getDateString.equals("вс"))
            numSteps = loadInstanceSteps(APP_SUNDAY_STEP);
    }

    //Reset button
    public void resetNumStepsVariable(View view) {
        numSteps=0;
        steps.setText("0 steps");
    }
}
