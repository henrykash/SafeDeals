package com.example.safedeals;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//this class deals with handling the realtime database
public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static FirebaseStorage mStorage;
    public static StorageReference mStorageRef;
    private static FirebaseUtil firebaseUtil;
    public static FirebaseAuth mFirebaseAuth;
    public static ArrayList<TravelDeal> mDeals;
    public static FirebaseAuth.AuthStateListener mAuthStateListener;
    private static ListActivity caller;
    private static final int RC_SIGN_IN = 1000;
    public static boolean isAdmin;


    private FirebaseUtil() {
    }

    ;

    public static void openFbReference(String ref, final ListActivity callerActivity) {

        if (firebaseUtil == null) {

            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            caller = callerActivity;

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.signIn();
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }
                    Toast.makeText(callerActivity.getBaseContext(), "Welcome back!", Toast.LENGTH_LONG).show();
                }
            };
            connectStorage();




      /*      mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser() == null){
                    FirebaseUtil.signIn();
                    }
                    Toast.makeText(callerActivity.getBaseContext(),"welcome Back!",Toast.LENGTH_LONG).show();
                }
            };

       */
        }


        mDeals = new ArrayList<TravelDeal>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
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

    private static void checkAdmin(String uid) {
        FirebaseUtil.isAdmin=false;
        DatabaseReference ref = mFirebaseDatabase.getReference().child("administrators")
                .child(uid);
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseUtil.isAdmin=true;
                caller.showMenu();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addChildEventListener(listener);
    }


    public  static void attachListener(){
       mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
      public static void detachListener(){
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);

      }
    public static void connectStorage() {
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference().child("deals_pictures");
    }
}


