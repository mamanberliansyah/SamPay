package bengkelradio.sampay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class withSelect extends AppCompatActivity {
    ImageButton confirm;
    ImageButton cancel;
    EditText withAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_select);
        withAmt = (EditText)findViewById(R.id.withAmt);
        confirm = (ImageButton) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateMoney addMoney = new updateMoney("withdraw");
                int withdrawAmmount = Integer.parseInt(withAmt.getText().toString());
                addMoney.moneyValue(withdrawAmmount);
                Intent intent = new Intent(withSelect.this, withList.class);
                intent.putExtra(Intent.EXTRA_TEXT, Integer.toString(withdrawAmmount));
                startActivity(intent);
            }
        });

        cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(withSelect.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
