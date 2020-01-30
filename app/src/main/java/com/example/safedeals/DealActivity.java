package com.example.safedeals;

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

public class DealActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText txtTitle;
    EditText txtPrice;
    EditText txtDescription;
    TravelDeal deal;
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_activity);
      //  FirebaseUtil.openFbReference("traveldeals",this);
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        txtTitle =(EditText)findViewById(R.id.txtTitle);
        txtPrice =(EditText)findViewById(R.id.txtPrice);
        txtDescription =(EditText)findViewById(R.id.txtDescription);
        Intent intent = getIntent();
        TravelDeal deal =(TravelDeal) intent.getSerializableExtra("Deal");
         if(deal== null){
             deal =new  TravelDeal();
         }
           this.deal = deal;
         txtTitle.setText(deal.getTitle());
         txtPrice.setText(deal.getPrice());
          txtDescription.setText(deal.getDescription());
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
            case R.id.delete_deal:
                deleteDeal();
                Toast.makeText(this,"Deal Deleted",Toast.LENGTH_LONG).show();
                backToList();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }
    
     private void saveDeal(){
        deal.setTitle(txtTitle.getText().toString());
         deal.setPrice(txtPrice.getText().toString());
         deal.setDescription(txtDescription.getText().toString());
         if (deal.getId()==null){
             //when inserting a new object on the database we use this
             mDatabaseReference.push().setValue(deal);
         } else{
             mDatabaseReference.child(deal.getId()).setValue(deal);
         }

     }

      private  void  deleteDeal(){
        if (deal==null){
            Toast.makeText(this,"please save the deal before deleting",Toast.LENGTH_SHORT).show();
            return;
        } else{
            mDatabaseReference.child(deal.getId()).removeValue();
      }
    }
     private void backToList(){

          Intent intent =new Intent(this,ListActivity.class);
            startActivity(intent);
         }


     private void clean(){
        txtPrice.setText("");
        txtDescription.setText("");
        txtTitle.setText("");
        txtTitle.requestFocus();
     }
}
