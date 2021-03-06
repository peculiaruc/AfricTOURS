package com.pecpaker.africtours.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.pecpaker.africtours.ui.DealActivity;
import com.pecpaker.africtours.ui.TravelDeals;
import com.pecpaker.africtours.util.FirebaseUtil;

import java.util.ArrayList;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.DealsViewHolder> {

    ArrayList<TravelDeals> deals;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    public DealsAdapter() {
        // FirebaseUtil.openFirebaseReference("traveldeals");
        firebaseDatabase = FirebaseUtil.firebaseDatabase;

        //call the deals from the Util class
        deals = FirebaseUtil.mdeals;

        databaseReference = FirebaseUtil.databaseReference;
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                TravelDeals tdeal = snapshot.getValue(TravelDeals.class);
//                Log.d("Deal ", tdeal.getTitle());
                tdeal.setId(snapshot.getKey());
                deals.add(tdeal);
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
        //     Context context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.traveldeals_row, parent, false);
        return new DealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealsViewHolder holder, int position) {

        TravelDeals deal = deals.get(position);
        holder.bind(deal);
//
//        holder.travelsDealTitle.setText(position);
//        holder.travelDealsDescription.setText(position);
//        holder.travelDealsPrice.setText(position);
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    public class DealsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView travelsDealTitle;
        TextView travelDealsDescription;
        TextView travelDealsPrice;
        ImageView imgDeal;

        public DealsViewHolder(@NonNull View itemView) {
            super(itemView);
            travelsDealTitle = (TextView) itemView.findViewById(R.id.traveldeal_title);
            travelDealsDescription = (TextView) itemView.findViewById(R.id.traveldeal_descrption);
            travelDealsPrice = (TextView) itemView.findViewById(R.id.traveldeal_price);
            imgDeal = (ImageView) itemView.findViewById(R.id.imgDeals);
            itemView.setOnClickListener(this);
        }

        public void bind(TravelDeals deal) {
            travelsDealTitle.setText(deal.getTitle());
            travelDealsPrice.setText(deal.getPrice());
            travelDealsDescription.setText(deal.getDescription());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("Click ", String.valueOf(position));
            TravelDeals selecteDeal = deals.get(position);
            Intent intent = new Intent(view.getContext(), DealActivity.class);
            intent.putExtra("Deal", selecteDeal);
            view.getContext().startActivity(intent);
        }
    }
}
