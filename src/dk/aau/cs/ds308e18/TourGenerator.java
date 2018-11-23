package dk.aau.cs.ds308e18;

import java.time.LocalDate;

public class TourGenerator {
    LocalDate date;
    String region;

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void setRegion(String region){
        this.region = region;
    }

    public void generateTours(){
        if (region != null)
            System.out.println("[Region = " + region + "]");
        if (date != null)
            System.out.println("[Date = " + date + "]");
        System.out.println("Generating tour(s)...");
    }
}
