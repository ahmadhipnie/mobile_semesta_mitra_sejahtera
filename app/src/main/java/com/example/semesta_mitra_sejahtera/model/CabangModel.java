package com.example.semesta_mitra_sejahtera.model;

import java.io.Serializable;

public class CabangModel implements Serializable {
    private int id;
    private String nama_peternakan;
    private int kapasitas;
    private String alamat;
    private String no_telp;

    public CabangModel(int id, String nama_peternakan, int kapasitas, String alamat, String no_telp) {
        this.id = id;
        this.nama_peternakan = nama_peternakan;
        this.kapasitas = kapasitas;
        this.alamat = alamat;
        this.no_telp = no_telp;
    }

    public int getId() {
        return id;
    }

    public String getNamaPeternakan() {
        return nama_peternakan;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNoTelp() {
        return no_telp;
    }
}

