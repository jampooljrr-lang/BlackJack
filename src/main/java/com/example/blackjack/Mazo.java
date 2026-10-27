
package com.example.blackjack;
/*herramienta para barajar*/
import java.util.Random;

public class Mazo {
    private Carta[] cartas;
    private int siguienteCarta;

    public Mazo() {
        cartas = new Carta[52];
        siguienteCarta = 0;
        crearMazo();
    }

    // Constructor auxiliar para pruebas con cartas conocidas.
    Mazo(Carta[] cartasPrueba) {
        cartas = cartasPrueba.clone();
        siguienteCarta = 0;
    }


    public void crearMazo() {
        String[] palos = {
                "Corazones", "Diamantes", "Treboles", "Picas"
        };
        String[] valores = {
                "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
        };
        int posicion = 0;

        for (String palo : palos) {
            for (String valor : valores) {
                cartas[posicion] = new Carta(valor, palo);
                posicion++;
            }
        }
    }

    public void barajar() {
        Random random = new Random();

        for (int i = 0; i < cartas.length; i++) {
            int posicionAleatoria = random.nextInt(cartas.length);

            Carta temporal = cartas[i];
            cartas[i] = cartas[posicionAleatoria];
            cartas[posicionAleatoria] = temporal;
        }

        siguienteCarta = 0;
    }

    /* Entrega una carta */
    public Carta repartirCarta() {
        if (siguienteCarta >= cartas.length) {
            return null;
        }

        Carta cartaRepartida = cartas[siguienteCarta];
        siguienteCarta++;

        return cartaRepartida;
    }
}

