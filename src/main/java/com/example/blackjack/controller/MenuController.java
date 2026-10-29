
package com.example.blackjack.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MenuController {

    @FXML
    private void onJugar() {
        System.out.println("Botón JUGAR presionado");
    }

    @FXML
    private void onVerReglas() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Reglas del Blackjack");
        alerta.setHeaderText("Objetivo: llegar a 21 sin pasarse");

        alerta.setContentText(
                "• Las cartas del 2 al 10 valen su número.\n" +
                        "• J, Q y K valen 10 puntos.\n" +
                        "• El As vale 1 u 11, según convenga.\n\n" +
                        "En tu turno puedes pedir carta o plantarte.\n" +
                        "Si superas 21, pierdes la ronda.\n" +
                        "El computador pide cartas hasta llegar a 17 o más.\n" +
                        "Gana quien consiga el mejor puntaje válido."
        );

        alerta.showAndWait();
    }

    @FXML
    private void onCerrarJuego() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

        alerta.setTitle("Cerrar juego");
        alerta.setHeaderText("¿Seguro que quieres salir?");
        alerta.setContentText("Se cerrará la aplicación.");

        Optional<ButtonType> respuesta = alerta.showAndWait();

        if (respuesta.isPresent()
                && respuesta.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
}
