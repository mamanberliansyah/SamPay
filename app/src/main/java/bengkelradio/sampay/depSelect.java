package bengkelradio.sampay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;


public class depSelect extends AppCompatActivity {
    ImageButton confirm,cancel,dualima,limapuluh,seratus;

    TextView depositText;
    Integer depositVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        depositVal = 0;
        setContentView(R.layout.activity_dep_select);

        depositText = (TextView) findViewById(R.id.depositAmt);
        depositText.setText("Rp. 0");


        dualima = (ImageButton) findViewById(R.id.dualima);
        dualima.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                depositVal = depositVal + 25000;
                depositText.setText("Rp. " + depositVal);
            }
        });

        limapuluh = (ImageButton) findViewById(R.id.limapuluh);
        limapuluh.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                depositVal = depositVal + 50000;
                depositText.setText("Rp. " + depositVal);
            }
        });

        seratus = (ImageButton) findViewById(R.id.seratus);
        seratus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                depositVal = depositVal + 100000;
                depositText.setText("Rp. " + depositVal);
            }
        });

        confirm = (ImageButton) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateMoney addMoney = new updateMoney("deposito");
                addMoney.moneyValue(depositVal);
                Intent intent = new Intent(depSelect.this, depList.class);
                intent.putExtra(Intent.EXTRA_TEXT, Integer.toString(depositVal));
                startActivity(intent);
            }
        });

        cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(depSelect.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}