package com.sgbuses;

public class BusFirebase {

    public String bus;
    public String tim1;
    public String tim2;




    public BusFirebase() { }

    public BusFirebase(String bus, String tim1, String tim2) {
        this.bus = bus;
        this.tim1 = tim1;
        this.tim2 = tim2;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getTim1() {
        return tim1;
    }

    public void setTim1(String tim1) {
        this.tim1 = tim1;
    }

    public String getTim2() {
        return tim2;
    }

    public void setTim2(String tim2) {
        this.tim2 = tim2;
    }
}