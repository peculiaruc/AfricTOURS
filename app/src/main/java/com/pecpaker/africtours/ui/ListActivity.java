package com.pecpaker.africtours.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pecpaker.africtours.R;
import com.pecpaker.africtours.adapter.DealsAdapter;
import com.pecpaker.africtours.util.FirebaseUtil;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<TravelDeals> deals;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

       /* FirebaseUtil.openFirebaseReference("traveldeals");
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
       //         TextView traveldeals = (TextView) findViewById(R.id.travel_deals);
                TravelDeal travelDeal = snapshot.getValue(TravelDeal.class);
        //        traveldeals.setText(traveldeals.getText() + "\n" + travelDeal.getTitle());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
        */

        FirebaseUtil.openFirebaseReference("traveldeals", this);
        RecyclerView recyclerViewDeals = (RecyclerView) findViewById(R.id.recyclerViewDeals);
        final DealsAdapter adapter = new DealsAdapter();
        recyclerViewDeals.setAdapter(adapter);
        LinearLayoutManager dealslinearLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        recyclerViewDeals.setLayoutManager(dealslinearLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity, menu);
        MenuItem insertmenu = menu.findItem(R.id.insert_menu);
        if (FirebaseUtil.isAdnin == true) {
            insertmenu.setVisible(true);
        } else {
            insertmenu.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_menu:
                Intent intent = new Intent(this, DealActivity.class);
                startActivity(intent);
                return true;
            case R.id.log_out:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Logout ", "Logged out user");
                                FirebaseUtil.attachListener();
                            }
                        });
                FirebaseUtil.detachListener();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.attachListener();
    }

    public void showMenu() {
        invalidateOptionsMenu();

    }
}