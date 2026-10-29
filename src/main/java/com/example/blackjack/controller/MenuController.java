package com.example.blackjack.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button btnJugar;

    @FXML
    private void onJugar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfazGame/GameView.fxml"));
            Parent mesa = loader.load();
            Stage stage = (Stage) btnJugar.getScene().getWindow();
            stage.getScene().setRoot(mesa);
            stage.sizeToScene();
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la mesa de juego", e);
        }
    }

    @FXML
    private void onVerReglas() {
        Dialogos.mostrarReglas();
    }

    @FXML
    private void onCerrarJuego() {
        if (Dialogos.confirmarCierre()) {
            Platform.exit();
        }
    }
}
