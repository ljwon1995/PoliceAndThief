package com.example.policeandthief;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class DecoderInitializer {

    private static final String TAG = "DecoderInitializer!";
    private double range = 0.003;

    public DecoderInitializer(Decoder[] decoders, LatLng cur){
        //set random location!
        HashMap<String, Double> loc = new HashMap<>();

        loc.put("latitude", cur.latitude + range);
        loc.put("longitude", cur.longitude);
        decoders[0].setLocation(loc);

        loc = new HashMap<>();
        loc.put("latitude", cur.latitude - range);
        loc.put("longitude", cur.longitude);
        decoders[1].setLocation(loc);

        loc = new HashMap<>();
        loc.put("latitude", cur.latitude);
        loc.put("longitude", cur.longitude + range);
        decoders[2].setLocation(loc);

        loc = new HashMap<>();
        loc.put("latitude", cur.latitude);
        loc.put("longitude", cur.longitude - range);
        decoders[3].setLocation(loc);

        loc = new HashMap<>();
        loc.put("latitude", cur.latitude);
        loc.put("longitude", cur.longitude);
        decoders[4].setLocation(loc);

    }

}
