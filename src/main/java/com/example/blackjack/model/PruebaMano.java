
package com.example.blackjack.model;

public class PruebaMano {

    public static void main(String[] args) {

        Mano mano1 = new Mano();
        mano1.agregarCarta(new Carta("A", "Picas"));
        mano1.agregarCarta(new Carta("7", "Corazones"));
        mano1.agregarCarta(new Carta("9", "Diamantes"));

        System.out.println("Caso 1 - Esperado: 17, obtenido: "
                + mano1.calcularPuntos());

        Mano mano2 = new Mano();
        mano2.agregarCarta(new Carta("A", "Picas"));
        mano2.agregarCarta(new Carta("A", "Corazones"));
        mano2.agregarCarta(new Carta("9", "Diamantes"));

        System.out.println("Caso 2 - Esperado: 21, obtenido: "
                + mano2.calcularPuntos());
    }
}
