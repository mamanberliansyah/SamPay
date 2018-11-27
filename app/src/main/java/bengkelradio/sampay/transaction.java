package bengkelradio.sampay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class transaction extends AppCompatActivity {
    ImageButton confirm;
    ImageButton cancel;
    DatabaseReference reference,sellerData;
    TextView alamatToko,bon,namaToko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        reference = FirebaseDatabase.getInstance().getReference().child("temp");

        alamatToko = (TextView) findViewById(R.id.alamatToko);
        namaToko = (TextView) findViewById(R.id.namaToko);
        bon =(TextView) findViewById(R.id.bon);

        //calling data
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String seller = dataSnapshot.child("seller").getValue().toString();
                String listProd = dataSnapshot.child("list").getValue().toString();
                int price = Integer.parseInt(dataSnapshot.child("price").getValue().toString());
                bon.setText(listProd + "\n\nTOTAL Rp." + price);
                sellerData = FirebaseDatabase.getInstance().getReference().child("seller-data").child(seller);
                sellerData.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String address = dataSnapshot.child("address").getValue().toString();
                        String store = dataSnapshot.child("storeName").getValue().toString();

                        alamatToko.setText(address);
                        namaToko.setText(store);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //updating data
        confirm = (ImageButton) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int price = dataSnapshot.child("price").getValue(Integer.class);
                        String buyer = dataSnapshot.child("buyer").getValue().toString();
                        String seller = dataSnapshot.child("seller").getValue().toString();

                        updateMoney penjual = new updateMoney("deposito");
                        penjual.myMoney(seller,price);

                        updateMoney pembeli = new updateMoney("withdraw");
                        pembeli.myMoney(buyer,price);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                Intent intent = new Intent(transaction.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(transaction.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}