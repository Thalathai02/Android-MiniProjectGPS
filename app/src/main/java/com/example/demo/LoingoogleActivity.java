package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demo.databinding.ActivityLoingoogleBinding;
import com.example.demo.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoingoogleActivity extends AppCompatActivity {
private String default_web_client_id = "378620451216-9ec4bjk7ae83dg8dlu7b9eap5b5l12un.apps.googleusercontent.com";
    private GoogleSignInClient mGoogleSignInClient;
    private final  static  int RC_SIGN_IN =60;
    private FirebaseAuth firebaseAuth;
// ...
// Initialize Firebase Auth
    private FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loingoogle);
        checkout();
        createRequest();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getAPI();
                signIn();
            }
        });

    }

public void checkout(){
    GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
    if(signInAccount != null){
        startActivity(new Intent(getApplicationContext(),MainActivity2.class));
        finish();
    }
}
    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("378620451216-9ec4bjk7ae83dg8dlu7b9eap5b5l12un.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
       Intent signInIntent = mGoogleSignInClient.getSignInIntent();
       startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("Auth", "firebaseAuthWithGoogle:" + account.getId());
               //firebaseAuthWithGoogle(account.getIdToken());
                GetcallAPI();
                PostcallAPI(account.getEmail(),account.getDisplayName());
                Toast.makeText(getApplicationContext(), "Wellcome "+ account.getAccount().name, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                finish();

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Auth", "Google sign in failed", e);
                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();

            }
        }
    }


//    private void firebaseAuthWithGoogle(String account) {
//        AuthCredential credential =GoogleAuthProvider.getCredential(account,null);
//        firebaseAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                Log.d("Login", "onSuccess: Login");
//                if(authResult.getAdditionalUserInfo().isNewUser()){
//                    Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getApplicationContext(), "Wellcome...", Toast.LENGTH_SHORT).show();
//                }
//                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
//                finish();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }
    public void GetcallAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.42.13:45455/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APiservice service = retrofit.create(APiservice.class);
        Call<List<UserJson>> call = service.user();



        call.enqueue(new Callback<List<UserJson>>() {
            @Override
            public void onResponse(Call<List<UserJson>> call, retrofit2.Response<List<UserJson>> response) {
                response.body();
                Toast.makeText(getApplicationContext(),response.toString() , Toast.LENGTH_SHORT).show();
                Log.d("Call", response.toString());
                List<UserJson> data =response.body();
                for (UserJson datas : data){
                    String TEXT = "";
                    TEXT +="Email "+datas.getEmail();
                    }
                }
            @Override
            public void onFailure(Call<List<UserJson>> call, Throwable t) {
                Log.d("Call", t.toString());
            }
        });

    }
    public void PostcallAPI(String email, String uuid){
        PostUser post =new PostUser("unknow","unknow");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.42.13:45455/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APiservice service = retrofit.create(APiservice.class);
        Call<PostUser> Post = service.Postuser(post);
        Post.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser> response) {
                Log.d("Call", email);
                Log.d("Call", uuid);
            }
            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                Log.d("Call", t.toString());
            }
        });



    }
}