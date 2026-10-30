# 21 Black Jack — Duelo contra el computador

## Descripción

Mini proyecto 1 de la asignatura **FPOE**, que consiste en desarrollar una aplicación de escritorio del juego 21 Black Jack en Java y JavaFX, en la que el usuario juega contra el computador.
El jugador comienza con 1.000 fichas ficticias, realiza apuestas, pide cartas o se planta. El computador actúa como rival siguiendo las reglas establecidas para el juego.

## Información del proyecto

- **Fundamentos De Programacion Orientada  A Eventos**
- **Universidad Del Valle**
- **Proyecto:** BlackJack21
- **Estudiante:** Joseth Jampool Rodriguez Rosero
- **Código estudiantil:** 202537119
- **Correo:** joseth.rodriguez@correounivalle.edu.co
- **Repositorio:** https://github.com/jampooljrr-lang/BlackJack.git
- **Versión entregada:** 
- **Usuarios de GitHub:** jampooljrr-lang

## Tecnologías y requisitos

- Java JDK 17.
- JavaFX para la interfaz gráfica.
- Maven para la gestión del proyecto y sus dependencias.
- IntelliJ IDEA recomendado como entorno de desarrollo.
- Git y GitHub para el control de versiones.

## Funcionalidades del juego

La aplicación está diseñada para implementar las siguientes reglas:

- Mazo de 52 cartas sin comodines y sin repetir cartas durante una ronda.
- Cálculo automático del puntaje, incluyendo el ajuste del valor del as entre 1 y 11.
- Saldo inicial de 1.000 fichas ficticias.
- Validación de apuestas: mínimo 10 fichas, múltiplos de 10 y sin superar el saldo disponible.
- Acciones para iniciar una ronda, pedir carta y plantarse.
- Turno del computador: recibe dos cartas y pide más mientras su puntaje sea menor que 17.
- Reconocimiento de Blackjack, veintiuno, veinte y medio, empate y derrota por superar 21.
- Actualización del saldo según el resultado de la ronda.
- Interfaz gráfica con JavaFX y FXML, mensajes de estado y visualización de las cartas.

## Estructura del proyecto

El código se organiza utilizando el patrón Modelo–Vista–Controlador (MVC).

- **Modelo (`model`):** contiene las clases relacionadas con las cartas, el mazo, las manos y las reglas del juego.
- **Controlador (`controller`):** recibe las acciones del usuario, ejecuta las operaciones correspondientes y actualiza la interfaz.
- **Vista (`resources`):** contiene los archivos FXML, los estilos CSS y las imágenes utilizadas por la interfaz.

Los archivos principales incluyen:

- `Carta.java`: representa una carta y sus características.
- `Mazo.java`: administra el mazo y el reparto de cartas.
- `Mano.java`: administra las cartas de una mano y calcula sus puntos.
- `BlackJackGame.java`: controla las rondas, las apuestas, los turnos y los resultados.
- `MenuController.java` y `GameController.java`: gestionan los eventos de la interfaz, según la pantalla correspondiente.
- `pom.xml`: configura el proyecto Maven y sus dependencias.

## Instrucciones de ejecución

### Opción 1. Desde IntelliJ IDEA

1. Clonar el repositorio de GitHub o descargar el proyecto.
2. Abrir IntelliJ IDEA y seleccionar **Open**.
3. Seleccionar la carpeta raíz del proyecto, donde se encuentra `pom.xml`.
4. Configurar un JDK compatible, preferiblemente JDK 17.
5. Esperar a que Maven cargue las dependencias del proyecto.
6. Localizar la clase principal `BlackJackApplication.java`.
7. Ejecutar el método `main` o la configuración de ejecución de JavaFX definida en el proyecto.

### Opción 2. Desde la terminal

Situarse en la carpeta raíz del proyecto, donde se encuentra `pom.xml`, y ejecutar:

```bash
mvn clean javafx:run
```

Este comando requiere que Maven esté instalado y configurado en el sistema, y que el `pom.xml` incluya la configuración necesaria para ejecutar JavaFX.

## Pruebas y verificación

Antes de la entrega, se deben comprobar los siguientes casos:

- Validación de apuestas incorrectas y saldo insuficiente.
- Cálculo de puntajes con uno o varios ases.
- Reconocimiento de Blackjack, veintiuno y veinte y medio.
- Derrota por superar 21 y resolución de empates.
- Cumplimiento de las reglas del computador.
- Actualización correcta del saldo.
- Reparto de cartas sin repetición y bloqueo de acciones fuera de turno.

Los resultados de las pruebas y los errores corregidos se documentan en los anexos técnicos del informe del proyecto.

## Uso de herramientas de inteligencia artificial

El uso de herramientas de inteligencia artificial, si corresponde, se declara en el informe del proyecto, indicando cuáles se utilizaron, para qué tareas y cómo se verificaron sus resultados.

## Autor

Joseth Jampool Rodriguez Rosero  
Estudiante de Ingeniería de Sistemas  
Universidad del Valle
