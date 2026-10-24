
package com.example.blackjack;

public class BlackJackGame {

    private Mazo mazo;
    private Mano manoJugador;
    private Mano manoComputador;
    private int saldo;
    private int apuesta;

    public BlackJackGame() {
        mazo = new Mazo();
        manoJugador = new Mano();
        manoComputador = new Mano();
        saldo = 1000;
        apuesta = 0;
    }

    public void iniciarRonda() {
        mazo = new Mazo();
        mazo.barajar();

        manoJugador = new Mano();
        manoComputador = new Mano();

        manoJugador.agregarCarta(mazo.repartirCarta());
        manoJugador.agregarCarta(mazo.repartirCarta());
    }

    public int getPuntosJugador() {
        return manoJugador.calcularPuntos();
    }

    public int getPuntosComputador() {
        return manoComputador.calcularPuntos();
    }

    public int getSaldo() {
        return saldo;
    }

    public boolean realizarApuesta(int cantidad) {
        if (cantidad <10 || cantidad%10 != 0 || cantidad > saldo) {
            return false;
        }
        apuesta = cantidad;
        saldo -= apuesta;
        return true;

    }
}
