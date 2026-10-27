
package com.example.blackjack;

import java.util.HashSet;
import java.util.Set;

public class PruebaBlackJack {

    public static void main(String[] args) {
        probarVictoriaNormal();
        probarEmpate();
        probarBlackjack();
        probarDerrota();
        probarVeinteYMedio();
        probarAses();
        probarApuestas();
        probarMazo();
    }

    public static void probarVictoriaNormal() {
        Mano jugador = new Mano();
        jugador.agregarCarta(new Carta("10", "Picas"));
        jugador.agregarCarta(new Carta("8", "Corazones"));

        Mano computador = new Mano();
        computador.agregarCarta(new Carta("10", "Treboles"));
        computador.agregarCarta(new Carta("7", "Diamantes"));

        probarResultado("Victoria normal", jugador, computador, 1100);
    }

    public static void probarEmpate() {
        Mano jugador = new Mano();
        jugador.agregarCarta(new Carta("10", "Picas"));
        jugador.agregarCarta(new Carta("8", "Corazones"));

        Mano computador = new Mano();
        computador.agregarCarta(new Carta("10", "Treboles"));
        computador.agregarCarta(new Carta("8", "Diamantes"));

        probarResultado("Empate", jugador, computador, 1000);
    }

    public static void probarBlackjack() {
        Mano jugador = new Mano();
        jugador.agregarCarta(new Carta("A", "Picas"));
        jugador.agregarCarta(new Carta("K", "Corazones"));

        Mano computador = new Mano();
        computador.agregarCarta(new Carta("10", "Treboles"));
        computador.agregarCarta(new Carta("7", "Diamantes"));

        probarResultado("Blackjack", jugador, computador, 1150);
    }

    public static void probarDerrota() {
        Mano jugador = new Mano();
        jugador.agregarCarta(new Carta("10", "Picas"));
        jugador.agregarCarta(new Carta("9", "Corazones"));
        jugador.agregarCarta(new Carta("5", "Treboles"));

        Mano computador = new Mano();
        computador.agregarCarta(new Carta("10", "Diamantes"));
        computador.agregarCarta(new Carta("7", "Picas"));

        probarResultado("Derrota", jugador, computador, 900);
    }

    private static void probarResultado(
            String nombre,
            Mano jugador,
            Mano computador,
            int saldoEsperado) {

        BlackJackGame juego =
                new BlackJackGame(jugador, computador, 100);

        juego.rondaResultado();
        juego.rondaResultado();

        comprobar(nombre, juego.getSaldo(), saldoEsperado);
    }

    public static void probarVeinteYMedio() {
        BlackJackGame juego = new BlackJackGame();

        Mano correcta = new Mano();
        correcta.agregarCarta(new Carta("J", "Picas"));
        correcta.agregarCarta(new Carta("4", "Corazones"));

        Mano incorrecta = new Mano();
        incorrecta.agregarCarta(new Carta("J", "Picas"));
        incorrecta.agregarCarta(new Carta("5", "Corazones"));

        Mano tresCartas = new Mano();
        tresCartas.agregarCarta(new Carta("J", "Picas"));
        tresCartas.agregarCarta(new Carta("4", "Corazones"));
        tresCartas.agregarCarta(new Carta("2", "Diamantes"));

        System.out.println("\n--- PRUEBAS DE VEINTE Y MEDIO ---");

        comprobar("Figura + 4",
                juego.tieneVeinteYMedio(correcta), true);

        comprobar("Figura + 5",
                juego.tieneVeinteYMedio(incorrecta), false);

        comprobar("Tres cartas",
                juego.tieneVeinteYMedio(tresCartas), false);
    }

