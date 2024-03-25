package com.example.semesta_mitra_sejahtera.model;
public class JadwalPanenModel {
    private int id;
    private String tanggal;
    private int jumlahAyam;
    private int bobotAyam;
    private String namaSopir;
    private String nopol;
    private String namaPeternakan;




    public JadwalPanenModel(int id, String tanggal, int jumlahAyam, int bobotAyam, String namaSopir, String nopol, String namaPeternakan) {
        this.id = id;
        this.tanggal = tanggal;
        this.jumlahAyam = jumlahAyam;
        this.bobotAyam = bobotAyam;
        this.namaSopir = namaSopir;
        this.nopol = nopol;
        this.namaPeternakan = namaPeternakan;

    }

    public int getId() {
        return id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getJumlahAyam() {
        return jumlahAyam;
    }

    public int getBobotAyam() {
        return bobotAyam;
    }

    public String getNamaSopir() {
        return namaSopir;
    }

    public String getNopol() {
        return nopol;
    }

    public String getNamaPeternakan() {
        return namaPeternakan;
    }

}
