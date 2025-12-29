package com.safetynet.alerts.dto;

public class FirestationRequestDTO {
    private String address;
    private int station;

    public FirestationRequestDTO() {}

    public FirestationRequestDTO(int station,String address) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }
}
