package com.example.policeandthief;

import android.location.Location;


import java.util.HashMap;

public class DistanceManager {

    private AllLocation allLoc;

    public DistanceManager(AllLocation loc){
        allLoc = loc;
    }

    public HashMap<String, Float> getDistance(){
        HashMap<String, Float> ans = new HashMap<>();
        float toPolice, toDecoder;
        Location thief = new Location("Thief");
        Location police = new Location("Police");
        Location decoder = new Location("Decoder");

        thief.setLatitude(allLoc.getThief().get("latitude"));
        thief.setLongitude(allLoc.getThief().get("longitude"));

        police.setLatitude(allLoc.getPolice().get("latitude"));
        police.setLongitude(allLoc.getPolice().get("longitude"));

        decoder.setLatitude(allLoc.getDecoder().get("latitude"));
        decoder.setLongitude(allLoc.getDecoder().get("longitude"));

        toPolice = thief.distanceTo(police);
        toDecoder = thief.distanceTo(decoder);




        ans.put("toPolice", toPolice);
        ans.put("toDecoder", toDecoder);

        return ans;
    }
}
