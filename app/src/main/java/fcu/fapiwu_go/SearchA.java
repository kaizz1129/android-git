package fcu.fapiwu_go;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchA extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getPetsFromFirebase();
    }

    class FirebaseThread extends Thread {

        private DataSnapshot dataSnapshot;

        public FirebaseThread(DataSnapshot dataSnapshot) {
            this.dataSnapshot = dataSnapshot;
        }

        @Override
        public void run() {
            List<Pet> lsPets = new ArrayList<>();
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                DataSnapshot dshsimun = ds.child("hsimun");
                DataSnapshot dsSUMMINPRC = ds.child("summinprc");
                DataSnapshot dsAREA = ds.child("area");
                DataSnapshot dsREGISTENO = ds.child("registeno");

                String hsimun = (String) dshsimun.getValue();
                String SUMMINPRC = (String) dsSUMMINPRC.getValue();
                String AREA = (String) dsAREA.getValue();
                String REGISTENO = (String) dsREGISTENO.getValue();


                Pet aPet = new Pet();
                aPet.setHsimun(hsimun);
                aPet.setAREA(AREA);
                aPet.setREGISTENO(REGISTENO);
                aPet.setSUMMINPRC(SUMMINPRC);
                lsPets.add(aPet);
                Log.v("地區:", hsimun + "面積:" + AREA + "價錢" + SUMMINPRC + "地址" + REGISTENO);
            }

        }
    }

    private void getPetsFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new FirebaseThread(dataSnapshot).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("AdoptPet", databaseError.getMessage());
            }
        });
    }

}
