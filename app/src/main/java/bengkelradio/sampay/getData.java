package bengkelradio.sampay;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class getData {
    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseReference reference;
    String uuid,hello,status,rekening;
    int money;

    public getData(String uuid) {
        this.uuid = uuid;
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
    }

    public void callData(){
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

    //kalau bisa pas mencet kasih suatu string buat nandain nama barang
    public void callDataSeller(){
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
}
