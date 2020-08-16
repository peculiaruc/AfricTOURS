package com.pecpaker.africtours.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pecpaker.africtours.R;
import com.pecpaker.africtours.ui.TravelDeal;
import com.pecpaker.africtours.util.FirebaseUtil;

import java.util.ArrayList;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.DealsViewHolder> {

    ArrayList<TravelDeal> deals;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    public DealsAdapter() {
        FirebaseUtil.openFirebaseReference("traveldeals");
        firebaseDatabase = FirebaseUtil.firebaseDatabase;

        //call the deals from the Util class
        deals = FirebaseUtil.mdeals;

        databaseReference = FirebaseUtil.databaseReference;
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
                //         TextView traveldeals = (TextView) findViewById(R.id.travel_deals);
                TravelDeal travelDeal = snapshot.getValue(TravelDeal.class);
                //        traveldeals.setText(traveldeals.getText() + "\n" + travelDeal.getTitle());
//                Log.d("Deal: deal called", travelDeal.getTitle());
                travelDeal.setId(snapshot.getKey());
                deals.add(travelDeal);
                notifyItemInserted(deals.size() - 1);

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

        //add childeventListenter to databaseReference
        databaseReference.addChildEventListener(childEventListener);
    }

    @NonNull
    @Override
    public DealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.traveldeals_row, parent, false);
        return new DealsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DealsViewHolder holder, int position) {

        TravelDeal deal = deals.get(position);
        holder.bind(deal);
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    public class DealsViewHolder extends RecyclerView.ViewHolder {

        TextView travelsDealTitle;

        public DealsViewHolder(@NonNull View itemView) {
            super(itemView);
            travelsDealTitle = itemView.findViewById(R.id.traveldeal_title);
        }

        public void bind(TravelDeal deal) {
            travelsDealTitle.setText(deal.getTitle());
        }
    }
}
