package bengkelradio.sampay;

import com.google.firebase.database.DatabaseReference;

public class write {
    DatabaseReference mDatabase;
    public String uid;
    public int money;

    public write(String uid, int money){
        this.uid = uid;
        this.money = money;
    }

    public void writeNewUser() {
        mDatabase.child("users").child(uid).setValue(money);
    }
}
