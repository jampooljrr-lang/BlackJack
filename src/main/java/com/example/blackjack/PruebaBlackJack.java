
package com.example.blackjack;

public class PruebaBlackJack {

    public static void main(String[] args) {

        System.out.println("=== PRUEBAS DEL BLACKJACK ===");

        probarResultados();
        probarVeinteYMedio();
        probarAses();
        probarApuestas();
        probarMazo();


        probarFlujoDelJugador();
        probarTurnoDelComputador();
        probarBlackjackDoble();
        probarVeinteYMedioSinPremio();
        probarAccionesFueraDeTurno();
        probarApuestaConSaldoReducido();

        probarBlackjackInicialAutomatico();
        probarInicioDeRonda();

        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }

    // Comprueba resultados y actualización del saldo.
    private static void probarResultados() {
        System.out.println("\n--- RESULTADOS ---");

        comprobarResultado(
                "Victoria normal",
                crearMano("10", "9"),
                crearMano("10", "7"),
                "Ganaste",
                1100
        );

        comprobarResultado(
                "Empate",
                crearMano("10", "8"),
                crearMano("10", "8"),
                "Empate",
                1000
        );

        comprobarResultado(
                "Blackjack",
                crearMano("A", "K"),
                crearMano("10", "Q"),
                "¡Blackjack! Ganaste",
                1150
        );

        comprobarResultado(
                "Derrota",
                crearMano("10", "6"),
                crearMano("10", "8"),
                "Ganó el computador",
                900
        );
    }

    private static void comprobarResultado(
            String nombre,
            Mano jugador,
            Mano computador,
            String resultadoEsperado,
            int saldoEsperado
    ) {
        BlackJackGame juego =
                new BlackJackGame(jugador, computador, 100);

        juego.rondaResultado();

        comprobar(
                nombre + ": resultado",
                juego.getResultado().equals(resultadoEsperado),
                true
        );

        comprobar(
                nombre + ": saldo",
                juego.getSaldo(),
                saldoEsperado
        );

        // Resolver una segunda vez no debe volver a pagar.
        juego.rondaResultado();

        comprobar(
                nombre + ": no paga dos veces",
                juego.getSaldo(),
                saldoEsperado
        );
    }

    // Comprueba cuándo se reconoce Veinte y medio.
    private static void probarVeinteYMedio() {
        System.out.println("\n--- VEINTE Y MEDIO ---");

        BlackJackGame juego =
                new BlackJackGame(
                        crearMano("J", "4"),
                        new Mano(),
                        100
                );

        comprobar(
                "Figura + 4",
                juego.tieneVeinteYMedio(crearMano("J", "4")),
                true
        );

        comprobar(
                "Figura + 5 no es Veinte y medio",
                juego.tieneVeinteYMedio(crearMano("J", "5")),
                false
        );

        comprobar(
                "Tres cartas no son Veinte y medio",
                juego.tieneVeinteYMedio(crearMano("J", "4", "2")),
                false
        );
    }

    // Comprueba el cálculo de los ases.
    private static void probarAses() {
        System.out.println("\n--- ASES ---");

        comprobar(
                "As + K = 21",
                crearMano("A", "K").calcularPuntos(),
                21
        );

        comprobar(
                "As + 9 = 20",
                crearMano("A", "9").calcularPuntos(),
                20
        );

        comprobar(
                "As + 9 + 5 = 15",
                crearMano("A", "9", "5").calcularPuntos(),
                15
        );

        comprobar(
                "As + As + 9 = 21",
                crearMano("A", "A", "9").calcularPuntos(),
                21
        );
    }

    // Comprueba la validación de las apuestas.
    private static void probarApuestas() {
        System.out.println("\n--- APUESTAS ---");

        BlackJackGame juego = new BlackJackGame();

        comprobar("Apuesta de 5 rechazada",
                juego.realizarApuesta(5), false);

        comprobar("Saldo sigue en 1000",
                juego.getSaldo(), 1000);

        comprobar("Apuesta de 15 rechazada",
                juego.realizarApuesta(15), false);

        comprobar("Apuesta superior al saldo rechazada",
                juego.realizarApuesta(1010), false);

        comprobar("Apuesta de 100 aceptada",
                juego.realizarApuesta(100), true);

        comprobar("Saldo después de apostar 100",
                juego.getSaldo(), 900);

        comprobar("No acepta una segunda apuesta",
                juego.realizarApuesta(100), false);
    }

