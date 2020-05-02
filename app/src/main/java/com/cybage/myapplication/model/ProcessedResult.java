package com.cybage.myapplication.model;

public class ProcessedResult {
    public String volume;
    public String year;

    @Override
    public String toString() {
        return "ProcessedResult{" +
                "volume='" + volume + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
