package bengkelradio.sampay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class transaction extends AppCompatActivity {
    ImageButton confirm;
    ImageButton cancel;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        confirm = (ImageButton) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                reference = FirebaseDatabase.getInstance().getReference().child("temp");
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