package com.example.mmustpark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    LatLng Kakamega = new LatLng(0.28963, 34.76066);
    LatLng LBB = new LatLng(0.288576, 34.765851);
    LatLng ECA = new LatLng(0.290687, 34.765907);
    LatLng Library = new LatLng(0.289682, 34.763982);
    LatLng SPD = new LatLng(0.287793, 34.765393);


    private OnMapReadyCallback callback = new OnMapReadyCallback() {



        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            for (int i=0; i<arrayList.size();i++){
                googleMap.addMarker(new MarkerOptions().position(arrayList.get(0)).title("LBB Car park"));
                googleMap.addMarker(new MarkerOptions().position(arrayList.get(1)).title("ECA Car park"));
                googleMap.addMarker(new MarkerOptions().position(arrayList.get(2)).title("Library Car park"));
                googleMap.addMarker(new MarkerOptions().position(arrayList.get(3)).title("SPD Car park"));
                //googleMap.animateCamera(CameraUpdateFactory.zoomTo(20.0f));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Kakamega,15.0f));
            }

            //googleMap.addMarker(new MarkerOptions().position(Kakamega).title("Marker in Kakamega"));
            //
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);

            arrayList.add(LBB);
            arrayList.add(ECA);
            arrayList.add(Library);
            arrayList.add(SPD);
        }
    }
}