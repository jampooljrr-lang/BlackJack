package com.example.blackjack.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.util.Optional;

/** Cuadros de dialogo compartidos por el menu y la mesa de juego. */
public final class Dialogos {

    private Dialogos() {
    }

    /** Muestra las reglas. No modifica la partida en curso. */
    public static void mostrarReglas() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Reglas del Blackjack");
        alerta.setHeaderText("Objetivo: superar al computador sin pasarte de 21");
        alerta.setContentText(
                "VALORES\n" +
                "• Del 2 al 10 valen su número. J, Q y K valen 10.\n" +
                "• El As vale 1 u 11, el que más te convenga.\n\n" +
                "APUESTAS\n" +
                "• Empiezas con 1.000 fichas. Apuesta mínima: 10, en múltiplos de 10.\n" +
                "• Victoria: recuperas tu apuesta y ganas otro tanto igual.\n" +
                "• Blackjack: recuperas tu apuesta y ganas 1,5 veces su valor.\n" +
                "• Empate: se devuelve la apuesta. Derrota: la pierdes.\n\n" +
                "JUGADAS\n" +
                "• Blackjack: un As y un diez (10, J, Q o K) en las dos primeras cartas.\n" +
                "• Veinte y medio: figura + 4 en las dos primeras cartas (suman 14). " +
                "No gana automáticamente: un 21 o un Blackjack lo superan.\n" +
                "• Si superas 21 pierdes de inmediato.\n\n" +
                "COMPUTADOR\n" +
                "• Recibe sus cartas cuando te plantas, pide mientras tenga menos de 17 " +
                "y se planta con 17 o más.");
        alerta.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alerta.showAndWait();
    }

    /** Pide confirmacion para cerrar. Devuelve true solo si el usuario acepta. */
    public static boolean confirmarCierre() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cerrar juego");
        alerta.setHeaderText("¿Seguro que quieres salir?");
        alerta.setContentText("Se cerrará la aplicación y se perderá la partida actual.");
        Optional<ButtonType> respuesta = alerta.showAndWait();
        return respuesta.isPresent() && respuesta.get() == ButtonType.OK;
    }
}
