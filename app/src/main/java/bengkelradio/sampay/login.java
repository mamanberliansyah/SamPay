package bengkelradio.sampay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class

login extends AppCompatActivity {

    EditText inputUser,inputPass;
    ImageButton loginButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUser = (EditText)findViewById(R.id.username);
        inputPass = (EditText)findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();
    }

    public void userLogin(View v)
    {
        if(inputUser.getText().toString().equals("") && inputPass.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"blank is not allowed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            auth.signInWithEmailAndPassword(inputUser.getText().toString(),inputPass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"logged in",Toast.LENGTH_SHORT).show();
                                finish();
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"wrong email or password",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
