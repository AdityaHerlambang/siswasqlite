package com.adityaherlambang.siswasqlite.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Tugas {

    int id;
    String nama;
    String deskripsi;
    int status; // 0 = mudah, 1 = sulit

    public Tugas(){}

    public Tugas(String nama, String deskripsi, int status) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.status = status;
    }

    public Tugas(int id, String nama, String deskripsi, int status) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
