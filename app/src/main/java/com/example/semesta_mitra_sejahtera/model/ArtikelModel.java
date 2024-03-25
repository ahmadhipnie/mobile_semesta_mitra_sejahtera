package com.example.semesta_mitra_sejahtera.model;

import java.io.Serializable;

public class ArtikelModel implements Serializable {

    int id;
    String judul;
    String kategori;
    String deskripsi;

    public ArtikelModel (int id, String judul, String kategori, String deskripsi) {
        this.id = id;
        this.judul = judul;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getKategori() {
        return kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
}
