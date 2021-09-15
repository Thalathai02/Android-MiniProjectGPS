package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LocationListener{
    private static final int PERMISSION_FINE_LOCATION = 99 ;
    TextView t1,t2;
    Button bt_pause;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationManager gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.textView);
        t2 = findViewById(R.id.textView2);
        bt_pause = findViewById(R.id.button2);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

         gps= (LocationManager) getSystemService(Context.LOCATION_SERVICE);

         bt_pause.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 onPause();
                 startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                 finish();
             }
         });

    }

    @Override
    protected void onPause() {
        super.onPause();
        gps.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1234);
        }else{
            gps.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,50, (LocationListener) this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
              // gps.addNmeaListener((OnNmeaMessageListener) this);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLocationChanged(@NonNull Location location) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        postLocation post =new postLocation(location.getLongitude(),location.getLatitude(),formatter.format(date));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.42.13:45455/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APiservice service = retrofit.create(APiservice.class);
        Call<postLocation> Post = service.PostLocation(post);
        Post.enqueue(new Callback<postLocation>() {
            @Override
            public void onResponse(Call<postLocation> call, Response<postLocation> response) {
                Log.d("Call", "Latitude :"+location.getLatitude());
                Log.d("Call","Longitude :"+location.getLongitude());
                Log.d("Call","date :"+formatter.format(date));

            }
            @Override
            public void onFailure(Call<postLocation> call, Throwable t) {
                Log.d("Call", t.toString());
            }
        });
        t1.setText(String.valueOf("Longitud :"+location.getLongitude()));
        t2.setText(String.valueOf("Latitude :"+location.getLatitude()));

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {

    }

    @Override
    public void onFlushComplete(int requestCode) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}