package com.mad1.smartpark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SpaceAvailability extends AppCompatActivity {

    SpaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_availability);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("carPark1");
        ArrayList<ParkingSpace> list = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rvSpaces);
        int noOfColumns = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(this, noOfColumns));
        adapter = new SpaceAdapter(this);
        adapter.setData(list);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ParkingSpace> updatedData = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    ParkingSpace space = dataSnapshot.getValue(ParkingSpace.class);
                    updatedData.add(space);
                }
                adapter.setData(updatedData);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}