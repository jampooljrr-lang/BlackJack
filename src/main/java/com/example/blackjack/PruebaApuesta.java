package com.example.blackjack;

public class PruebaApuesta {
    public static void main(String[] args) {
        BlackJackGame juego = new BlackJackGame();

        System.out.println(juego.realizarApuesta(150));
        System.out.println(juego.getSaldo());
        System.out.println(juego.realizarApuesta(888));
        System.out.println(juego.getSaldo());
    }
}
