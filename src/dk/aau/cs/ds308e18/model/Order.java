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

    private long plukrute;
    private String order;
    private String returordreReference;
    private String ekspeditionsStatus;
    private String navn;
    private String modtagelsesDato;
    private String gadeNavn;
    private long postNummer;
    private long receipt;
    private boolean afhentning;
    private String sfhentningsDato;
    private int leveringsUge;
    private String lagerSted;
    private String ordreKategori;
    private String fleetOwner;
    private boolean udskrevet;
    private String rute;
    private boolean FV;
    private String projekt;

    public Order() {
        plukrute = 0;
        order = "";
        returordreReference = "";
        ekspeditionsStatus = "";
        navn = "";
        modtagelsesDato = "";
        gadeNavn = "";
        postNummer = 0;
        receipt = 0;
        afhentning = false;
        sfhentningsDato = "";
        leveringsUge = 0;
        lagerSted = "";
        ordreKategori = "";
        fleetOwner = "";
        udskrevet = false;
        rute = "";
        FV = false;
        projekt = "";
    }

    public Order(long plukrute, String order, String returordreReference, String ekspeditionsStatus, String navn,
                 String modtagelsesDato, String gadeNavn, long postNummer, long receipt, boolean afhentning,
                 String sfhentningsDato, int leveringsUge, String lagerSted, String ordreKategori, String fleetOwner,
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
        this.sfhentningsDato = sfhentningsDato;
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