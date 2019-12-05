package com.example.policeandthief;

import java.util.ArrayList;
import java.util.HashMap;

public class Decoder {

    private ArrayList<String> texts;
    private int progress;
    private HashMap<String, Double> location;

    public Decoder(){
        texts = new ArrayList<>();

        texts.add("동해물과 백두산이 마르고 닳도록");
        texts.add("하느님이 보우하사 우리나라 만세");
        texts.add("무궁화 삼천리 화려강산");
        texts.add("대한사람 대한으로 길이 보전하세");


        progress = 0;

    }

    public ArrayList<String> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<String> texts) {
        this.texts = texts;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public HashMap<String, Double> getLocation() {
        return location;
    }

    public void setLocation(HashMap<String, Double> location) {
        this.location = location;
    }
}
