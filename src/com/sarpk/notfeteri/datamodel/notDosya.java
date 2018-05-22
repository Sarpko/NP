package com.sarpk.notfeteri.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class notDosya {

    private static notDosya instance=new notDosya();
    private static String dosyaAdı="notlar.txt";
    private DateTimeFormatter formatter;

    private ObservableList<notOge> notListesi;


    private notDosya (){
        formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");

    }

    public ObservableList<notOge> getNotListesi() {
        return notListesi;
    }

    public static notDosya getInstance(){
        return instance;
    }

    public void notlarıGetir() throws IOException {

        notListesi= FXCollections.observableArrayList();
        Path dosyaYolu= Paths.get(dosyaAdı);
        BufferedReader br= Files.newBufferedReader(dosyaYolu);

        String ifade;
        try {
            while ((ifade = br.readLine())!= null) {

                String[] notParcaları = ifade.split("\t");
                String baslık = notParcaları[0];
                String detay = notParcaları[1];
                String tarih = notParcaları[2];

                LocalDate bitisTarih = LocalDate.parse(tarih, formatter);

                notOge notSatır = new notOge(baslık, detay, bitisTarih);
                notListesi.add(notSatır);
            }
        }finally {
            if(br!=null){
                br.close();
            }
        }
    }

    public void notlarıYaz() throws IOException {

        Path dosyaYolu= Paths.get(dosyaAdı);
        BufferedWriter bw=Files.newBufferedWriter(dosyaYolu);

        Iterator<notOge> iterator=notListesi.iterator();
        try {

        while (iterator.hasNext()){

            notOge okunanNot=iterator.next();
            bw.write(String.format("%s\t%s\t%s", okunanNot.getBaslık(),
                                                 okunanNot.getDetay(),
                                                 okunanNot.getBitisTarih().format(formatter)));

            bw.newLine();

        }
        }finally {
            if (bw!=null){
                bw.close();
            }
        }
    }

    public void yeniNotekle(notOge yeniNot){
        notListesi.add(yeniNot);
    }

    public void notSil(notOge silenecekNot){
        notListesi.remove(silenecekNot);
    }
}
