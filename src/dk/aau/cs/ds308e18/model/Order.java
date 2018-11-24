package dk.aau.cs.ds308e18.model;

import java.util.ArrayList;

public class Order {
    ArrayList<Ware> wares;

    /*
    String adress;

    String moreInfo;

    long orderNumber;

    long zipCode;

    long receiptNumber;

    float receiveDate;

    float pickupDate;

    float timeRequired;
    */

    public long plukrute;
    public String order;
    public String returordreReference;
    public String ekspeditionsStatus;
    public String navn;
    public String modtagelsesDato;
    public String gadeNavn;
    public long postNummer;
    public long receipt;
    public boolean afhentning;
    public String afhentningsDato;
    public int leveringsUge;
    public String lagerSted;
    public String ordreKategori;
    public String fleetOwner;
    public boolean udskrevet;
    public String rute;
    public boolean FV;
    public String projekt;

    public Order(long plukrute, String order, String returordreReference, String ekspeditionsStatus, String navn,
                 String modtagelsesDato, String gadeNavn, long postNummer, long receipt, boolean afhentning,
                 String afhentningsDato, int leveringsUge, String lagerSted, String ordreKategori, String fleetOwner,
                 boolean udskrevet, String rute, boolean FV, String projekt) {
        this.plukrute = plukrute;
        this.order = order;
        this.returordreReference = returordreReference;
        this.ekspeditionsStatus = ekspeditionsStatus;
        this.navn = navn;
        this.modtagelsesDato = modtagelsesDato;
        this.gadeNavn = gadeNavn;
        this.postNummer = postNummer;
        this.receipt = receipt;
        this.afhentning = afhentning;
        this.afhentningsDato = afhentningsDato;
        this.leveringsUge = leveringsUge;
        this.lagerSted = lagerSted;
        this.ordreKategori = ordreKategori;
        this.fleetOwner = fleetOwner;
        this.udskrevet = udskrevet;
        this.rute = rute;
        this.FV = FV;
        this.projekt = projekt;
    }
}