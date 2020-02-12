package com.google.android.gms.location.sample.locationupdates.FirebaseDB;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.Map;

public class FirebaseDB implements OnSuccessListener<DocumentReference>, OnFailureListener, OnCompleteListener<QuerySnapshot>
{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context context;

    public FirebaseDB(Context context)
    {
        this.context = context;
    }

    public void añadirUsuario(String user, String uid)
    {
        Map<String, String> doc = new HashMap<>();

        doc.put("User", user);
        doc.put("UID", uid);

        db.collection("Users")
                .add(doc);
    }

    public void getAllPoints(OnCompleteListener onCompleteListener)
    {
        db.collection("Points")
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

    public void getUserPoints(String uid, OnCompleteListener onCompleteListener)
    {
        db.collection("Points")
                .whereEqualTo("User", uid)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

    public void añadirPuntos(String user, int points, LatLng latLng)
    {
        Map<String, Object> doc = new HashMap<>();

        doc.put("User", user);
        doc.put("Points", points);
        doc.put("Latitude", latLng.latitude);
        doc.put("Longitude", latLng.longitude);

        db.collection("Points")
                .add(doc)
                .addOnFailureListener(this);
    }

    @Override
    public void onSuccess(DocumentReference documentReference)
    {
        Toast.makeText(context, "Document added: " + documentReference.getId(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(@NonNull Exception e)
    {
        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task)
    {
        if(task.isSuccessful())
        {
            for (QueryDocumentSnapshot document : task.getResult())
            {
                Toast.makeText(context, document.get("Mensaje").toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
