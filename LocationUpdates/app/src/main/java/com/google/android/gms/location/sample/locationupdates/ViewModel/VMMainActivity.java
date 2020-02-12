package com.google.android.gms.location.sample.locationupdates.ViewModel;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.sample.locationupdates.FirebaseDB.FirebaseDB;
import com.google.android.gms.location.sample.locationupdates.Point;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;
//import com.google.type.LatLng;

import java.util.ArrayList;

public class VMMainActivity extends AndroidViewModel
{
    private Location currentLocation;
    private MutableLiveData<ArrayList<Point>> points = new MutableLiveData<>(new ArrayList<Point>());
    private FirebaseUser currentUser;

    public VMMainActivity(@NonNull Application application)
    {
        super(application);
    }

    public Location getCurrentLocation()
    {
        return currentLocation;
    }

    public ArrayList<Point> getPoints()
    {
        //return points;
        return points.getValue();
    }

    public void setCurrentLocation(Location currentLocation)
    {
        this.currentLocation = currentLocation;
    }

    public void setPoints(ArrayList<Point> points)
    {
        //this.points = points;
        this.points.setValue(points);
    }

    public void addPoint(Point point)
    {
        if(points != null)
            points.getValue().add(point);

        points.setValue(points.getValue());
    }

    public Point getPointByID(String pointID)
    {
        Point point = null;

        for(int i = 0 ; i < points.getValue().size() && point == null ; i++)
        {
            if(points.getValue().get(i).getID().equals(pointID))
            {
                point = points.getValue().get(i);
            }
        }

        return point;
    }

    public boolean removePoint(String pointID)
    {
        boolean removed = false;

        for(int i = 0 ; i < points.getValue().size() && removed == false ; i++)
        {
            if(points.getValue().get(i).getID().equals(pointID))
            {
                points.getValue().remove(i);
                removed = true;
            }
        }

        if(removed) points.setValue(points.getValue());

        return removed;
    }

    public FirebaseUser getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(FirebaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public void sumarPuntos(int pointsToAdd, LatLng latLng)
    {
        if(currentUser != null)
        {
            new FirebaseDB(getApplication()).añadirPuntos(currentUser.getUid(), pointsToAdd, latLng);
        }
    }
}
