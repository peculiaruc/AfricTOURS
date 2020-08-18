package com.pecpaker.africtours.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        FirebaseUtil.openFirebaseReference("traveldeals");
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;

        editTitle = (EditText) findViewById(R.id.editTextTitle);
        editPrice = (EditText) findViewById(R.id.editTextPrice);
        editDescription = (EditText) findViewById(R.id.editTextDescription);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.save_menu:
            saveDeal();
                Toast.makeText(this, "Deal Saved", Toast.LENGTH_LONG).show();
            clear();
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clear() {
        editTitle.setText("");
        editPrice.setText("");
        editDescription.setText("");

        editTitle.requestFocus();

    }

    private void saveDeal() {

        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();
        String price = editPrice.getText().toString();

        TravelDeal deal = new TravelDeal(title, description, price, "");
        databaseReference.push().setValue(deal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


}