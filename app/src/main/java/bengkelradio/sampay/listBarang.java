package bengkelradio.sampay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class listBarang extends AppCompatActivity {
    private ImageButton confirm;
    private ImageButton cancel;

    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseReference reference;
    TextView storeNameTxt,txt1,txt2,txt3,txt4,listProd,totalPrice,price1,price2,price3,count1,count2,count3;
    ImageButton button1,button2,button3,button4;
    int priceTotal = 0;
    int cont1 = 0;
    int cont2 = 0;
    int cont3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("seller-data").child(currentUser.getUid());
        storeNameTxt = (TextView)findViewById(R.id.storeName);

        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);
        txt3 = (TextView)findViewById(R.id.txt3);
        txt4 = (TextView)findViewById(R.id.txt4);

        price1 = (TextView)findViewById(R.id.price1);
        price2 = (TextView)findViewById(R.id.price2);
        price3 = (TextView)findViewById(R.id.price3);

        listProd = (TextView)findViewById(R.id.listProd);
        totalPrice = (TextView)findViewById(R.id.totalPrice);

        count1 = (TextView)findViewById(R.id.count1);
        count2 = (TextView)findViewById(R.id.count2);
        count3 = (TextView)findViewById(R.id.count3);

        button1 = (ImageButton)findViewById(R.id.button1);
        button2 = (ImageButton)findViewById(R.id.button2);
        button3 = (ImageButton)findViewById(R.id.button3);
        button4 = (ImageButton)findViewById(R.id.button4);

        totalPrice.setText("Rp. " + priceTotal);

        //post database value to TextView
        reference.addValueEventListener(new ValueEventListener() {
        @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String storeName = dataSnapshot.child("storeName").getValue().toString();
                storeNameTxt.setText(storeName);
                txt1.setText(dataSnapshot.child("1").child("name").getValue().toString());
                txt2.setText(dataSnapshot.child("2").child("name").getValue().toString());
                txt3.setText(dataSnapshot.child("3").child("name").getValue().toString());
                txt4.setText(dataSnapshot.child("4").child("name").getValue().toString());

                price1.setText(dataSnapshot.child("1").child("price").getValue().toString());
                price2.setText(dataSnapshot.child("2").child("price").getValue().toString());
                price3.setText(dataSnapshot.child("3").child("price").getValue().toString());

                            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                priceTotal = priceTotal + Integer.parseInt(price1.getText().toString());
                totalPrice.setText("Rp. " + priceTotal);
                cont1 = cont1 + 1;
                count1.setText(Integer.toString(cont1));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                priceTotal = priceTotal + Integer.parseInt(price2.getText().toString());
                totalPrice.setText("Rp. " + priceTotal);
                cont2 = cont2 + 1;
                count2.setText(Integer.toString(cont2));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                priceTotal = priceTotal +  Integer.parseInt(price3.getText().toString());
                totalPrice.setText("Rp. " + priceTotal);
                cont3 = cont3 + 1;
                count3.setText(Integer.toString(cont3));
            }
        });

        confirm = (ImageButton) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String item1="";
                String item2="";
                String item3="";

                if (cont1 > 0){
                    item1 = txt1.getText().toString() + "        x" + cont1 + "\n";
                }
               if (cont2 > 0){
                    item2 = txt2.getText().toString() + "        x" + cont2 + "\n";
                }
                if (cont1 > 0){
                    item3 = txt3.getText().toString() + "        x" + cont3 + "\n";
                }
                String listofProducts = item1 + item2 + item3;
                String sellerID = currentUser.getUid();

                getData sellerInfo = new getData();
                sellerInfo.getSeller(sellerID, listofProducts, priceTotal);
                sellerInfo.writeTemp();

                Intent intent = new Intent(listBarang.this, progressSell.class);
                startActivity(intent);
            }
        });
        cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(listBarang.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
