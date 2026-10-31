package com.example.blackjack.model;

public class PruebaPregunta3 {
    public static void main(String[] args) {
        // Jugador con 18 (10 + 8). El mazo entrega al computador: 10, 6 y luego 4.
        BlackJackGame juego = new BlackJackGame(
                new Mazo(new Carta[] {
                        new Carta("10", "Corazones"),
                        new Carta("6", "Diamantes"),
                        new Carta("4", "Picas")
                }),
                crearMano("10", "8"),
                new Mano(),
                100);

        System.out.println("Antes de plantarse: ronda activa=" + juego.isRondaActiva()
                + ", turno computador=" + juego.isTurnoComputador()
                + ", cartas computador=" + juego.getCantidadCartasComputador()
                + ", saldo=" + juego.getSaldo());

        juego.plantarse();

        System.out.println("Despues de plantarse: ronda activa=" + juego.isRondaActiva()
                + ", turno computador=" + juego.isTurnoComputador()
                + ", cartas computador=" + juego.getCantidadCartasComputador()
                + ", puntos computador=" + juego.getPuntosComputador()
                + ", resultado=" + juego.getResultado()
                + ", saldo=" + juego.getSaldo());

        juego.plantarse(); // segundo intento

        System.out.println("Segundo intento: cartas computador=" + juego.getCantidadCartasComputador()
                + ", saldo=" + juego.getSaldo() + ", resultado=" + juego.getResultado());

        comprobar("El computador termina con 20", juego.getPuntosComputador(), 20);
        comprobar("El computador tiene 3 cartas", juego.getCantidadCartasComputador(), 3);
        comprobar("Gana el computador: saldo 900", juego.getSaldo(), 900);
        comprobar("Segundo intento no reparte cartas", juego.getCantidadCartasComputador(), 3);
        comprobar("Segundo intento no cambia el saldo", juego.getSaldo(), 900);
    }

    private static Mano crearMano(String... valores) {
        Mano mano = new Mano();
        for (String v : valores) mano.agregarCarta(new Carta(v, "Corazones"));
        return mano;
    }

    private static void comprobar(String nombre, int obtenido, int esperado) {
        System.out.println((obtenido == esperado ? "[OK] " : "[FALLA] ") + nombre
                + " (esperado " + esperado + ", obtenido " + obtenido + ")");
    }
}