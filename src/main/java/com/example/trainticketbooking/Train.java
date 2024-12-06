package com.example.trainticketbooking;

import java.io.Serializable;

public class Train implements Serializable {
    private String name;
    private String time;
    private String price3A;
    private String price2A;
    private String price1A;

    public Train(String name, String time, String price3A, String price2A, String price1A) {
        this.name = name;
        this.time = time;
        this.price3A = price3A;
        this.price2A = price2A;
        this.price1A = price1A;
    }

    public String getPrice1A() {
        return price1A;
    }

    public void setPrice1A(String price1A) {
        this.price1A = price1A;
    }

    public String getPrice2A() {
        return price2A;
    }

    public void setPrice2A(String price2A) {
        this.price2A = price2A;
    }

    public String getPrice3A() {
        return price3A;
    }

    public void setPrice3A(String price3A) {
        this.price3A = price3A;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
