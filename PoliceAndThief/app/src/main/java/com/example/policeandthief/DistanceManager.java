package com.example.policeandthief;

import android.location.Location;


import java.util.HashMap;

public class DistanceManager {

    private AllLocation allLoc;
    private Decoder[] decoders;

    public DistanceManager(AllLocation loc, Decoder[] decs){
        allLoc = loc;
        this.decoders = decs;
    }

    public HashMap<String, Float> getDistance(){


        HashMap<String, Float> ans = new HashMap<>();
        float toPolice;
        float[] toDecoder;

        toDecoder = new float[5];
        Location thief = new Location("Thief");
        Location police = new Location("Police");
        Location[] decoderLocs = new Location[5];

        for(int i = 0; i < 5; i++){
            decoderLocs[i] = new Location("Decoder"+i);
        }

        thief.setLatitude(allLoc.getThief().get("latitude"));
        thief.setLongitude(allLoc.getThief().get("longitude"));

        police.setLatitude(allLoc.getPolice().get("latitude"));
        police.setLongitude(allLoc.getPolice().get("longitude"));

        toPolice = thief.distanceTo(police);
        ans.put("toPolice", toPolice);

        for(int i = 0; i < 5; i++){

            decoderLocs[i].setLatitude(decoders[i].getLocation().get("latitude"));
            decoderLocs[i].setLongitude(decoders[i].getLocation().get("longitude"));
            toDecoder[i] = thief.distanceTo(decoderLocs[i]);
            ans.put("toDecoder"+i, toDecoder[i]);

        }





        return ans;
    }
}
