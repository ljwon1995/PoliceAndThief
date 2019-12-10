package com.example.policeandthief;

public class RoomItem {

    private String roomName;
    private String masterId;
    private int persons;
    private String policeId;
    private String thiefId;
    private int gameStart;

    public RoomItem(){

    }

    public RoomItem(String rn, String mi, int per, String pi){
        roomName = rn;
        masterId = mi;
        persons = per;
        policeId = pi;
        gameStart = 0;
    }

    public int getGameStart() {
        return gameStart;
    }

    public void setGameStart(int gameStart) {
        this.gameStart = gameStart;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public String getPoliceId() {
        return policeId;
    }

    public void setPoliceId(String policeId) {
        this.policeId = policeId;
    }

    public String getThiefId() {
        return thiefId;
    }

    public void setThiefId(String thiefId) {
        this.thiefId = thiefId;
    }
}
