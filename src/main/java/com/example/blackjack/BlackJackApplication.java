package com.example.blackjack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BlackJackApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BlackJackApplication.class.getResource("/InterfazMenu/menuview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Juego BlackJack!");
        stage.setScene(scene);
        stage.show();
    }
}
