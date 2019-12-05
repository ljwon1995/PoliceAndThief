package com.example.policeandthief;

import java.util.HashMap;

public class DecoderInitializer {

    public DecoderInitializer(Decoder[] decoders){
        //set random location!
        for(int i = 0; i < 5; i++){
            HashMap<String, Double> loc = new HashMap<>();
            loc.put("latitude", 40.5035);
            loc.put("longitude", 126.956949);
            decoders[i].setLocation(loc);
        }
    }

}
