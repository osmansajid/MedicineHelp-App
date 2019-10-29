package com.example.tahmid.Medicine_HelpV2;

public class PrescriptionDetails {

    String name,morning,afternoon,night,duration;



    public PrescriptionDetails()
    {

    }

    public PrescriptionDetails(String name,String morning,String afternoon,String night,String duration){
        this.name=name;
        this.morning=morning;
        this.afternoon=afternoon;
        this.night=night;
        this.duration=duration;
    }

    public String getName() {
        return name;
    }

    public String getMorning() {
        return morning;
    }

    public String getAfternoon() {
        return afternoon;
    }

    public String getNight() {
        return night;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public void setAfternoon(String afternoon) {
        this.afternoon = afternoon;
    }

    public void setNight(String night) {
        this.night = night;
    }
}
