package com.google.android.gms.location.sample.locationupdates;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.UUID;

public class Point
{


    private String ID;
    private Marker marker;
    private MarkerOptions markerOptions;
    private Type type;
    private String description;
    private double distance;

    public void setID(String ID) {
        this.ID = ID;
    }

    public Circle getCircleRange() {
        return circleRange;
    }

    public void setCircleRange(Circle circleRange) {
        this.circleRange = circleRange;
    }

    private Circle circleRange;

    public Point(Marker marker, Type type, String description, double distance, Circle circleRange)
    {
        this.ID = UUID.randomUUID().toString();
        this.marker = marker;
        this.type = type;
        this.description = description;
        this.distance = distance;
        this.circleRange = circleRange;
    }



    /*
    public Point(MarkerOptions markerOptions, String type, String description, double distance)
    {
        this.ID = UUID.randomUUID().toString();
        this.markerOptions = markerOptions;
        this.type = type;
        this.description = description;
        this.distance = distance;

        this.marker = new Marker(markerOptions)
    } */

    public String getID() {
        return ID;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }

    public void setMarkerOptions(MarkerOptions markerOptions) {
        this.markerOptions = markerOptions;
    }
}
