package fitness.sportgenertaion.fitnesstrainer.Classes;

import android.support.annotation.NonNull;
import android.view.MenuItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Carlos on 27/04/2018.
 */

public class AnyadirRutina implements ValueEventListener, ChildEventListener {
String dia;
String ejercicio;

public AnyadirRutina(String dia,String ejercicio){
this.ejercicio=ejercicio;
this.dia=dia;
}

public void anyadir(){
    DatabaseReference dbRutina = FirebaseDatabase.getInstance()
            .getReference()
            .child("Rutina");
    dbRutina.child("/"+dia+"/"+ejercicio).setValue(false);
}



    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}

