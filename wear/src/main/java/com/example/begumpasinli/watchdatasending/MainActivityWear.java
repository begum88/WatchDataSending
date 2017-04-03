package com.example.begumpasinli.watchdatasending;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivityWear extends ConfirmationActivity implements GoogleApiClient.ConnectionCallbacks ,GoogleApiClient.OnConnectionFailedListener{


    public static final double STRIDE_FACTOR_FEMALE = 0.413;
    public static final double STRIDE_FACTOR_MALE = 0.415;

    public TextView stepsCount, caloriesCount, accelerometer;
    public String gender;
    public int W,H,S;
    public SensorManager mSensorManager, aSensorManager ;
    public Sensor mSensor, aSensor;
    public double A,TCB;

    Node mNode;
    GoogleApiClient mGoogleApiClient;
    private boolean mResolvingError= false;

    public static String SERVICE_CALLED_WEAR = "WearListClicked";
    public static String TAG = "WearListActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //connect the google api
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        stepsCount = (TextView)findViewById(R.id.text_steps);
        caloriesCount = (TextView)findViewById(R.id.text_calories);
        accelerometer = (TextView)findViewById(R.id.text_accelerometer);

        SharedPreferences prefs= getPreferences(MODE_PRIVATE);
        gender = prefs.getString("gender", "female");
        W = prefs.getInt("w",80);
        H = prefs.getInt("h",180);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        aSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        aSensor = aSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mResolvingError){
            mGoogleApiClient.connect();

        }
    }

    private void resolveNode() {
        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).setResultCallback(new ResultCallback< NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult nodes) {
                for (Node node : nodes.getNodes()) {
                    mNode = node;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorEventListener, mSensor , 1000);
        aSensorManager.registerListener(aSensorEventListener, aSensor , 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
        aSensorManager.unregisterListener(aSensorEventListener);
    }
    public SensorEventListener aSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            A += (int) event.values[0];
            updateUserInterface();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

        public SensorEventListener mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                S+= (int) event.values[0];
                TCB = getCalories();
                updateUserInterface();
            }


            @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
     public double getCalories(){
         double CBD = W*2.02;
         double D=0;
         if(gender.equals("female")){
             D = H* STRIDE_FACTOR_FEMALE * S * 0.00001;

         }else{
             D = H* STRIDE_FACTOR_MALE * S * 0.00001;

         }
         return CBD*D;
     }

     public void updateUserInterface() {


         if(stepsCount != null){
             stepsCount.setText(Integer.toString(S));}
         if(accelerometer != null){
             accelerometer.setText(Double.toString(A));}

         if (caloriesCount != null) {
             caloriesCount.setText(Double.toString(TCB));
         }
     }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        resolveNode();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
