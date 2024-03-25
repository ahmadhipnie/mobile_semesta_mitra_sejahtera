package com.example.semesta_mitra_sejahtera.model;

public class PerkembanganModel {
    private int id;
    private String tanggal;
    private int mingguKe;
    private double pakanPakai;
    private double pakanSisa;
    private double bobot;
    private int afkir;
    private int kematian;
    private String namaPeternakan;
    private String alamat;
    private String createdAt;
    private String updatedAt;

        public PerkembanganModel(int id, String tanggal, int mingguKe, double pakanPakai, double pakanSisa, double bobot, int afkir, int kematian, String namaPeternakan, String alamat, String createdAt, String updatedAt) {
        this.id = id;
        this.tanggal = tanggal;
        this.mingguKe = mingguKe;
        this.pakanPakai = pakanPakai;
        this.pakanSisa = pakanSisa;
        this.bobot = bobot;
        this.afkir = afkir;
        this.kematian = kematian;
        this.namaPeternakan = namaPeternakan;
        this.alamat = alamat;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;


    }  public int getId() {
        return id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getMingguKe() {
        return mingguKe;
    }

    public double getPakanPakai() {
        return pakanPakai;
    }

    public double getPakanSisa() {
        return pakanSisa;
    }

    public double getBobot() {
        return bobot;
    }

    public int getAfkir() {
        return afkir;
    }

    public int getKematian() {
        return kematian;
    }

    public String getNamaPeternakan() {
        return namaPeternakan;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