    // Comprueba las 52 cartas, su unicidad y el final del mazo.
    private static void probarMazo() {
        System.out.println("\n--- MAZO ---");

        Mazo mazo = new Mazo();

        Carta[] cartas = new Carta[52];
        boolean todasUnicas = true;

        for (int i = 0; i < 52; i++) {
            cartas[i] = mazo.repartirCarta();

            if (cartas[i] == null) {
                todasUnicas = false;
                continue;
            }

            for (int j = 0; j < i; j++) {
                if (cartas[i].getValor().equals(cartas[j].getValor())
                        && cartas[i].getPalo().equals(cartas[j].getPalo())) {
                    todasUnicas = false;
                }
            }
        }

        comprobar("El mazo entrega 52 cartas",
                contarCartas(cartas), 52);

        comprobar("No hay cartas repetidas",
                todasUnicas, true);

        comprobar("El mazo se agota",
                mazo.repartirCarta() == null, true);

        mazo.barajar();

        Carta[] cartasMezcladas = new Carta[52];
        boolean unicasDespuesDeBarajar = true;

        for (int i = 0; i < 52; i++) {
            cartasMezcladas[i] = mazo.repartirCarta();

            if (cartasMezcladas[i] == null) {
                unicasDespuesDeBarajar = false;
                continue;
            }

            for (int j = 0; j < i; j++) {
                if (cartasMezcladas[i].getValor()
                        .equals(cartasMezcladas[j].getValor())
                        && cartasMezcladas[i].getPalo()
                        .equals(cartasMezcladas[j].getPalo())) {
                    unicasDespuesDeBarajar = false;
                }
            }
        }

        comprobar("Después de barajar hay 52 cartas",
                contarCartas(cartasMezcladas), 52);

        comprobar("Después de barajar no hay repetidas",
                unicasDespuesDeBarajar, true);

        comprobar("Después de barajar vuelve a agotarse",
                mazo.repartirCarta() == null, true);

        mazo.barajar();

        comprobar("Barajar reinicia el mazo",
                contarRepartidas(mazo), 52);
    }

    private static int contarCartas(Carta[] cartas) {
        int cantidad = 0;

        for (Carta carta : cartas) {
            if (carta != null) {
                cantidad++;
            }
        }

        return cantidad;
    }

    private static int contarRepartidas(Mazo mazo) {
        int cantidad = 0;

        while (mazo.repartirCarta() != null) {
            cantidad++;
        }

        return cantidad;
    }

    // Comprueba las acciones que realiza el jugador.
    private static void probarFlujoDelJugador() {
        System.out.println("\n--- FLUJO DEL JUGADOR ---");

        BlackJackGame juego = new BlackJackGame(
                new Mazo(new Carta[] {
                        new Carta("2", "Picas")
                }),
                crearMano("10", "6"),
                new Mano(),
                100
        );

        comprobar("No permite cambiar la apuesta durante la ronda",
                juego.realizarApuesta(200), false);

        comprobar("El saldo no cambia al intentar cambiar la apuesta",
                juego.getSaldo(), 900);

        juego.pedirCarta();

        comprobar("Pedir carta agrega una carta",
                juego.getCantidadCartasJugador(), 3);

        comprobar("El jugador queda con 18 puntos",
                juego.getPuntosJugador(), 18);

        comprobar("La ronda sigue activa",
                juego.isRondaActiva(), true);

        // El jugador tiene 19 y pide un 5: supera 21.
        BlackJackGame derrota = new BlackJackGame(
                new Mazo(new Carta[] {
                        new Carta("5", "Picas")
                }),
                crearMano("10", "9"),
                new Mano(),
                100
        );

        derrota.pedirCarta();

        comprobar("Pasarse de 21 termina la ronda",
                derrota.isRondaActiva(), false);

        comprobar("Pierde la apuesta al pasarse",
                derrota.getSaldo(), 900);

        comprobar("Se registra la derrota por superar 21",
                derrota.getResultado().equals(
                        "Perdiste: superaste los 21 puntos"
                ),
                true
        );

        // Llega a 21: el computador recibe dos cartas automáticamente.
        BlackJackGame veintiuno = new BlackJackGame(
                new Mazo(new Carta[] {
                        new Carta("3", "Picas"),
                        new Carta("10", "Corazones"),
                        new Carta("7", "Diamantes")
                }),
                crearMano("10", "8"),
                new Mano(),
                100
        );

        veintiuno.pedirCarta();

        comprobar("El jugador llega a 21",
                veintiuno.getPuntosJugador(), 21);

        comprobar("El computador recibe dos cartas",
                veintiuno.getCantidadCartasComputador(), 2);

        comprobar("El turno termina automáticamente",
                veintiuno.isRondaActiva(), false);

        comprobar("El jugador gana contra 17",
                veintiuno.getSaldo(), 1100);
    }

