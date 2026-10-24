
package com.example.blackjack;

public class BlackJackGame {

    private Mazo mazo;
    private Mano manoJugador;
    private Mano manoComputador;
    private int saldo;
    private int apuesta;
    private boolean rondaActiva;
    private boolean turnoComputador;

    public BlackJackGame() {
        mazo = new Mazo();
        manoJugador = new Mano();
        manoComputador = new Mano();
        saldo = 1000;
        apuesta = 0;
        rondaActiva = false;
        turnoComputador = false;
    }

    public void iniciarRonda() {
        mazo = new Mazo();
        mazo.barajar();

        manoJugador = new Mano();
        manoComputador = new Mano();

        manoJugador.agregarCarta(mazo.repartirCarta());
        manoJugador.agregarCarta(mazo.repartirCarta());
        rondaActiva = true;
        turnoComputador = false;
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

    public void pedirCarta() {
        if (!rondaActiva || turnoComputador) {
            return;
        }

        manoJugador.agregarCarta(mazo.repartirCarta()); /*agrega cartas a la mano*/

        /*si se pasa del puntaje 21 pierde la ronda*/
        if (getPuntosJugador() > 21) {
            rondaResultado();
            return;
        }

        if (getPuntosJugador()==21) {
            computadoraJugando();
        }

    }

    public void plantarse() {
        if (!rondaActiva || turnoComputador) {
            return;
        }
        computadoraJugando();
    }

    private void computadoraJugando() {
        turnoComputador = true;
        manoComputador.agregarCarta(mazo.repartirCarta());
        manoComputador.agregarCarta(mazo.repartirCarta());

        while (getPuntosComputador() <  17 ) {
            manoComputador.agregarCarta(mazo.repartirCarta());
        }
    }
    private void rondaResultado() {
        if ( getPuntosJugador() > 21 ) {
            rondaActiva = false;
    }
}
