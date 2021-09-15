package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity2 extends AppCompatActivity {
Button logout,Record_map ,story;
TextView t1,t2;




    private FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        checkout();
        logout = findViewById(R.id.Logout);
        t1 =findViewById(R.id.textView4);
        t2 =findViewById(R.id.textView5);
        Record_map=findViewById(R.id.button3);
        story =findViewById(R.id.button4);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);



        story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                callApi();
//                Toast.makeText(getApplicationContext(),service.toString() , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MapsActivity.class));
//                finish();
            }
        });
        Record_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Good bye : "+ signInAccount.getDisplayName(), Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoingoogleActivity.class));
//                finish();
            }
        });

        if(signInAccount!=null){
            t1.setText(signInAccount.getDisplayName());
            t2.setText(signInAccount.getEmail());
            Log.d("getIdToken", "onCreate: "+signInAccount.getIdToken());
        }
    }

    public void checkout(){
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount == null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
        }
    }
}
