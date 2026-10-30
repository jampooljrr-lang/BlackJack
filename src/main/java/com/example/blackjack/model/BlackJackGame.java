
package com.example.blackjack.model;

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
    }

    // Constructor auxiliar para probar turnos con cartas conocidas.
    BlackJackGame(Mazo mazoPrueba, Mano jugador, Mano computador, int apuestaPrueba) {
        mazo = mazoPrueba;
        manoJugador = jugador;
        manoComputador = computador;
        saldo = 1000 - apuestaPrueba;
        apuesta = apuestaPrueba;
        rondaActiva = true;
        turnoComputador = false;
        resultado = null;
    }


    // Constructor auxiliar para probar resultados específicos.
    BlackJackGame(Mano jugador, Mano computador, int apuestaPrueba) {
        mazo = new Mazo();
        manoJugador = jugador;
        manoComputador = computador;
        saldo = 1000 - apuestaPrueba;
        apuesta = apuestaPrueba;
        rondaActiva = true;
        turnoComputador = true;
        resultado = null;
    }


    public void iniciarRonda() {
        if (rondaActiva || apuesta == 0) {
            return;
        }

        mazo = new Mazo();
        mazo.barajar();
        manoJugador = new Mano();
        manoComputador = new Mano();

        manoJugador.agregarCarta(mazo.repartirCarta());
        manoJugador.agregarCarta(mazo.repartirCarta());

        rondaActiva = true;
        turnoComputador = false;
        resultado = null;

        if (tieneBlackJackInicial(manoJugador)) {
            computadoraJugando();
        }
    }


    // Métodos auxiliares para comprobar cuántas cartas tiene cada mano.
    int getCantidadCartasJugador() {
        return manoJugador.getCantidadCartas();
    }

    int getCantidadCartasComputador() {
        return manoComputador.getCantidadCartas();
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

    public boolean tieneVeinteYMedio(Mano mano) {
        if (mano.getCantidadCartas() != 2) {
            return false;
        }

        String valor1 = mano.getCarta(0).getValor();
        String valor2 = mano.getCarta(1).getValor();

        boolean tieneFigura = valor1.equals("J")
                || valor1.equals("Q")
                || valor1.equals("K")
                || valor2.equals("J")
                || valor2.equals("Q")
                || valor2.equals("K");

        boolean tieneCuatro = valor1.equals("4")
                || valor2.equals("4");

        return tieneFigura && tieneCuatro;
    }



    public boolean realizarApuesta(int cantidad) {
        if (rondaActiva || apuesta != 0) {
            return false;
        }

        if (cantidad < 10 || cantidad % 10 != 0 || cantidad > saldo) {
            return false;
        }

        apuesta = cantidad;
        saldo -= apuesta;
        return true;
    }


    public void plantarse() {
        if (!rondaActiva || turnoComputador) {
            return;
        }
        computadoraJugando();
    }

    public boolean isRondaActiva() {
        return rondaActiva;
    }


    private void computadoraJugando() {
        if (!rondaActiva || turnoComputador) {
            return;
        }

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


    public void pedirCarta() {
        if (!rondaActiva || turnoComputador) {
            return;
        }

        Carta carta = mazo.repartirCarta();

        if (carta == null) {
            return;
        }

        manoJugador.agregarCarta(carta);

        if (getPuntosJugador() > 21) {
            rondaResultado();
        } else if (getPuntosJugador() == 21) {
            computadoraJugando();
        }
    }



    void rondaResultado() {
        // Evita pagar dos veces por la misma ronda.
        if (!rondaActiva) {
            return;
        }

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

        } else if (blackjackComputador) {
            resultado = "Perdiste: el computador tiene Blackjack";

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
        apuesta = 0;
    }

    // ===== Metodos publicos para la vista / controlador (MVC) =====

    public int getApuesta() {
        return apuesta;
    }

    public boolean isTurnoComputador() {
        return turnoComputador;
    }

    public Mano getManoJugador() {
        return manoJugador;
    }

    public Mano getManoComputador() {
        return manoComputador;
    }

    /** El jugador puede seguir jugando si le alcanza para la apuesta minima (10). */
    public boolean haySaldoSuficiente() {
        return saldo >= 10;
    }

    public boolean esBlackJack(Mano mano) {
        return tieneBlackJackInicial(mano);
    }

    /** Valida el texto escrito por el usuario. Devuelve null si es valido o el motivo del rechazo. */
    public String validarApuesta(String texto) {
        if (rondaActiva) {
            return "No se puede cambiar la apuesta durante la ronda.";
        }
        if (texto == null || texto.trim().isEmpty()) {
            return "Escribe tu apuesta.";
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return "La apuesta debe ser un numero entero.";
        }
        if (cantidad < 10) {
            return "La apuesta minima es de 10 fichas.";
        }
        if (cantidad % 10 != 0) {
            return "La apuesta debe ser multiplo de 10.";
        }
        if (cantidad > saldo) {
            return "Saldo insuficiente: solo tienes " + saldo + " fichas.";
        }
        return null;
    }

}
