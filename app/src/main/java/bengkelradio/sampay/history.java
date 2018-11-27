package bengkelradio.sampay;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class history extends AppCompatActivity {

    DatabaseReference reference;
    TextView historylist, saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historylist = (TextView) findViewById(R.id.historylist);
        saldo = (TextView) findViewById(R.id.saldo);
        reference = FirebaseDatabase.getInstance().getReference().child("temp");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String list = dataSnapshot.child("list").getValue().toString();
                String price = dataSnapshot.child("price").getValue().toString();

                historylist.setText(list);
                saldo.setText("TOTAL" + price);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
