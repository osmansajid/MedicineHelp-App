package com.example.tahmid.Medicine_HelpV2;

import java.io.Serializable;

public class Medinfosearch implements Serializable {
    String s1,s2,s3,s4,s5;

    public Medinfosearch() {
        ////
    }

    public Medinfosearch(String s1, String s2, String s3, String s4, String s5) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
    }



    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }

    public String getS3() {
        return s3;
    }

    public String getS4() {
        return s4;
    }

    public String getS5() {
        return s5;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }

    public void setS5(String s5) {
        this.s5 = s5;
    }
}
