package com.example.policeandthief;

import android.location.Location;

import java.util.HashMap;

public class PoliceDistanceManager {

    private AllLocation allLoc;

    public PoliceDistanceManager(AllLocation loc){
        allLoc = loc;
    }

    public HashMap<String, Float> getDistance(){


        HashMap<String, Float> ans = new HashMap<>();
        float toThief;

        Location thief = new Location("Thief");
        Location police = new Location("Police");

        thief.setLatitude(allLoc.getThief().get("latitude"));
        thief.setLongitude(allLoc.getThief().get("longitude"));

        police.setLatitude(allLoc.getPolice().get("latitude"));
        police.setLongitude(allLoc.getPolice().get("longitude"));

        toThief = police.distanceTo(thief);
        ans.put("toThief", toThief);

        return ans;
    }
}
