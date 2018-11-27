package bengkelradio.sampay;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class history extends AppCompatActivity {

    DatabaseReference reference;
    TextView historylist, saldo;
    FirebaseUser currentUser;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        auth = FirebaseAuth.getInstance();
        historylist = (TextView) findViewById(R.id.historylist);
        saldo = (TextView) findViewById(R.id.saldo);
        reference = FirebaseDatabase.getInstance().getReference();

        currentUser = auth.getCurrentUser();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String list = dataSnapshot.child("user").child(currentUser.getUid()).child("riwayat").getValue().toString();
                String money = dataSnapshot.child("user").child(currentUser.getUid()).child("money").getValue().toString();
                historylist.setText(list);
                saldo.setText("TOTAL                       Rp." + money);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
