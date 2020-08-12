package com.kptrafficpolice.trafficapp.Model;

public class OffenceModel {
    String offence_title,bike_offence_fee,car_offence_fee,jeep_offence_fee,truck_offence_fee,bus_offence_fee
            ,trolley_offence_fee;

    public OffenceModel(String offence_title, String bike_offence_fee, String car_offence_fee, String jeep_offence_fee,
                        String truck_offence_fee, String bus_offence_fee, String trolley_offence_fee) {
        this.offence_title = offence_title;
        this.bike_offence_fee = bike_offence_fee;
        this.car_offence_fee = car_offence_fee;
        this.jeep_offence_fee = jeep_offence_fee;
        this.truck_offence_fee = truck_offence_fee;
        this.bus_offence_fee = bus_offence_fee;
        this.trolley_offence_fee = trolley_offence_fee;
    }

    public OffenceModel() {
    }

    public String getOffence_title() {
        return offence_title;
    }

    public void setOffence_title(String offence_title) {
        this.offence_title = offence_title;
    }

    public String getBike_offence_fee() {
        return bike_offence_fee;
    }

    public void setBike_offence_fee(String bike_offence_fee) {
        this.bike_offence_fee = bike_offence_fee;
    }

    public String getCar_offence_fee() {
        return car_offence_fee;
    }

    public void setCar_offence_fee(String car_offence_fee) {
        this.car_offence_fee = car_offence_fee;
    }

    public String getJeep_offence_fee() {
        return jeep_offence_fee;
    }

    public void setJeep_offence_fee(String jeep_offence_fee) {
        this.jeep_offence_fee = jeep_offence_fee;
    }

    public String getTruck_offence_fee() {
        return truck_offence_fee;
    }

    public void setTruck_offence_fee(String truck_offence_fee) {
        this.truck_offence_fee = truck_offence_fee;
    }

    public String getBus_offence_fee() {
        return bus_offence_fee;
    }

    public void setBus_offence_fee(String bus_offence_fee) {
        this.bus_offence_fee = bus_offence_fee;
    }

    public String getTrolley_offence_fee() {
        return trolley_offence_fee;
    }

    public void setTrolley_offence_fee(String trolley_offence_fee) {
        this.trolley_offence_fee = trolley_offence_fee;
    }
}
