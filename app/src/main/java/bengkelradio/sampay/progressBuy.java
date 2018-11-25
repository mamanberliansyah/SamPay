package bengkelradio.sampay;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class progressBuy extends Activity
        implements NfcAdapter.OnNdefPushCompleteCallback,
        NfcAdapter.CreateNdefMessageCallback {
    private ArrayList<String> messagesToSendArray = new ArrayList<>();
    private NfcAdapter mNfcAdapter;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseReference reference;

    //writes NFC tag to Seller
    public void addMessage() {
        String sentID = "Send to Seller";
        messagesToSendArray.add(sentID);
    }

    public NdefRecord[] createRecords() {
        NdefRecord[] records = new NdefRecord[messagesToSendArray.size() + 1];
        //To Create Messages Manually if API is less than
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            for (int i = 0; i < messagesToSendArray.size(); i++) {
                byte[] payload = messagesToSendArray.get(i).
                        getBytes(Charset.forName("UTF-8"));
                NdefRecord record = new NdefRecord(
                        NdefRecord.TNF_WELL_KNOWN,      //Our 3-bit Type name format
                        NdefRecord.RTD_TEXT,            //Description of our payload
                        new byte[0],                    //The optional id for our Record
                        payload);                       //Our payload for the Record
                records[i] = record;
            }
        }
        else {
            for (int i = 0; i < messagesToSendArray.size(); i++) {
                byte[] payload = messagesToSendArray.get(i).
                        getBytes(Charset.forName("UTF-8"));
                NdefRecord record = NdefRecord.createMime("text/plain", payload);
                records[i] = record;
            }
        }

        //call ID from database
        String id = currentUser.getUid();
        //write ID here
        records[messagesToSendArray.size()] = NdefRecord.createApplicationRecord(id);
        return records;
    }

    // write record
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        if (messagesToSendArray.size() == 0) {
            return null;
        }
        NdefRecord[] recordsToAttach = createRecords();
        return new NdefMessage(recordsToAttach);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_buy);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid());
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter != null) {
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        } else {
            Toast.makeText(this, "NFC not available on this device",
                    Toast.LENGTH_SHORT).show();
        }
        addMessage();
    }

    //call here if NFC message is sucessfully sent
    @Override
    public void onNdefPushComplete(NfcEvent event) {
        messagesToSendArray.clear();
        Intent intent = new Intent(progressBuy.this, transaction.class);
        startActivity(intent);
    }
}