    // Comprueba que el computador pide hasta llegar a 17 o más.
    private static void probarTurnoDelComputador() {
        System.out.println("\n--- TURNO DEL COMPUTADOR ---");

        BlackJackGame computadorPide = new BlackJackGame(
                new Mazo(new Carta[] {
                        new Carta("10", "Corazones"),
                        new Carta("2", "Diamantes"),
                        new Carta("4", "Picas"),
                        new Carta("6", "Treboles")
                }),
                crearMano("10", "8"),
                new Mano(),
                100
        );

        computadorPide.plantarse();

        comprobar("El computador pide hasta superar 16",
                computadorPide.getCantidadCartasComputador(), 4);

        comprobar("El computador termina con 22",
                computadorPide.getPuntosComputador(), 22);

        comprobar("El jugador gana cuando el computador se pasa",
                computadorPide.getSaldo(), 1100);

        BlackJackGame computadorEn17 = new BlackJackGame(
                new Mazo(new Carta[] {
                        new Carta("10", "Corazones"),
                        new Carta("7", "Diamantes")
                }),
                crearMano("10", "8"),
                new Mano(),
                100
        );

        computadorEn17.plantarse();

        comprobar("El computador se planta con 17",
                computadorEn17.getPuntosComputador(), 17);

        comprobar("No pide otra carta con 17",
                computadorEn17.getCantidadCartasComputador(), 2);

        BlackJackGame asSiete = new BlackJackGame(
                new Mazo(new Carta[] {
                        new Carta("A", "Corazones"),
                        new Carta("6", "Diamantes")
                }),
                crearMano("10", "8"),
                new Mano(),
                100
        );

        asSiete.plantarse();

        comprobar("As + 6 equivale a 17",
                asSiete.getPuntosComputador(), 17);

        comprobar("Se planta con As + 6",
                asSiete.getCantidadCartasComputador(), 2);
    }

    // Ambos participantes reciben Blackjack inicial.
    private static void probarBlackjackDoble() {
        System.out.println("\n--- BLACKJACK DE AMBOS ---");

        BlackJackGame juego = new BlackJackGame(
                crearMano("A", "K"),
                crearMano("A", "Q"),
                100
        );

        juego.rondaResultado();

        comprobar("Ambos Blackjack producen empate",
                juego.getResultado().equals(
                        "Empate: ambos tienen Blackjack"
                ),
                true
        );

        comprobar("Se devuelve la apuesta",
                juego.getSaldo(), 1000);
    }

    // Veinte y medio no es una victoria automática.
    private static void probarVeinteYMedioSinPremio() {
        System.out.println("\n--- VEINTE Y MEDIO SIN PREMIO ---");

        BlackJackGame juego = new BlackJackGame(
                crearMano("J", "4"),
                crearMano("10", "7"),
                100
        );

        comprobar("J + 4 se reconoce como Veinte y medio",
                juego.tieneVeinteYMedio(crearMano("J", "4")),
                true);

        juego.rondaResultado();

        comprobar("Veinte y medio no gana automáticamente",
                juego.getResultado().equals("Ganó el computador"),
                true);

        comprobar("No recibe premio extra",
                juego.getSaldo(), 900);
    }

