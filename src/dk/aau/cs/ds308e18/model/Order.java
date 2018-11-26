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
    private int id;
    private String returordreReference;
    private String ekspeditionsStatus;
    private String customerName;
    private String modtagelsesDato;
    private String address;
    private long zipCode;
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
        id = 0;
        returordreReference = "";
        ekspeditionsStatus = "";
        customerName = "";
        modtagelsesDato = "";
        address = "";
        zipCode = 0;
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

    public Order(long plukrute, int id, String returordreReference, String ekspeditionsStatus, String customerName,
                 String modtagelsesDato, String address, long zipCode, long receipt, boolean afhentning,
                 String sfhentningsDato, int leveringsUge, String lagerSted, String ordreKategori, String fleetOwner,
                 boolean udskrevet, String rute, boolean FV, String projekt) {
        this.plukrute = plukrute;
        this.id = id;
        this.returordreReference = returordreReference;
        this.ekspeditionsStatus = ekspeditionsStatus;
        this.customerName = customerName;
        this.modtagelsesDato = modtagelsesDato;
        this.address = address;
        this.zipCode = zipCode;
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

    public int getID() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public long getZipCode() {
        return zipCode;
    }
}