package com.ait.setup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataActivity extends AppCompatActivity {

    private RecyclerView rView;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        rView = findViewById(R.id.listView);
        rootRef = FirebaseDatabase.getInstance().getReference();
        rView.setLayoutManager(new LinearLayoutManager(this));

        gainData();
    }

    public void gainData(){
        final FirebaseRecyclerOptions<Object> options =
                new FirebaseRecyclerOptions.Builder<>().setQuery(rootRef,Object.class).build();

        final FirebaseRecyclerAdapter<Object, DataViewHolder> apadter = new FirebaseRecyclerAdapter<Object, DataViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DataViewHolder dataViewHolder, int i, @NonNull Object o) {
                rootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String data = dataSnapshot.child("Data").getValue().toString();
                        dataViewHolder.data.setText(data);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_view_layout, parent, false);
                return new DataViewHolder(view);
            }
        };
        rView.setAdapter(apadter);
        apadter.startListening();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{
        TextView data;
        public  DataViewHolder(View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.data);
        }
    }
}