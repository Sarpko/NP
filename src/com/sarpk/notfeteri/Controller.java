package com.sarpk.notfeteri;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXToggleButton;
import com.sarpk.notfeteri.datamodel.notDosya;
import com.sarpk.notfeteri.datamodel.notOge;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;


public class Controller {

    private ContextMenu lvMenu;

    private FilteredList<notOge> filteredList;
    private Predicate<notOge> tumNotlar;
    private Predicate<notOge> bugününnotları;

    @FXML
    private JFXListView<notOge> lvNotlarListesi;


    @FXML
    private JFXToggleButton tgBugun;


    @FXML
    private BorderPane anaPencere;

    @FXML
    private JFXTextArea taDetay;

    @FXML
    private Label lBitis;

    @FXML
    public void cikisYap(){
        Platform.exit();

    }

    @FXML
    public void initialize(){
        lvMenu=new ContextMenu();
        MenuItem notSil=new MenuItem("Notu Sil");
        notSil.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                notOge silenecekNot=lvNotlarListesi.getSelectionModel().getSelectedItem();
                secilenNotuSil(silenecekNot);
            }
        });

        lvMenu.getItems().addAll(notSil);

        tumNotlar=new Predicate<notOge>() {
            @Override
            public boolean test(notOge notOge) {
                return true;
            }
        };

        bugününnotları=new Predicate<notOge>() {
            @Override
            public boolean test(notOge notOge) {
                return notOge.getBitisTarih().equals(LocalDate.now());
            }
        };

        filteredList=new FilteredList<notOge>(notDosya.getInstance().getNotListesi(), new Predicate<notOge>() {
            @Override
            public boolean test(notOge notOge) {
                return true;
            }
        });

        SortedList<notOge> siraliListe=new SortedList<notOge>(filteredList, new Comparator<notOge>() {
            @Override
            public int compare(notOge o1, notOge o2) {
                return o1.getBitisTarih().compareTo(o2.getBitisTarih());
            }
        });

        lvNotlarListesi.setItems(siraliListe);

        lvNotlarListesi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<notOge>() {
            @Override
            public void changed(ObservableValue<? extends notOge> observable, notOge oldValue, notOge newValue) {

                if (newValue != null){
                    notOge secilenNot=lvNotlarListesi.getSelectionModel().getSelectedItem();
                    taDetay.setText(secilenNot.getDetay());

                    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    lBitis.setText(secilenNot.getBitisTarih().format(formatter));

                }

            }
        });
        lvNotlarListesi.getSelectionModel().selectFirst();

        lvNotlarListesi.setCellFactory(new Callback<ListView<notOge>, ListCell<notOge>>() {
            @Override
            public ListCell<notOge> call(ListView<notOge> param) {
                ListCell<notOge> cell=new ListCell<notOge>(){

                    @Override
                    protected void updateItem(notOge item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty){
                            setText(null);
                        }else{
                            setText(null);
                            setText(item.getBaslık());
                            if (item.getBitisTarih().equals(LocalDate.now())){
                                setTextFill(Color.RED);
                            }else {
                                setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
                cell.setContextMenu(lvMenu);
                return cell;
            }
        });
    }

    private void secilenNotuSil(notOge silenecekNot) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Not sil");
        alert.setHeaderText("Silenecek not: " + silenecekNot.getBaslık());
        alert.setContentText("Emin miyizzz?");

        Optional<ButtonType> sonuc=alert.showAndWait();

        if (sonuc.get() == ButtonType.OK){
            notDosya.getInstance().notSil(silenecekNot);
        }
    }

    @FXML
    void notlarıFiltrele(ActionEvent event) {

        if(tgBugun.isSelected()){
            filteredList.setPredicate(bugününnotları);
            lvNotlarListesi.getSelectionModel().selectFirst();

        }else{
            filteredList.setPredicate(tumNotlar);

        }

    }

    public void yeniNotEkleDialog(ActionEvent actionEvent) throws IOException {

        Dialog<ButtonType> dialog=new Dialog<>();
        dialog.initOwner(anaPencere.getScene().getWindow());

        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("yeniNotEkle.fxml"));

        dialog.setTitle("Yeni Not");
        dialog.getDialogPane().setContent(fxmlLoader.load());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


        Optional<ButtonType> sonuc=dialog.showAndWait();

        if (sonuc.get() == ButtonType.OK){
            YeniNotEkle controller=fxmlLoader.getController();
            notOge eklenennot=controller.yeniNotEkle();
            lvNotlarListesi.getSelectionModel().select(eklenennot);
        }


    }

    public void klavyedengiris(KeyEvent keyEvent) {

        notOge secilenNot=lvNotlarListesi.getSelectionModel().getSelectedItem();
        if (secilenNot!=null){
            if (keyEvent.getCode().equals(KeyCode.DELETE)){

                secilenNotuSil(secilenNot);
            }

        }
    }
}
