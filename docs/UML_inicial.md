# UML inicial - 21 Black Jack

Diseño de clases con el que se comenzó el proyecto, a partir del enunciado (MVC, mazo de 52 posiciones, manos como lista enlazada). Editable en [`UML_inicial.puml`](./UML_inicial.puml).

```mermaid
classDiagram
    class Carta {
        -String valor
        -String palo
        +getValor() String
        +getPalo() String
    }
    class Mazo {
        -Carta[] cartas
        -int siguienteCarta
        +crearMazo()
        +barajar()
        +repartirCarta() Carta
    }
    class Mano {
        -LinkedList~Carta~ cartas
        +agregarCarta(Carta)
        +calcularPuntos() int
        +getCantidadCartas() int
        +getCarta(int) Carta
    }
    class BlackJackGame {
        -Mazo mazo
        -Mano manoJugador
        -Mano manoComputador
        -int saldo
        -int apuesta
        -boolean rondaActiva
        +realizarApuesta(int) boolean
        +iniciarRonda()
        +pedirCarta()
        +plantarse()
        +getPuntosJugador() int
        +getPuntosComputador() int
        +getSaldo() int
    }
    class GameController {
        +onIniciarRonda()
        +onPedirCarta()
        +onPlantarse()
    }
    class GameView

    Mazo "1" *-- "52" Carta : contiene
    Mano "1" o-- "0..*" Carta : contiene
    BlackJackGame *-- Mazo
    BlackJackGame *-- Mano
    GameController --> BlackJackGame
    GameController ..> GameView
```

## Cambios entre el UML inicial y el final
(ver `UML_final.md`; verificables en el historial de Git)

- Se agregaron `turnoComputador` y `resultado` en `BlackJackGame`, y las guardas de turno (`isRondaActiva`, `isTurnoComputador`).
- Se agregaron `validarApuesta(String)`, `tieneVeinteYMedio`, `esBlackJack` y `haySaldoSuficiente` en el modelo.
- Se agregaron `MenuController`, `Dialogos` y las vistas `MenuView` / `GameView`.
