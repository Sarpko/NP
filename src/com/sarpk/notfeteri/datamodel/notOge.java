package com.sarpk.notfeteri.datamodel;

import java.time.LocalDate;

public class notOge {

    private String baslık;
    private String detay;
    private LocalDate bitisTarih;

    public notOge(String baslık, String detay, LocalDate bitisTarih) {
        this.baslık = baslık;
        this.detay = detay;
        this.bitisTarih = bitisTarih;
    }

    public String getBaslık() {
        return baslık;
    }

    public void setBaslık(String baslık) {
        this.baslık = baslık;
    }

    public String getDetay() {
        return detay;
    }

    public void setDetay(String detay) {
        this.detay = detay;
    }

    public LocalDate getBitisTarih() {
        return bitisTarih;
    }

    public void setBitisTarih(LocalDate bitisTarih) {
        this.bitisTarih = bitisTarih;
    }

    @Override
    public String toString() {
        return baslık;
    }
}
