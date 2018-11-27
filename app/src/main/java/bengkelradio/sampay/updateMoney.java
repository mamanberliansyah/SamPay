package bengkelradio.sampay;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

//processing and updating money on Firebase
public class updateMoney {
    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseReference reference,reference2;
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
                String riwayat = dataSnapshot.child("riwayat").getValue().toString();
                String uid = currentUser.getUid();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String timeStamp = sdf.format(new Date());

                if (mode == "deposito"){
                    int total = addedMoney + money;
                    reference.child("money").setValue(total);
                    reference.child("riwayat").setValue("Deposito (+)\nRp." + addedMoney + "\n" + timeStamp + "\n\n" + riwayat);
                }
                if ((mode == "withdraw")&&(money > addedMoney)){
                    int total = money - addedMoney;
                    reference.child("money").setValue(total);
                    reference.child("riwayat").setValue("Withdraw (-)\nRp." + addedMoney + "\n" + timeStamp + "\n\n" + riwayat);
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
                final String hello = dataSnapshot.child("name").getValue().toString();
                final String status = dataSnapshot.child("status").getValue().toString();
                final String rekening = dataSnapshot.child("bank").getValue().toString();
                final String riwayat = dataSnapshot.child("riwayat").getValue().toString();

                if (mode == "deposito"){
                    final int total = cash + money;
                    reference.child("money").setValue(total);

                    reference2 = FirebaseDatabase.getInstance().getReference();
                    reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String buyerID = dataSnapshot.child("temp").child("buyer").getValue().toString();
                            String price = dataSnapshot.child("temp").child("price").getValue().toString();

                            String buyerName = dataSnapshot.child("user").child(buyerID).child("name").getValue().toString();

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            String timeStamp = sdf.format(new Date());

                            reference.child("riwayat").setValue(buyerName + " (+)\nRp." + price + "\n" + timeStamp + "\n\n" + riwayat);
                            reference.child("name").setValue(hello);
                            reference.child("status").setValue(status);
                            reference.child("bank").setValue(rekening);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                }
                if ((mode == "withdraw")&&(money > cash)){
                    final int total = money - cash;
                    reference.child("money").setValue(total);

                    reference2 = FirebaseDatabase.getInstance().getReference();
                    reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String sellerID = dataSnapshot.child("temp").child("seller").getValue().toString();
                            String price = dataSnapshot.child("temp").child("price").getValue().toString();
                            String sellerName = dataSnapshot.child("seller-data").child(sellerID).child("storeName").getValue().toString();

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            String timeStamp = sdf.format(new Date());

                            reference.child("riwayat").setValue(sellerName + " (-)\nRp." + price + "\n" + timeStamp + "\n\n" + riwayat);
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
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}