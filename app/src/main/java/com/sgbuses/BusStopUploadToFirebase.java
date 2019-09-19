package com.sgbuses;

public class BusStopUploadToFirebase {

    public String serviceno;
    public String operator;
    public String direction;
    public String stopsequence;
    public String busstopcode;
    public String distance;
    public String roadname;
    public String latitude;
    public String longitude;



    public BusStopUploadToFirebase() { }

    public BusStopUploadToFirebase(String serviceno, String operator, String direction, String stopsequence, String busstopcode, String distance, String roadname, String latitude, String longitude) {
        this.serviceno = serviceno;
        this.operator = operator;
        this.direction = direction;
        this.stopsequence = stopsequence;
        this.busstopcode = busstopcode;
        this.distance = distance;
        this.roadname = roadname;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getServiceno() {
        return serviceno;
    }

    public void setServiceno(String serviceno) {
        this.serviceno = serviceno;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStopsequence() {
        return stopsequence;
    }

    public void setStopsequence(String stopsequence) {
        this.stopsequence = stopsequence;
    }

    public String getBusstopcode() {
        return busstopcode;
    }

    public void setBusstopcode(String busstopcode) {
        this.busstopcode = busstopcode;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRoadname() {
        return roadname;
    }

    public void setRoadname(String roadname) {
        this.roadname = roadname;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}