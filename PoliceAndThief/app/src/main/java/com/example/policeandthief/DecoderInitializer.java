package com.example.policeandthief;

import android.util.Log;

import java.util.HashMap;

public class DecoderInitializer {

    private static final String TAG = "DecoderInitializer!";

    public DecoderInitializer(Decoder[] decoders){
        //set random location!
        HashMap<String, Double> loc = new HashMap<>();
        loc.put("latitude", 120.5035);
        loc.put("longitude", 126.956949);
        decoders[0].setLocation(loc);

        loc = new HashMap<>();
        loc.put("latitude", 120.5035);
        loc.put("longitude", 126.956949);
        decoders[1].setLocation(loc);

        loc = new HashMap<>();
        loc.put("latitude", 37.5037587);
        loc.put("longitude", 126.9567754);
        decoders[2].setLocation(loc);

        loc = new HashMap<>();
        loc.put("latitude", 120.5035);
        loc.put("longitude", 126.956949);
        decoders[3].setLocation(loc);

        loc = new HashMap<>();
        loc.put("latitude", 120.5035);
        loc.put("longitude", 126.956949);
        decoders[4].setLocation(loc);

    }

}
