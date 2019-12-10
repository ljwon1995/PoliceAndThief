package com.example.policeandthief;

public class RoomItem {

    private String roomName;
    private String masterId;
    private int persons;
    private String policeId;
    private String thiefId;

    public RoomItem(){

    }

    public RoomItem(String rn, String mi, int per, String pi){
        roomName = rn;
        masterId = mi;
        persons = per;
        policeId = pi;
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
}
