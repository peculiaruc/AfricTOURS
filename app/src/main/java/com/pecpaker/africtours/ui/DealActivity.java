package com.pecpaker.africtours.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pecpaker.africtours.R;
import com.pecpaker.africtours.util.FirebaseUtil;

public class DealActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    EditText editTitle;
    EditText editPrice;
    EditText editDescription;
    TravelDeals deal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        FirebaseUtil.openFirebaseReference("traveldeals", this);
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
        editTitle = (EditText) findViewById(R.id.editTextTitle);
        editPrice = (EditText) findViewById(R.id.editTextPrice);
        editDescription = (EditText) findViewById(R.id.editTextDescription);

        Intent intent = getIntent();
        TravelDeals deal = (TravelDeals) intent.getSerializableExtra("Deal");
        if (deal == null) {
            deal = new TravelDeals();
        }
        this.deal = deal;
        editTitle.setText((CharSequence) deal.getTitle());
        editDescription.setText(deal.getDescription());
        editPrice.setText(deal.getPrice());
        ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_menu:
                saveDeal();
                Toast.makeText(this, "Deal Saved", Toast.LENGTH_LONG).show();
                clean();
                backToList();
                return true;

            case R.id.delete_menu:
                deleteDeal();
                Toast.makeText(this, "Delete deal", Toast.LENGTH_LONG).show();
                backToList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clean() {
        editTitle.setText(" ");
        editPrice.setText(" ");
        editDescription.setText(" ");
        editTitle.requestFocus();

    }

    private void saveDeal() {

        deal.setTitle(editTitle.getText().toString());
        deal.setTitle(editDescription.getText().toString());
        deal.setTitle(editPrice.getText().toString());

        if (deal.getId() == null) {
            databaseReference.push().setValue(deal);
        } else {
            databaseReference.child(deal.getId()).setValue(deal);
        }
    }

    private void deleteDeal() {
        if (deal == null) {
            Toast.makeText(this, "pleasa save this deal before deleting it", Toast.LENGTH_LONG).show();
            return;
        }
        databaseReference.child(deal.getId()).removeValue();
    }

    private void backToList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


}