package com.example.safedeals;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//this class deals with handling the realtime database
public class FirebaseUtil {
    public  static FirebaseDatabase mFirebaseDatabase;
    public  static DatabaseReference mDatabaseReference;
    private  static  FirebaseUtil firebaseUtil;
    public static FirebaseAuth mFirebaseAuth;
    public  static ArrayList<TravelDeal> mDeals;
    public  static FirebaseAuth.AuthStateListener mAuthStateListener;
    private static ListActivity caller;
   private static final int RC_SIGN_IN = 1000;

    private FirebaseUtil(){};

    public static void openFbReference(String ref, final ListActivity callerActivity){

       if(firebaseUtil == null) {

            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            caller = callerActivity;

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser() == null){
                    FirebaseUtil.signIn();
                    }
                    Toast.makeText(callerActivity.getBaseContext(),"welcome Back!",Toast.LENGTH_LONG).show();
                }
            };
        }
        mDeals = new ArrayList<TravelDeal>();
        mDatabaseReference= mFirebaseDatabase.getReference().child(ref);
    }


     private static void signIn(){
          List<AuthUI.IdpConfig> providers = Arrays.asList(
                  new AuthUI.IdpConfig.EmailBuilder().build(),
                  new AuthUI.IdpConfig.GoogleBuilder().build());


// Create and launch sign-in intent
          caller.startActivityForResult(
                  AuthUI.getInstance()
                          .createSignInIntentBuilder()
                          .setAvailableProviders(providers)
                          .build(),
                  RC_SIGN_IN);
      }



   public  static void attachListener(){
       mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
      public static void detachListener(){
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
      }
}


