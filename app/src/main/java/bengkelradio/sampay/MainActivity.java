package     bengkelradio.sampay;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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


public class MainActivity extends AppCompatActivity {
    ImageButton sell,buy,deposit,withdraw,history,promo;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseReference reference;
    TextView nama,uang;

    @Override
    public void onBackPressed(){
        logout();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
        nama = (TextView)findViewById(R.id.hai);
        uang = (TextView) findViewById(R.id.amount);

        //post database value to TextView
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String hello = dataSnapshot.child("name").getValue().toString();
                String money = dataSnapshot.child("money").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String rekening = dataSnapshot.child("bank").getValue().toString();
                nama.setText(hello);
                uang.setText(money);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        //navigassi activity
        sell = (ImageButton) findViewById(R.id.sell);
        sell.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, listBarang.class);
                startActivity(intent);
            }
        });

        buy = (ImageButton) findViewById(R.id.buy);
        buy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, progressBuy.class);
                startActivity(intent);
            }
        });

        deposit = (ImageButton) findViewById(R.id.deposit);
        deposit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, depSelect.class);
                startActivity(intent);
            }
        });

        withdraw = (ImageButton) findViewById(R.id.withdraw);
        withdraw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, withSelect.class);
                startActivity(intent);
            }
        });

        history = (ImageButton) findViewById(R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, history.class);
                startActivity(intent);
            }
        });

        promo = (ImageButton) findViewById(R.id.promo);
        promo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notAvailable();
            }
        });
    }

    private void notAvailable() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle("N/A");
        alertDialogBuilder
                .setMessage("This feature is not available yet, please come back later :D")
                .setIcon(R.mipmap.ic_launcher);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void logout(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle("Log Out");
        alertDialogBuilder
                .setMessage("Do you want to log out?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent intent = new Intent(MainActivity.this, login.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}