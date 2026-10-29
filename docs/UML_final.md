# UML final - 21 Black Jack

Este diagrama representa la estructura final implementada del proyecto.

El archivo editable está en [`UML_final.puml`](./UML_final.puml). Puede abrirse con PlantUML, IntelliJ IDEA con un complemento de PlantUML o cualquier visor compatible.

```mermaid
classDiagram
    class Carta {
        -String valor
        -String palo
        +getValor() String
        +getPalo() String
    }
    class Mano {
        -LinkedList~Carta~ cartas
        +agregarCarta(Carta)
        +calcularPuntos() int
        +getCarta(int) Carta
    }
    class Mazo {
        -Carta[] cartas
        -int siguienteCarta
        +crearMazo()
        +barajar()
        +repartirCarta() Carta
    }
    class BlackJackGame {
        -Mazo mazo
        -Mano manoJugador
        -Mano manoComputador
        -int saldo
        -int apuesta
        -boolean rondaActiva
        +iniciarRonda()
        +realizarApuesta(int) boolean
        +pedirCarta()
        +plantarse()
        +validarApuesta(String) String
    }
    class GameController
    class MenuController
    class Dialogos
    class GameView
    class MenuView

    Mazo "1" *-- "52" Carta
    Mano "1" *-- "0..*" Carta
    BlackJackGame *-- Mazo
    BlackJackGame *-- Mano
    GameController --> BlackJackGame
    GameController ..> GameView
    MenuController ..> MenuView
    MenuController ..> GameView
    MenuController ..> Dialogos
    GameController ..> Dialogos
```

## Lectura del diseño

- `model` contiene las reglas y datos del juego.
- `controller` atiende eventos y comunica la vista con el modelo.
- `InterfazMenu` e `InterfazGame` contienen las vistas FXML y sus estilos CSS.
- `GameController` utiliza una instancia de `BlackJackGame`.
- `BlackJackGame` compone un mazo y dos manos.
- `PruebaBlackJack` verifica manualmente los casos principales del modelo.
