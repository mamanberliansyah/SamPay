package bengkelradio.sampay;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//call and write data on Firebase
public class getData {
    DatabaseReference reference;
    String sellerID,buyerID,lisofProducts;
    int priceTotal;

    public void getBuyer (String buyerID){
        this.buyerID = buyerID;
    }

    public void getSeller (String sellerID, String listofProducts, int priceTotal){
        this.sellerID = sellerID;
        this.lisofProducts = listofProducts;
        this.priceTotal = priceTotal;
    }

    public void writeTemp() {
        reference = FirebaseDatabase.getInstance().getReference().child("temp");
        //reference.child("buyer").setValue(buyerID);
        reference.child("list").setValue(lisofProducts);
        reference.child("price").setValue(priceTotal);
        reference.child("seller").setValue(sellerID);
    }

    public void writeFinal(){
        reference = FirebaseDatabase.getInstance().getReference().child("temp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int price = dataSnapshot.child("price").getValue(Integer.class);
                String list = dataSnapshot.child("list").getValue().toString();
                String seller = dataSnapshot.child("seller").getValue().toString();

                reference.child("list").setValue(list);
                reference.child("price").setValue(price);
                reference.child("seller").setValue(seller);
                reference.child("buyer").setValue(buyerID);
                reference.child("state").setValue(0);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
