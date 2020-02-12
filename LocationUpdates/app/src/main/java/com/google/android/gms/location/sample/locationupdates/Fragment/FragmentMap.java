package com.google.android.gms.location.sample.locationupdates.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.sample.locationupdates.BuildConfig;
import com.google.android.gms.location.sample.locationupdates.FirebaseDB.FirebaseDB;
import com.google.android.gms.location.sample.locationupdates.Map;
import com.google.android.gms.location.sample.locationupdates.Point;
import com.google.android.gms.location.sample.locationupdates.R;
import com.google.android.gms.location.sample.locationupdates.Type;
import com.google.android.gms.location.sample.locationupdates.ViewModel.VMMainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FragmentMap extends SupportMapFragment implements OnMapReadyCallback
{
    private static final String TAG = FragmentMap.class.getSimpleName();

    private static final int RANGE = 250;

    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * Constant used in the location settings dialog.
     */
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Provides access to the Location Settings API.
     */
    private SettingsClient mSettingsClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private LocationSettingsRequest mLocationSettingsRequest;

    /**
     * Callback for Location events.
     */
    private LocationCallback mLocationCallback;

    /**
     * Represents a geographical location.
     */
    //private Location mCurrentLocation;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    private Boolean mRequestingLocationUpdates;

    /**
     * Time when the location was updated represented as a String.
     */
    private String mLastUpdateTime;

    private GoogleMap mMap;

    private Circle circle;

    private Marker mMarker;

    private LatLng ltlng;

    //private ArrayList<Marker> markers;

    private View mView;

    private boolean primeraVez;

    private VMMainActivity vm;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(bundle);

        vm = ViewModelProviders.of(getActivity()).get(VMMainActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle)
    {
        mView = super.onCreateView(layoutInflater, viewGroup, bundle);

        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";


        vm.setPoints(new ArrayList<Point>());



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mSettingsClient = LocationServices.getSettingsClient(getActivity());

        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        new FirebaseDB(getContext()).getUserPoints(vm.getCurrentUser().getUid(), new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                    long puntos = 0;
                    for(QueryDocumentSnapshot document : task.getResult())
                    {
                        //if(document.get("User").equals(vm.getCurrentUser().getUid()))
                        puntos += (long)document.get("Points");
                    }

                    Toast.makeText(getContext(), "Tienes un total de " + puntos +  " puntos", Toast.LENGTH_LONG).show();
                }
            }
        });

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        primeraVez = true;

        if (checkPermissions())
        {
            startLocationUpdates();
        } else if (!checkPermissions())
        {
            requestPermissions();
        }
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                vm.setCurrentLocation((Location)savedInstanceState.getParcelable(KEY_LOCATION));
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING))
            {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }
        }
    }

    private void createLocationRequest()
    {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback()
    {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if( circle != null)
                    circle.remove();

                if (mMarker != null)
                    mMarker.remove();



                ltlng = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());

                MarkerOptions marker = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker());

                marker.position(ltlng);
                marker.title("Marker");

                mMarker = mMap.addMarker(marker);

                if(vm.getPoints().size() < 4)
                    generateNewMarker();


                Point pointABorrar = null;
                double distanciaEnKm;
                boolean borrar = false;
                for(Point p : vm.getPoints())
                {
                    distanciaEnKm = distanciaEnKM(ltlng.latitude, ltlng.longitude, p.getMarker().getPosition().latitude, p.getMarker().getPosition().longitude);

                    if(distanciaEnKm * 1000 < 20)
                    {
                        Toast.makeText(getActivity(), "Encontraste un punto! +10 puntos", Toast.LENGTH_LONG).show();
                        vm.sumarPuntos(10, p.getMarker().getPosition());
                        pointABorrar = p;
                        borrar = true;
                    }
                }

                if(borrar && pointABorrar != null)
                {
                    pointABorrar.getMarker().remove();
                    pointABorrar.getCircleRange().remove();
                    vm.removePoint(pointABorrar.getID());

                    borrar = false;
                }

                if(primeraVez)
                {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltlng, 15.0f));
                    primeraVez = false;
                }

                circle = drawCircle(ltlng, RANGE, 0x3066ffff);
            }
        };
    }

    private double gradosARadianes(double grados)
    {
        return grados * 3.1416 / 180;
    }

    private double distanciaEnKM(double lat1, double long1, double lat2, double long2)
    {
        int radioTierraKM = 6371;

        double dLat = gradosARadianes(lat2-lat1);
        double dLon = gradosARadianes(long2-long1);

        lat1 = gradosARadianes(lat1);
        lat2 = gradosARadianes(lat2);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return radioTierraKM * c;
    }

    private void generateNewMarker()
    {
        double lat;
        double lng;
        Type type;

        //distancia aleatoria limitada al rango
        double distancia = ((0.001 * RANGE) / 111.32) * Math.random();

        //Porcentaje que le pertenece a cada tipo de coordenada
        int porcentajeLat = (int)(Math.random()*100 + 1);
        int porcentajeLong = 100-porcentajeLat;

        //indica si se le suma o se le resta esa distancia a la latitud y longitud dada (centro)
        if(Math.random() > 0.5)
            lat = ltlng.latitude + ((distancia * porcentajeLat) / 100);
        else
            lat = ltlng.latitude - ((distancia * porcentajeLat) / 100);

        if(Math.random() < 0.5)
            lng = ltlng.longitude + ((distancia * porcentajeLong) / 100);
        else
            lng = ltlng.longitude - ((distancia * porcentajeLong) / 100);

        type = Type.randomType();

        MarkerOptions mark = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getActivity().getResources(), Type.getDrawableIDByType(type))));

        mark.position(new LatLng(lat, lng));
        mark.title(type.name());

        Point pt = new Point(mMap.addMarker(mark), type, type.name(), 10, drawCircle(new LatLng(lat, lng), 20, 0x30ff0000));

        vm.addPoint(pt);
    }

    private Circle drawCircle(LatLng point, int rangeInMeters, int color)
    {
        CircleOptions circleOptions = new CircleOptions();

        circleOptions.center(point);

        circleOptions.radius(rangeInMeters);

        circleOptions.strokeColor(Color.BLACK);

        circleOptions.fillColor(color);

        circleOptions.strokeWidth(2);

        Circle cir = mMap.addCircle(circleOptions);

        return cir;
    }

    private void buildLocationSettingsRequest()
    {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();

        builder.addLocationRequest(mLocationRequest);

        mLocationSettingsRequest = builder.build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        //updateUI();
                        break;
                }
                break;
        }
    }

    public void startUpdatesButtonHandler(View view)
    {
        if (!mRequestingLocationUpdates)
        {
            mRequestingLocationUpdates = true;
            //setButtonsEnabledState();
            startLocationUpdates();
        }
    }

    public void stopUpdatesButtonHandler(View view) {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        stopLocationUpdates();
    }

    private void startLocationUpdates()
    {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>()
                {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse)
                    {
                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                    }
                })
                .addOnFailureListener(getActivity(), new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode)
                        {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                try
                                {
                                    ResolvableApiException rae = (ResolvableApiException) e;

                                    rae.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                                }
                                catch (IntentSender.SendIntentException sie)
                                {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;

                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                                String errorMessage = "Location settings are inadequate, and cannot be " +          //TODO Meter esto en values y traducir al español
                                        "fixed here. Fix in Settings.";

                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }
                    }
                });
    }


    private void stopLocationUpdates()
    {
        if (mRequestingLocationUpdates)
        {
            // It is a good practice to remove location requests when the activity is in a paused or
            // stopped state. Doing so helps battery performance and is especially
            // recommended in applications that request frequent location updates.
            mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            mRequestingLocationUpdates = false;
                        }
                    });
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        // En {@code onPause()}, se quita la actualización de localización. Aquí, se vuelve a
        // actualizar la ubicación si se ha solicitado.
        if (mRequestingLocationUpdates && checkPermissions())
        {
            startLocationUpdates();
        } else if (!checkPermissions())
        {
            requestPermissions();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();

        // Para de actualizar la ubicación.
        stopLocationUpdates();
    }


    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(KEY_LOCATION, vm.getCurrentLocation());
        savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId, View.OnClickListener listener)
    {
        Snackbar.make(
                mView,
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private boolean checkPermissions()
    {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions()
    {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale)
        {
            showSnackbar(R.string.permission_rationale, android.R.string.ok, new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // Request permission
                    requestPermissions(
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_PERMISSIONS_REQUEST_CODE);
                }
            });
        }
        else
        {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
           /*ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE); */

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE)
        {
            if (grantResults.length <= 0)
            {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            }
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                startLocationUpdates();
            }
        }
        else
        {
            // Permission denied.

            // Notify the user via a SnackBar that they have rejected a core permission for the
            // app, which makes the Activity useless. In a real app, core permissions would
            // typically be best requested during a welcome-screen flow.

            // Additionally, it is important to remember that a permission might have been
            // rejected without asking the user for permission (device policy or "Never ask
            // again" prompts). Therefore, a user interface affordance is typically implemented
            // when permissions are denied. Otherwise, your app could appear unresponsive to
            // touches or interactions which have required permissions.
            showSnackbar(R.string.permission_denied_explanation,
                    R.string.settings, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Build intent that displays the App settings screen.
                            Intent intent = new Intent();
                            intent.setAction(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
        }
    }
}
