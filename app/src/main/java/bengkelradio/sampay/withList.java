package bengkelradio.sampay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
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

public class withList extends AppCompatActivity {

    TextView bank;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseReference reference;

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(withList.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_list);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
        bank = (TextView)findViewById(R.id.bank);

        //post database value to TextView
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String rekening = dataSnapshot.child("bank").getValue().toString();
                bank.setText(rekening);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);

        // use the text in a TextView
        TextView textView = (TextView) findViewById(R.id.withAmt);
        textView.setText("Rp." + text);
    }
}
