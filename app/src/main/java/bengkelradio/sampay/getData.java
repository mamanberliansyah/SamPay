package bengkelradio.sampay;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

//call and write data on Firebase
public class getData {
    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseReference reference;
    String uuid, hello, status, rekening, var;
    int money;

    public getData(String uuid) {
        this.uuid = uuid;
    }

    public void callData() {
        reference = FirebaseDatabase.getInstance().getReference().child("user").child(uuid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int money = dataSnapshot.child("money").getValue(Integer.class);
                String hello = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String rekening = dataSnapshot.child("bank").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void callDataSeller() {
        reference = FirebaseDatabase.getInstance().getReference().child("seller-data").child(uuid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int money = dataSnapshot.child("price").getValue(Integer.class);
                String image = dataSnapshot.child("photo").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void writeTemp(String var) {
        reference = FirebaseDatabase.getInstance().getReference().child("temp");
        reference.child("tmp").setValue(var);
    }
}
