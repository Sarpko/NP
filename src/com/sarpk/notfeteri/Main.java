package com.sarpk.notfeteri;

import com.sarpk.notfeteri.datamodel.notDosya;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        notDosya.getInstance().notlarıGetir();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("anaPencere.fxml"));
        primaryStage.setTitle("Not Defteri");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        notDosya.getInstance().notlarıYaz();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
