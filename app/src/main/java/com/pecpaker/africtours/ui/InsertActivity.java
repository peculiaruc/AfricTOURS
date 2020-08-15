package com.pecpaker.africtours.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pecpaker.africtours.R;
import com.pecpaker.africtours.util.FirebaseUtil;

public class InsertActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    TextInputLayout textInputTitle;
    TextInputLayout textInputPrice;
    TextInputLayout textInputDescription;
    TextInputEditText editTextTitle;
    TextInputEditText editTextprice;
    TextInputEditText editTextDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        FirebaseUtil.openFirebaseReference("traveldeals");
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;

        textInputTitle = (TextInputLayout)findViewById(R.id.textInput_title);
        textInputPrice = (TextInputLayout) findViewById(R.id.textInput_prize);
        textInputDescription = (TextInputLayout) findViewById(R.id.textInput_description);
        editTextDescription = findViewById(R.id.edit_description);
        editTextprice = findViewById(R.id.edit_price);
        editTextTitle = findViewById(R.id.edit_title);

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
        editTextTitle.setText("");
        editTextprice.setText("");
        editTextDescription.setText("");

    }

    private void saveDeal() {

        String title = textInputTitle.getEditText().getText().toString();
        String description = textInputDescription.getEditText().getText().toString();
        String price = textInputPrice.getEditText().getText().toString();

        TravelDeal deal = new TravelDeal(title, description, price,"");
        databaseReference.push().setValue(deal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


}