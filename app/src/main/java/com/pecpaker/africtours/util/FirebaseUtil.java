package com.pecpaker.africtours.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pecpaker.africtours.ui.TravelDeal;

import java.util.ArrayList;

public class FirebaseUtil {

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    private static FirebaseUtil firebaseUtil;
    public static ArrayList<TravelDeal> mdeals;

    private  FirebaseUtil() {};

    public static void openFirebaseReference(String ref){
       if (firebaseUtil == null){
           firebaseUtil = new FirebaseUtil();
           firebaseDatabase = FirebaseDatabase.getInstance();
           mdeals = new ArrayList<TravelDeal>();
       }
       databaseReference = firebaseDatabase.getReference();
    }
}
