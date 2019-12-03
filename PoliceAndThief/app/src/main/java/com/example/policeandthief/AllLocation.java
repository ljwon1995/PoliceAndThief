package com.example.policeandthief;

import java.util.HashMap;

public class AllLocation {
    private HashMap<String, Double> Police;
    private HashMap<String, Double> Thief;
    private HashMap<String, Double> Decoder;

    public HashMap<String, Double> getPolice() {
        return Police;
    }

    public void setPolice(HashMap<String, Double> police) {
        Police = police;
    }

    public HashMap<String, Double> getThief() {
        return Thief;
    }

    public void setThief(HashMap<String, Double> thief) {
        Thief = thief;
    }

    public HashMap<String, Double> getDecoder() {
        return Decoder;
    }

    public void setDecoder(HashMap<String, Double> decoder) {
        Decoder = decoder;
    }
}
