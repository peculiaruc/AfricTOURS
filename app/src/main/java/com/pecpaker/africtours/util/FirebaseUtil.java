package com.pecpaker.africtours.util;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pecpaker.africtours.ui.TravelDeal;

import java.util.ArrayList;

public class FirebaseUtil {

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    private static FirebaseUtil firebaseUtil;

    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    public static ArrayList<TravelDeal> mdeals;

    private FirebaseUtil() {
    }

    ;

    public static void openFirebaseReference(String ref) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            firebaseDatabase = FirebaseDatabase.getInstance();

            firebaseAuth = FirebaseAuth.getInstance();
            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                }
            };

        }
        mdeals = new ArrayList<TravelDeal>();
        databaseReference = firebaseDatabase.getReference();
    }

    public static void attachListener() {
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    public static void detachListener() {
        firebaseAuth.removeAuthStateListener(authStateListener);

    }
}
