package com.sgbuses;


public class DataModel {

    String serv_no;
    String bus_inter1;
    String bus_inter2;
    String bus_inter3;

    public DataModel(){}


    public DataModel(String serv_no, String bus_inter1, String bus_inter2, String bus_inter3) {
        this.serv_no = serv_no;
        this.bus_inter1 = bus_inter1;
        this.bus_inter2 = bus_inter2;
        this.bus_inter3 = bus_inter3;
    }


    public String getServ_no() {
        return serv_no;
    }

    public void setServ_no(String serv_no) {
        this.serv_no = serv_no;
    }

    public String getBus_inter1() {
        return bus_inter1;
    }

    public void setBus_inter1(String bus_inter1) {
        this.bus_inter1 = bus_inter1;
    }

    public String getBus_inter2() {
        return bus_inter2;
    }

    public void setBus_inter2(String bus_inter2) {
        this.bus_inter2 = bus_inter2;
    }

    public String getBus_inter3() {
        return bus_inter3;
    }

    public void setBus_inter3(String bus_inter3) {
        this.bus_inter3 = bus_inter3;
    }
}