    // Comprueba que no se pueda actuar durante el turno del computador.
    private static void probarAccionesFueraDeTurno() {
        System.out.println("\n--- ACCIONES FUERA DE TURNO ---");

        BlackJackGame juego = new BlackJackGame(
                crearMano("10", "7"),
                crearMano("10", "8"),
                100
        );

        juego.pedirCarta();
        juego.plantarse();

        comprobar("No se agrega carta al jugador",
                juego.getCantidadCartasJugador(), 2);

        comprobar("No cambia la mano del computador",
                juego.getCantidadCartasComputador(), 2);

        comprobar("La ronda sigue activa",
                juego.isRondaActiva(), true);

        comprobar("No se registra un resultado",
                juego.getResultado() == null, true);

        comprobar("El saldo no cambia",
                juego.getSaldo(), 900);
    }

    // Comprueba las apuestas después de perder saldo.
    private static void probarApuestaConSaldoReducido() {
        System.out.println("\n--- APUESTA CON SALDO REDUCIDO ---");

        BlackJackGame juego = new BlackJackGame(
                crearMano("10", "6"),
                crearMano("10", "8"),
                100
        );

        juego.rondaResultado();

        comprobar("Saldo después de perder 100",
                juego.getSaldo(), 900);

        comprobar("Rechaza apostar 910 con saldo de 900",
                juego.realizarApuesta(910), false);

        comprobar("El saldo permanece en 900",
                juego.getSaldo(), 900);

        comprobar("Acepta apostar los 900 disponibles",
                juego.realizarApuesta(900), true);

        comprobar("El saldo queda en cero",
                juego.getSaldo(), 0);
    }

    // Crea una mano con los valores indicados.
    private static Mano crearMano(String... valores) {
        Mano mano = new Mano();

        for (String valor : valores) {
            mano.agregarCarta(new Carta(valor, "Picas"));
        }

        return mano;
    }

    // Comprobación de números enteros.
    private static void comprobar(
            String nombre,
            int obtenido,
            int esperado
    ) {
        if (obtenido == esperado) {
            System.out.println(
                    "CORRECTO: " + nombre + " = " + obtenido
            );
        } else {
            System.out.println(
                    "ERROR: " + nombre
                            + " | esperado: " + esperado
                            + " | obtenido: " + obtenido
            );
        }
    }

    // Comprobación de valores booleanos.
    private static void comprobar(
            String nombre,
            boolean obtenido,
            boolean esperado
    ) {
        if (obtenido == esperado) {
            System.out.println("CORRECTO: " + nombre);
        } else {
            System.out.println(
                    "ERROR: " + nombre
                            + " | esperado: " + esperado
                            + " | obtenido: " + obtenido
            );
        }
    }
    private static void probarBlackjackInicialAutomatico() {
        Mano jugador = crearMano("A", "K");
        Mano computador = crearMano();

        Mazo mazoPrueba = new Mazo(new Carta[]{
                new Carta("7", "Picas"),
                new Carta("8", "Corazones")
        });

        BlackJackGame juego = new BlackJackGame(
                mazoPrueba, jugador, computador, 100
        );

        juego.plantarse();

        comprobar("Computador recibe sus dos cartas",
                juego.getCantidadCartasComputador(), 2);

        comprobar("Blackjack inicial gana contra 15",
                juego.getResultado() != null, true);

        comprobar("Saldo correcto tras Blackjack",
                juego.getSaldo(), 1150);
    }
    private static void probarInicioDeRonda() {
        BlackJackGame juego = new BlackJackGame();

        comprobar("Acepta apuesta inicial",
                juego.realizarApuesta(100), true);

        juego.iniciarRonda();

        comprobar("Descuenta apuesta al iniciar",
                juego.getSaldo(), 900);

        comprobar("Jugador recibe dos cartas",
                juego.getCantidadCartasJugador(), 2);

        comprobar("Computador empieza sin cartas",
                juego.getCantidadCartasComputador(), 0);
    }
}
