package com.sarpk.notfeteri;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sarpk.notfeteri.datamodel.notDosya;
import com.sarpk.notfeteri.datamodel.notOge;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.time.LocalDate;

public class YeniNotEkle {

    @FXML
    private JFXTextField notBaslik;

    @FXML
    private TextArea notDetay;

    @FXML
    private JFXDatePicker notBitisTarihi;



    public notOge yeniNotEkle() {
        String baslık=notBaslik.getText().trim();
        String detay=notDetay.getText().trim();
        LocalDate bitis=notBitisTarihi.getValue();

        notOge eklenecekNot=new notOge(baslık,detay,bitis);
        notDosya.getInstance().yeniNotekle(eklenecekNot);

        return eklenecekNot;


    }
}
