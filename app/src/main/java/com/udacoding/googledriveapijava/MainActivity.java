package com.udacoding.googledriveapijava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    //TODO 7 Tambahkan const variable
    private static final int RC_SIGN_IN = 616;

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO 2 Tambahkan initGoogleSignIn
        initGoogleSignIn();

        //TODO 5 Inisialisasi SignInButton
        SignInButton signInButton = findViewById(R.id.buttonSignIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 6 Tambahkan function signIn
                signIn();

            }
        });

    }

    private void initGoogleSignIn() {

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //TODO 8 onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //TODO 9 Handle data jika berhasil sign-in
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);

            if (googleSignInAccount != null)
            Log.d("TAG", "checkExistingSignInUser: "+ googleSignInAccount.getDisplayName());

        } catch (ApiException e){
            Log.w("TAG", "handleSignInResult: failed code = " + e.getStatusCode());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO 3 Tambahkan checkExistingSignInUser
        checkExistingSignInUser();
    }

    private void checkExistingSignInUser() {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null){
            Log.d("TAG", "checkExistingSignInUser: "+googleSignInAccount.getDisplayName());
        }
    }
}