    public static void probarAses() {
        Mano mano1 = new Mano();
        mano1.agregarCarta(new Carta("A", "Picas"));
        mano1.agregarCarta(new Carta("K", "Corazones"));

        Mano mano2 = new Mano();
        mano2.agregarCarta(new Carta("A", "Picas"));
        mano2.agregarCarta(new Carta("9", "Corazones"));

        Mano mano3 = new Mano();
        mano3.agregarCarta(new Carta("A", "Picas"));
        mano3.agregarCarta(new Carta("9", "Corazones"));
        mano3.agregarCarta(new Carta("5", "Treboles"));

        Mano mano4 = new Mano();
        mano4.agregarCarta(new Carta("A", "Picas"));
        mano4.agregarCarta(new Carta("A", "Corazones"));
        mano4.agregarCarta(new Carta("9", "Treboles"));

        System.out.println("\n--- PRUEBAS DE LOS ASES ---");

        comprobar("As + K", mano1.calcularPuntos(), 21);
        comprobar("As + 9", mano2.calcularPuntos(), 20);
        comprobar("As + 9 + 5", mano3.calcularPuntos(), 15);
        comprobar("As + As + 9", mano4.calcularPuntos(), 21);
    }

    public static void probarApuestas() {
        System.out.println("\n--- PRUEBAS DE LAS APUESTAS ---");

        BlackJackGame juego1 = new BlackJackGame();
        comprobar("Apuesta de 5 rechazada",
                juego1.realizarApuesta(5), false);
        comprobar("Saldo sigue en 1000",
                juego1.getSaldo(), 1000);

        BlackJackGame juego2 = new BlackJackGame();
        comprobar("Apuesta de 15 rechazada",
                juego2.realizarApuesta(15), false);

        BlackJackGame juego3 = new BlackJackGame();
        comprobar("Apuesta de 1010 rechazada",
                juego3.realizarApuesta(1010), false);

        BlackJackGame juego4 = new BlackJackGame();
        comprobar("Apuesta de 100 aceptada",
                juego4.realizarApuesta(100), true);
        comprobar("Saldo después de apostar 100",
                juego4.getSaldo(), 900);

        comprobar("Segunda apuesta rechazada",
                juego4.realizarApuesta(100), false);
    }

    public static void probarMazo() {
        System.out.println("\n--- PRUEBAS DEL MAZO ---");

        Mazo mazo = new Mazo();
        Set<String> cartasUnicas = new HashSet<>();

        int cantidadRepartida = 0;
        boolean sinRepetidas = true;

        for (int i = 0; i < 52; i++) {
            Carta carta = mazo.repartirCarta();

            if (carta == null) {
                break;
            }

            cantidadRepartida++;

            String identificador =
                    carta.getValor() + " de " + carta.getPalo();

            if (!cartasUnicas.add(identificador)) {
                sinRepetidas = false;
            }
        }

        comprobar("Se reparten 52 cartas",
                cantidadRepartida, 52);

        comprobar("Hay 52 cartas diferentes",
                cartasUnicas.size(), 52);

        comprobar("No hay cartas repetidas",
                sinRepetidas, true);

        comprobar("El mazo agotado devuelve null",
                mazo.repartirCarta() == null, true);

        Mazo mazoBarajado = new Mazo();
        mazoBarajado.barajar();

        Set<String> cartasBarajadas = new HashSet<>();
        int cantidadBarajada = 0;

        for (int i = 0; i < 52; i++) {
            Carta carta = mazoBarajado.repartirCarta();

            if (carta == null) {
                break;
            }

            cantidadBarajada++;
            cartasBarajadas.add(
                    carta.getValor() + " de " + carta.getPalo()
            );
        }

        comprobar("Mazo barajado reparte 52 cartas",
                cantidadBarajada, 52);

        comprobar("Mazo barajado conserva las 52 cartas únicas",
                cartasBarajadas.size(), 52);

        mazoBarajado.repartirCarta();
        mazoBarajado.barajar();

        comprobar("Barajar reinicia el mazo",
                mazoBarajado.repartirCarta() != null, true);
    }

    public static void comprobar(
            String nombre, int obtenido, int esperado) {

        System.out.println(
                nombre
                        + " | Esperado: " + esperado
                        + " | Obtenido: " + obtenido
                        + " | " + (obtenido == esperado
                        ? "CORRECTO" : "ERROR")
        );
    }

    public static void comprobar(
            String nombre, boolean obtenido, boolean esperado) {

        System.out.println(
                nombre
                        + " | Esperado: " + esperado
                        + " | Obtenido: " + obtenido
                        + " | " + (obtenido == esperado
                        ? "CORRECTO" : "ERROR")
        );
    }
}
