package com.example.policeandthief;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Decoder implements Serializable {

    private ArrayList<String> texts;
    private int progress;
    private HashMap<String, Double> location;
    private boolean isActive;
    private boolean[] isDone;


    public Decoder(){
        texts = new ArrayList<>();

        texts.add("동해물과 백두산이 마르고 닳도록");
        texts.add("하느님이 보우하사 우리나라 만세");
        texts.add("무궁화 삼천리 화려강산");
        texts.add("대한사람 대한으로 길이 보전하세");

        isActive = false;


        progress = 0;
        isDone = new boolean[4];
        for(int i = 0; i < 4; i++){
            isDone[i] = false;
        }

    }

    public boolean[] getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean[] isDone) {
        this.isDone = isDone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
