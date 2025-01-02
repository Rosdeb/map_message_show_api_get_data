package com.example.coffee.fragments;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coffee.MainActivity;
import com.example.coffee.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Map extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    FusedLocationProviderClient fusedLocationClient;
    SearchView searchView;
    ImageButton imageButton;
    private LinearLayout selectedLayout = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        searchView = view.findViewById(R.id.search);
        searchView = view.findViewById(R.id.search);
        CircleImageView  circleImageView= view.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottonsheet();
            }
        });

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                if (query != null && !query.isEmpty()) {
                    searchLocation(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;

    }


    private void bottonsheet() {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet);

        LinearLayout defa=  dialog.findViewById(R.id.defaul);
        LinearLayout sate=  dialog.findViewById(R.id.satelite);
        LinearLayout terrain=  dialog.findViewById(R.id.terrain);
        LinearLayout hybird=  dialog.findViewById(R.id.hybrid);

        defa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


            }
        });
        sate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            }
        });
        terrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

            }
        });
        hybird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomSheetsyle;
        dialog.getWindow().setGravity(Gravity.BOTTOM);



    }

    private void setItemSelectedListener(LinearLayout layout) {
        layout.setOnClickListener(v -> {
            // Reset previous selection
            if (selectedLayout != null) {
                selectedLayout.setSelected(false);
            }

            // Set new selection
            layout.setSelected(true);
            selectedLayout = layout;

            // Handle map type selection here (e.g., callback or setting map type)
        });
    }
    private void searchLocation(String location) {
        Geocoder geocoder = new Geocoder(requireContext());
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 1);
            if (addresses == null || addresses.isEmpty()) {
                Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show();
                return;
            }

            Address address = addresses.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            // Move the map and add a marker
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title(location));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error searching location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        getCurrentLocation();

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location").
                                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16));
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng));


                        }
                    });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }
}