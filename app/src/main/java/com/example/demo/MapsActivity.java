package com.example.demo;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.demo.databinding.ActivityMapsBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.42.13:45455/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APiservice service = retrofit.create(APiservice.class);
        Call<List<JsonItem>> call = service.locations();



        call.enqueue(new Callback<List<JsonItem>>() {
            @Override
            public void onResponse(Call<List<JsonItem>> call, retrofit2.Response<List<JsonItem>> response) {
                response.body();
                Toast.makeText(getApplicationContext(),response.toString() , Toast.LENGTH_SHORT).show();
                Log.d("Call", response.toString());
                List<JsonItem> data =response.body();
                for (JsonItem datas : data){
                            Marker markerLocation = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(datas.getLat(), datas.getLng()))
                            .title("Walker"));
                    markerLocation.setTag(0);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(datas.getLat(),datas.getLng()),16.2f));
                    Log.d("Call", datas.getLat().toString());
                    Log.d("Call", datas.getLng().toString());
                }
            }
            @Override
            public void onFailure(Call<List<JsonItem>> call, Throwable t) {
                Log.d("Call", t.toString());
            }
        });

        mMap.setOnMarkerClickListener(this);



    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
//        return false;

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

}