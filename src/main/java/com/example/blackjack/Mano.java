package com.example.blackjack;
import java.util.LinkedList;

public class Mano {
    private LinkedList<Carta> cartas;

    public Mano(){
        cartas = new LinkedList<>();
    }

    public void agregarCarta(Carta carta) {
        cartas.add(carta);
    }

/*metodo para calcular los puntos implementado con IA*/
    public int calcularPuntos() {
        int puntos = 0;
        int ases = 0;

        for (Carta carta : cartas) {
            String valor = carta.getValor();

            if (valor.equals("A")) {
                ases++;
                puntos += 1;
            } else if (valor.equals("J") ||
                    valor.equals("Q") ||
                    valor.equals("K")) {
                puntos += 10;
            } else {
                puntos += Integer.parseInt(valor);
            }
        }

        while (ases > 0 && puntos + 10 <= 21) {
            puntos += 10;
            ases--;
        }

        return puntos;
    }
}
