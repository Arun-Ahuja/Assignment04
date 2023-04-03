//FNU ARUN
package com.example.assignment04;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private  ArrayList<Officials> List = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter adapter;
    private TextView address;
    private Officials current;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int LOCATION_REQUEST = 111;
    private static String locationString = "Unspecified Location";
    private boolean gps_enabled = false;
    private boolean network_enabled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        adapter = new Adapter(List, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        address = findViewById(R.id.txtlocation);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleResult);
        LocationManager lm = (LocationManager) getSystemService(Context. LOCATION_SERVICE ) ;
        gps_enabled = lm.isProviderEnabled(LocationManager. GPS_PROVIDER ) ;
        network_enabled = lm.isProviderEnabled(LocationManager. NETWORK_PROVIDER ) ;

        if(hasNetworkConnection()){
            mFusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(this);
            determineLocation();
            //noConnectionDialog();
        }
        /*else if(hasNetworkConnection()){
            address.setText(R.string.nolocation);
        }*/
        else {
            noConnectionDialog();
        }
        /*if(!hasNetworkConnection()){
            noConnectionDialog();
        }*/

        /*else{
            mFusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(this);
            determineLocation();
        }*/

    }
    private void determineLocation() {
        // Check perm - if not then start the  request and return
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some situations this can be null.
                    if (location != null) {
                        locationString = getPlace(location);
                        address.setText(locationString);
                    }
                })
                .addOnFailureListener(this, e ->
                        Toast.makeText(MainActivity.this,
                                e.getMessage(), Toast.LENGTH_LONG).show());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    determineLocation();
                } else {
                    address.setText(R.string.nolocation);
                }
            }
        }
    }

    private String getPlace(Location loc) {
        StringBuilder sb = new StringBuilder();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        java.util.List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String code = addresses.get(0).getPostalCode();

            sb.append(String.format(
                    Locale.getDefault(),
                    //"%s, %s, %s%n%n%.5f, %.5f",
                    "%s, %s, %s",
                    //city, state,code,  loc.getLatitude(), loc.getLongitude()));
                    city, state,code));

            Toast.makeText(this, "code " +code, Toast.LENGTH_SHORT).show();

            Log.e(TAG, "onCreate: "+code);
            InfoDownloader obj = new InfoDownloader(MainActivity.this,code);
            obj.alldownload(this, code);

        }
        catch (IOException e) {
            Log.e(TAG, "getPlace: "+e );
            e.printStackTrace();
        }

        return sb.toString();
    }

    public void handleResult(ActivityResult result) {
        if (result == null || result.getData() == null) {
            Log.d(TAG, "handleResult: NULL ActivityResult received");
            return;
        }
        Intent data = result.getData();
        if (result.getResultCode() == RESULT_OK) {
            if (data.hasExtra("NEW_NOTE")) {
                Officials n = (Officials) data.getSerializableExtra("NEW_NOTE");
                List.add(n);
                Collections.sort(List);
                adapter.notifyItemRangeChanged(0, List.size());
            } else if (data.hasExtra("EDIT_NOTE")) {
                Officials n = (Officials) data.getSerializableExtra("EDIT_NOTE");
                current.setOfficename(n.getOfficename());
                current.setPersonname(n.getPersonname());
                Collections.sort(List);
                adapter.notifyItemRangeChanged(0, List.size());
            }
        }
      //  setTitle(getString(R.string.app_name) + " [" + List.size() + "]");

    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        current = List.get(pos);
        Intent intent = new Intent(this, OfficialActivity.class);
        intent.putExtra("EDIT_NOTE",current);
        activityResultLauncher.launch(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitem, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){

        if (item.getItemId() == R.id.itemhelp) {
            donothing();
            //activityResultLauncher.launch(intent);
        } else if (item.getItemId() == R.id.itemsearch) {
            dailog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void dailog(){
        if(!hasNetworkConnection()){
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setView(et);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List.clear();
                String ett = et.getText().toString();
                address.setText(ett);
                InfoDownloader down = new InfoDownloader(MainActivity.this,ett);
                down.alldownload(MainActivity.this, ett);
              //  Toast.makeText(MainActivity.this, "Still in progress", Toast.LENGTH_SHORT).show();
            }
        });

        // lambda can be used here (as is below)
        builder.setNegativeButton("Cancel", (dialog, id) -> {
        });
        builder.setTitle("Enter Address");

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void donothing(){
        if (!hasNetworkConnection()){
            return;
        }
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void add(Officials st) {
        List.add(st);
        adapter.notifyItemRangeChanged(0, List.size());
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    private void noConnectionDialog(){
        recyclerView.setVisibility(View.INVISIBLE);
        address.setText(R.string.deniedText);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Network Connection");
        builder.setMessage("Data cannot be accessed/loaded\nWithout an Internet connection ");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean locationEnabled () {
        LocationManager lm = (LocationManager) getSystemService(Context. LOCATION_SERVICE ) ;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager. GPS_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager. NETWORK_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        if (!gps_enabled && !network_enabled) {
            return false;
        }
        else {
            return true;
        }
    }
}
