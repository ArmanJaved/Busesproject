package com.sgbuses;


public class DataModel {

    String serv_no;
    String bus_inter1;
    String bus_inter2;
    String bus_inter3;
    String bus1load;
    String bus2load;
    String bus3load;


    public DataModel(){}

    public DataModel(String serv_no, String bus_inter1, String bus_inter2, String bus_inter3, String bus1load, String bus2load, String bus3load) {
        this.serv_no = serv_no;
        this.bus_inter1 = bus_inter1;
        this.bus_inter2 = bus_inter2;
        this.bus_inter3 = bus_inter3;
        this.bus1load = bus1load;
        this.bus2load = bus2load;
        this.bus3load = bus3load;
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

    public String getBus1load() {
        return bus1load;
    }

    public void setBus1load(String bus1load) {
        this.bus1load = bus1load;
    }

    public String getBus2load() {
        return bus2load;
    }

    public void setBus2load(String bus2load) {
        this.bus2load = bus2load;
    }

    public String getBus3load() {
        return bus3load;
    }

    public void setBus3load(String bus3load) {
        this.bus3load = bus3load;
    }
}
