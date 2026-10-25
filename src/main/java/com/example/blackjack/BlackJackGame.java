
package com.example.blackjack;

public class BlackJackGame {

    private Mazo mazo;
    private Mano manoJugador;
    private Mano manoComputador;
    private int saldo;
    private int apuesta;
    private boolean rondaActiva;
    private boolean turnoComputador;
    private String resultado;

    public BlackJackGame() {
        mazo = new Mazo();
        manoJugador = new Mano();
        manoComputador = new Mano();
        saldo = 1000;
        apuesta = 0;
        rondaActiva = false;
        turnoComputador = false;

        if (tieneBlackJackInicial(manoJugador)) {
            computadoraJugando();
        }
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
    public String getResultado() {
        return resultado;
    }

    private boolean tieneBlackJackInicial(Mano mano) {
        if (mano.getCantidadCartas() != 2) {
            return false;
        }

        String valor1 = mano.getCarta(0).getValor();
        String valor2 = mano.getCarta(1).getValor();

        boolean tieneAs = valor1.equals("A") || valor2.equals("A");

        boolean tieneDiez = valor1.equals("10") ||
                valor1.equals("J") ||
                valor1.equals("Q") ||
                valor1.equals("K") ||
                valor2.equals("10") ||
                valor2.equals("J") ||
                valor2.equals("Q") ||
                valor2.equals("K");

        return tieneAs && tieneDiez;
    }

    public boolean realizarApuesta(int cantidad) {
        if (cantidad < 10 || cantidad % 10 != 0 || cantidad > saldo) {
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

        if (getPuntosJugador() == 21) {
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

        // Si el computador tiene Blackjack inicial,
        // se termina la ronda para comparar los Blackjacks.
        if (tieneBlackJackInicial(manoComputador)) {
            rondaResultado();
            return;
        }

        // Si el jugador tiene Blackjack inicial,
        // gana porque el computador no obtuvo Blackjack.
        if (tieneBlackJackInicial(manoJugador)) {
            rondaResultado();
            return;
        }

        while (getPuntosComputador() < 17) {
            manoComputador.agregarCarta(mazo.repartirCarta());
        }

        rondaResultado();
    }



    private void rondaResultado() {
        boolean blackjackJugador = tieneBlackJackInicial(manoJugador);
        boolean blackjackComputador = tieneBlackJackInicial(manoComputador);

        if (blackjackJugador && blackjackComputador) {
            resultado = "Empate: ambos tienen Blackjack";
            saldo += apuesta;

        } else if (blackjackJugador) {
            resultado = "¡Blackjack! Ganaste";
            saldo += apuesta * 5 / 2;

        } else if (getPuntosJugador() > 21) {
            resultado = "Perdiste: superaste los 21 puntos";

        } else if (getPuntosComputador() > 21) {
            resultado = "Ganaste: el computador superó los 21 puntos";
            saldo += apuesta * 2;

        } else if (getPuntosJugador() > getPuntosComputador()) {
            resultado = "Ganaste";
            saldo += apuesta * 2;

        } else if (getPuntosComputador() > getPuntosJugador()) {
            resultado = "Ganó el computador";

        } else {
            resultado = "Empate";
            saldo += apuesta;
        }

        rondaActiva = false;
    }


}
