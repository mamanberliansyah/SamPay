package bengkelradio.sampay;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//processing and updating money on Firebase
public class updateMoney {
    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseReference reference;
    int addedMoney,money,cash;
    String mode,id;

    public updateMoney(String mode) {
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        this.mode = mode;
    }

    public void moneyValue(int Money){
        reference = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
        addedMoney = Money;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int money = dataSnapshot.child("money").getValue(Integer.class);
                String hello = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String rekening = dataSnapshot.child("bank").getValue().toString();
                String uid = currentUser.getUid();

                if (mode == "deposito"){
                    int total = addedMoney + money;
                    reference.child("money").setValue(total);
                }
                if ((mode == "withdraw")&&(money > addedMoney)){
                    int total = money - addedMoney;
                    reference.child("money").setValue(total);
                }
                reference.child("name").setValue(hello);
                reference.child("status").setValue(status);
                reference.child("bank").setValue(rekening);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void myMoney(String id,int Money){
        this.id = id;
        this.cash = Money;
        reference = FirebaseDatabase.getInstance().getReference().child("user").child(id);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int money = dataSnapshot.child("money").getValue(Integer.class);
                String hello = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String rekening = dataSnapshot.child("bank").getValue().toString();

                if (mode == "deposito"){
                    int total = cash + money;
                    reference.child("money").setValue(total);
                }
                if ((mode == "withdraw")&&(money > cash)){
                    int total = money - cash;
                    reference.child("money").setValue(total);
                }

                reference.child("name").setValue(hello);
                reference.child("status").setValue(status);
                reference.child("bank").setValue(rekening);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}