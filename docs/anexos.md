# **Anexos técnicos: Mini proyecto BlackJack**

750014C FPOE · Joseth Jampool Rodriguez Rosero \- 202537119

## **Anexo A. UML inicial y final**

Los diagramas completos están en el repositorio y GitHub los dibuja: [UML inicial](https://github.com/jampooljrr-lang/BlackJack/blob/main/docs/UML_inicial.md) y [UML final](https://github.com/jampooljrr-lang/BlackJack/blob/main/docs/UML_final.md). Aquí se resume el diseño de cada uno.

### **A.1 UML inicial (diseño de partida según el enunciado)**

| Clase | Atributos | Métodos | Relaciones |
| :---- | :---- | :---- | :---- |
| Carta | \-valor: String, \-palo: String | getValor(), getPalo() | — |
| Mazo | \-cartas: Carta\[52\], \-siguienteCarta: int | crearMazo(), barajar(), repartirCarta() | Compuesto por 52 Carta |
| Mano | \-cartas: LinkedList\<Carta\> | agregarCarta(), calcularPuntos(), getCantidadCartas(), getCarta() | Contiene 0..\* Carta |
| BlackJackGame | \-mazo, \-manoJugador, \-manoComputador, \-saldo, \-apuesta, \-rondaActiva | realizarApuesta(), iniciarRonda(), pedirCarta(), plantarse(), getPuntosJugador(), getPuntosComputador(), getSaldo() | Contiene un Mazo y dos Mano |
| GameController | — | onIniciarRonda(), onPedirCarta(), onPlantarse() | Usa BlackJackGame y actualiza GameView |

### **A.2 UML final (corresponde al código entregado)**

| Clase | Atributos | Métodos principales |
| :---- | :---- | :---- |
| Carta | valor, palo | getValor(), getPalo() |
| Mazo | Carta\[52\] cartas, siguienteCarta | crearMazo(), barajar(), repartirCarta(); constructor de paquete para pruebas |
| Mano | LinkedList\<Carta\> cartas | agregarCarta(), calcularPuntos(), getCantidadCartas(), getCarta() |
| BlackJackGame | mazo, manoJugador, manoComputador, saldo, apuesta, rondaActiva, turnoComputador, resultado | realizarApuesta(), validarApuesta(String), iniciarRonda(), pedirCarta(), plantarse(), rondaResultado() (paquete), tieneVeinteYMedio(), esBlackJack(), haySaldoSuficiente(), isRondaActiva(), isTurnoComputador(), getApuesta(), getSaldo(), getResultado(), getManoJugador(), getManoComputador(), getPuntosJugador(), getPuntosComputador() |
| GameController | campos FXML de la mesa, BlackJackGame juego | onIniciarRonda(), onPedirCarta(), onPlantarse(), onVerReglas(), onCerrarJuego(), actualizarVista(), mostrarResultado() |
| MenuController | btnJugar | onJugar(), onVerReglas(), onCerrarJuego() |
| Dialogos | — | mostrarReglas(), confirmarCierre() |
| BlackJackApplication / Launcher | — | Arranque de la aplicación y manejador de cierre de la ventana |
| Vistas | MenuView.fxml, GameView.fxml | — |

### **A.3 Cambios relevantes entre el UML inicial y el final**

| Cambio | Qué permite |
| :---- | :---- |
| BlackJackGame gana turnoComputador y resultado | Bloquear acciones fuera de turno y mostrar el resultado de la ronda en la vista |
| validarApuesta(String) en el modelo | Devolver el motivo exacto del rechazo (no es número, menor a 10, no múltiplo de 10, saldo insuficiente) sin que el controlador conozca las reglas |
| tieneVeinteYMedio() y esBlackJack() | Reconocer las jugadas especiales y mostrar su mensaje |
| isRondaActiva(), isTurnoComputador(), haySaldoSuficiente() | Habilitar o deshabilitar botones según el estado de la partida |
| Clases Dialogos, MenuController y vista MenuView | Pantalla de inicio, reglas y cierre con confirmación |
| Manejador de cierre en BlackJackApplication | Confirmar también al cerrar con la X de la ventana |
| Constructores de paquete en Mazo y BlackJackGame | Probar manos y resultados concretos con cartas conocidas |

## **Anexo B. Matriz de pruebas**

### **B.1 Pruebas del modelo (ejecutadas con PruebaApuesta, PruebaMano y PruebaBlackJack)**

| \# | Prueba | Datos y acción | Esperado | Obtenido | Estado |
| :---- | :---- | :---- | :---- | :---- | :---- |
| 1 | Apuesta no múltiplo de 10 | Apostar 15 | Rechazada | Rechazada, saldo 1000 | Cumple |
| 2 | Apuesta menor al mínimo | Apostar 5 | Rechazada | Rechazada | Cumple |
| 3 | Saldo insuficiente | Apostar 910 con saldo 900 | Rechazada | Rechazada, saldo 900 | Cumple |
| 4 | Apuesta válida | Apostar 100 | Aceptada, saldo 900 | Saldo 900 | Cumple |
| 5 | Segunda apuesta | Apostar de nuevo en la misma ronda | Rechazada | Rechazada | Cumple |
| 6 | As ajustado | As \+ 9 \+ 5 | 15 | 15 | Cumple |
| 7 | Varios ases | As \+ As \+ 9 | 21 | 21 | Cumple |
| 8 | As \+ figura | As \+ K | 21 | 21 | Cumple |
| 9 | Veinte y medio | J \+ 4 | Reconocido | Reconocido | Cumple |
| 10 | No es veinte y medio | Figura \+ 5; tres cartas | No reconocido | No reconocido | Cumple |
| 11 | Veinte y medio sin premio | J \+ 4 y gana con pago normal | Sin pago extra | Saldo 900, sin extra | Cumple |
| 12 | Victoria ordinaria | Apuesta 100 | Saldo 1100, pago una sola vez | 1100 | Cumple |
| 13 | Empate | Apuesta 100 | Saldo 1000 | 1000 | Cumple |
| 14 | Victoria con Blackjack | Apuesta 100 | Saldo 1150 | 1150 | Cumple |
| 15 | Derrota | Apuesta 100 | Saldo 900 | 900 | Cumple |
| 16 | Ambos Blackjack | Jugador y computador con Blackjack | Empate, apuesta devuelta | Saldo 1000 | Cumple |
| 17 | Pasarse de 21 | El jugador pide y supera 21 | Pierde y el computador no juega | Derrota registrada | Cumple |
| 18 | Computador pide con menos de 17 | Mano inicial baja | Pide hasta 17 o más | Terminó con 22 (se pasó) | Cumple |
| 19 | Computador se planta con 17 | 17 normal y As \+ 6 | No pide | Se plantó con 2 cartas | Cumple |
| 20 | Mazo sin repetidas | Repartir las 52 cartas | 52 distintas | 52, sin repetidas | Cumple |
| 21 | Reinicio del mazo | Barajar de nuevo | 52 cartas otra vez | 52 | Cumple |
| 22 | Acciones fuera de turno | Pedir o plantarse con la ronda terminada | Sin efecto | Manos y saldo sin cambios | Cumple |
| 23 | Blackjack inicial automático | El jugador recibe Blackjack | Turno automático del computador | Ronda resuelta, saldo 1150 | Cumple |

### **B.2 Pruebas de interfaz**

| \# | Prueba | Datos y acción | Esperado | Obtenido | Estado |
| :---- | :---- | :---- | :---- | :---- | :---- |
| 24 | Cerrar con la X | Pulsar la X de la ventana | Pide confirmación | Se mostró la confirmación | Cumple |
| 25 | Cancelar el cierre | Cancelar en el diálogo | El juego sigue igual | La partida siguió igual | Cumple |
| 26 | Ver reglas en partida | Abrir las reglas a mitad de ronda | Partida intacta | La partida siguió igual | Cumple |
| 27 | Imágenes de cartas | Jugar varias rondas | Todas las cartas con imagen | Cartas mostradas con imagen | Cumple |

## **Anexo C. Registro de errores y correcciones**

### **Error 1\. Segunda apuesta aceptada durante una ronda activa**

* **Qué ocurrió:** realizarApuesta(100) se aceptaba otra vez con la ronda iniciada y descontaba de nuevo. Con la versión anterior al commit fbc8b22: primera apuesta, saldo 900; segunda apuesta con la ronda activa, aceptada, saldo 800\.  
* **Causa:** el método solo validaba el mínimo, el múltiplo de 10 y el saldo; no comprobaba si ya había una ronda activa o una apuesta vigente.  
* **Cambio realizado:** se agregó if (rondaActiva \|\| apuesta \!= 0\) return false; al inicio de realizarApuesta, y el bloqueo también en validarApuesta.  
* **Comprobación posterior:** la segunda apuesta devuelve false y el saldo sigue en 900\. Pasan las pruebas 5 y 22\.

### **Error 2\. El Blackjack inicial no iniciaba el turno del computador**

* **Qué ocurrió:** con Blackjack del jugador la ronda esperaba que pidiera o se plantara, pero el enunciado exige que el turno del computador comience automáticamente. Con la versión anterior, en 3000 rondas simuladas hubo 134 Blackjack iniciales y ninguno activó el turno del computador.  
* **Causa:** la revisión tieneBlackJackInicial(manoJugador) estaba en el constructor, cuando la mano aún estaba vacía, y iniciarRonda() no la repetía tras repartir.  
* **Cambio realizado:** la revisión se movió al final de iniciarRonda() y llama a computadoraJugando() si hay Blackjack.  
* **Comprobación posterior:** con la versión corregida, en 3000 rondas hubo 145 Blackjack iniciales y los 145 se resolvieron automáticamente. Pasa la prueba 23\.

## **Anexo D. Índice de evidencias del proceso**

| \# | Evidencia | Qué demuestra |
| :---- | :---- | :---- |
| 1 | [Commit de corrección fbc8b22](https://github.com/jampooljrr-lang/BlackJack/commit/fbc8b222badd84d65a41716408140dff6cc2cf41) | La corrección de los errores 1 y 2 del modelo |
| 2 | [Carpeta docs/](https://github.com/jampooljrr-lang/BlackJack/tree/main/docs) | El UML inicial y el final, y por tanto la evolución del diseño |
| 3 | [PruebaBlackJack.java](https://github.com/jampooljrr-lang/BlackJack/blob/main/src/main/java/com/example/blackjack/model/PruebaBlackJack.java) | Las pruebas de reglas, saldo, computador y acciones fuera de turno |

