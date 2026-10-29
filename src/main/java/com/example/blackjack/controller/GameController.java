package com.example.blackjack.controller;

import com.example.blackjack.model.BlackJackGame;
import com.example.blackjack.model.Carta;
import com.example.blackjack.model.Mano;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Controlador de la mesa de juego (GameView.fxml).
 * No calcula puntajes ni pagos: solo pide operaciones al modelo y actualiza la vista.
 */
public class GameController {

    private static final double CARTA_ANCHO = 114;
    private static final double CARTA_ALTO = 155;
    private static final double SEPARACION = 13;
    private static final double ANCHO_DISPONIBLE = 460; // zona central entre los paneles laterales

    @FXML private Label lblPuntajeComputador;
    @FXML private Label lblPuntajeJugador;
    @FXML private Label lblSaldo;
    @FXML private Label lblApuestaActual;
    @FXML private Label lblTurno;
    @FXML private TextField txtApuesta;
    @FXML private Button btnIniciarRonda;
    @FXML private Button btnPedirCarta;
    @FXML private Button btnPlantarse;
    @FXML private Button btnVerReglas;
    @FXML private Button btnCerrarJuego;
    @FXML private HBox hboxCartasComputador;
    @FXML private HBox hboxCartasJugador;

    private final BlackJackGame juego = new BlackJackGame();
    private final NumberFormat formato = NumberFormat.getIntegerInstance(new Locale("es", "CO"));
    private final Map<String, Image> imagenes = new HashMap<>();

    private int apuestaVigente = 0;
    private int saldoAntesDeApostar = 0;

    @FXML
    private void initialize() {
        txtApuesta.setText("10");
        lblTurno.setWrapText(true);
        lblTurno.setTextOverrun(OverrunStyle.CLIP);
        lblTurno.setEllipsisString("");
        lblTurno.setText("¡HAZ TU APUESTA!");
        mostrarMensaje("Ingresa una apuesta múltiplo de 10.\nPulsa INICIAR RONDA para comenzar.", false);
        actualizarVista();
    }

    // ---------------------------------------------------------------- acciones

    @FXML
    private void onIniciarRonda() {
        if (juego.isRondaActiva()) {
            return; // no se puede iniciar otra ronda antes de terminar la actual
        }
        String error = juego.validarApuesta(txtApuesta.getText());
        if (error != null) {
            mostrarMensaje(error, true);
            return;
        }

        int cantidad = Integer.parseInt(txtApuesta.getText().trim());
        saldoAntesDeApostar = juego.getSaldo();
        juego.realizarApuesta(cantidad);
        apuestaVigente = cantidad;
        juego.iniciarRonda();
        quitarEstiloResultado();
        actualizarVista();

        if (!juego.isRondaActiva()) {
            mostrarResultado(); // Blackjack inicial: la ronda se resolvio sola
            return;
        }

        lblTurno.setText("¡TU TURNO!");
        if (juego.tieneVeinteYMedio(juego.getManoJugador())) {
            mostrarMensaje("¡VEINTE Y MEDIO! Figura + 4 = 14.\nPuedes pedir carta o plantarte.", false);
        } else {
            mostrarMensaje("Tienes " + juego.getPuntosJugador() + " puntos.\nPide carta o plántate.", false);
        }
    }

    @FXML
    private void onPedirCarta() {
        if (!juego.isRondaActiva() || juego.isTurnoComputador()) {
            return;
        }
        juego.pedirCarta();
        actualizarVista();

        if (!juego.isRondaActiva()) {
            mostrarResultado(); // se paso de 21, o llego a 21 y jugo el computador
        } else {
            mostrarMensaje("Tienes " + juego.getPuntosJugador() + " puntos.\n¿Pides otra o te plantas?", false);
        }
    }

    @FXML
    private void onPlantarse() {
        if (!juego.isRondaActiva() || juego.isTurnoComputador()) {
            return;
        }
        juego.plantarse();
        actualizarVista();
        mostrarResultado();
    }

    @FXML
    private void onVerReglas() {
        Dialogos.mostrarReglas(); // la partida no cambia
    }

    @FXML
    private void onCerrarJuego() {
        if (Dialogos.confirmarCierre()) {
            Platform.exit();
        }
    }

    // ----------------------------------------------------------------- vista

    private void actualizarVista() {
        boolean turnoJugador = juego.isRondaActiva() && !juego.isTurnoComputador();
        boolean puedeApostar = !juego.isRondaActiva() && juego.haySaldoSuficiente();

        mostrarMano(hboxCartasComputador, juego.getManoComputador(), false);
        mostrarMano(hboxCartasJugador, juego.getManoJugador(), turnoJugador);

        lblPuntajeJugador.setText("(PUNTAJE: " + (juego.getManoJugador().getCantidadCartas() == 0
                ? "--" : juego.getPuntosJugador()) + ")");
        lblPuntajeComputador.setText("(PUNTAJE: " + (juego.getManoComputador().getCantidadCartas() == 0
                ? "--" : juego.getPuntosComputador()) + ")");

        lblSaldo.setText("SALDO: " + formato.format(juego.getSaldo()) + " FICHAS");
        lblApuestaActual.setText(formato.format(juego.isRondaActiva() ? apuestaVigente : 0) + " FICHAS");

        txtApuesta.setDisable(!puedeApostar);
        btnIniciarRonda.setDisable(!puedeApostar);
        btnPedirCarta.setDisable(!turnoJugador);
        btnPlantarse.setDisable(!turnoJugador);
    }

    /** Dibuja una mano. Sin cartas muestra dos espacios vacios; en turno del jugador deja uno libre. */
    private void mostrarMano(HBox contenedor, Mano mano, boolean espacioSiguiente) {
        contenedor.getChildren().clear();
        int n = mano.getCantidadCartas();
        for (int i = 0; i < n; i++) {
            contenedor.getChildren().add(crearCarta(mano.getCarta(i)));
        }
        int vacios = (n == 0) ? 2 : (espacioSiguiente ? 1 : 0);
        for (int i = 0; i < vacios; i++) {
            contenedor.getChildren().add(crearEspacioVacio());
        }

        // Si hay muchas cartas se solapan para no tapar los paneles laterales.
        int total = contenedor.getChildren().size();
        double ancho = total * CARTA_ANCHO + (total - 1) * SEPARACION;
        if (total > 1 && ancho > ANCHO_DISPONIBLE) {
            contenedor.setSpacing((ANCHO_DISPONIBLE - total * CARTA_ANCHO) / (total - 1));
        } else {
            contenedor.setSpacing(SEPARACION);
        }
    }

    private Node crearEspacioVacio() {
        StackPane slot = new StackPane();
        slot.getStyleClass().add("juego-slot-vacio");
        slot.setMinSize(CARTA_ANCHO, CARTA_ALTO);
        slot.setPrefSize(CARTA_ANCHO, CARTA_ALTO);
        slot.setMaxSize(CARTA_ANCHO, CARTA_ALTO);
        return slot;
    }

    /** Imagen /InterfazGame/img/{valor}_{palo}.png (ej. K_treboles.png). Si falta, dibuja una carta de texto. */
    private Node crearCarta(Carta carta) {
        String archivo = carta.getValor() + "_" + carta.getPalo().toLowerCase() + ".png";
        Image imagen = imagenes.computeIfAbsent(archivo, this::cargarImagen);
        if (imagen != null) {
            ImageView vista = new ImageView(imagen);
            vista.setFitWidth(CARTA_ANCHO);
            vista.setFitHeight(CARTA_ALTO);
            vista.setPreserveRatio(true);
            vista.getStyleClass().add("juego-carta");
            return vista;
        }
        return crearCartaDeTexto(carta);
    }

    private Image cargarImagen(String archivo) {
        try (InputStream in = getClass().getResourceAsStream("/InterfazGame/img/" + archivo)) {
            return in == null ? null : new Image(in);
        } catch (Exception e) {
            return null;
        }
    }

    private Node crearCartaDeTexto(Carta carta) {
        String simbolo = switch (carta.getPalo()) {
            case "Corazones" -> "♥";
            case "Diamantes" -> "♦";
            case "Treboles" -> "♣";
            default -> "♠";
        };
        String color = (simbolo.equals("♥") || simbolo.equals("♦")) ? "#c0262d" : "#111111";
        Label valor = new Label(carta.getValor());
        valor.getStyleClass().add("juego-carta-valor");
        valor.setStyle("-fx-text-fill: " + color + ";");
        Label palo = new Label(simbolo);
        palo.getStyleClass().add("juego-carta-palo");
        palo.setStyle("-fx-text-fill: " + color + ";");
        VBox contenido = new VBox(-4, valor, palo);
        contenido.setAlignment(Pos.CENTER);
        StackPane tarjeta = new StackPane(contenido);
        tarjeta.getStyleClass().add("juego-carta-texto");
        tarjeta.setMinSize(CARTA_ANCHO, CARTA_ALTO);
        tarjeta.setPrefSize(CARTA_ANCHO, CARTA_ALTO);
        tarjeta.setMaxSize(CARTA_ANCHO, CARTA_ALTO);
        return tarjeta;
    }

    // ------------------------------------------------------------- mensajes

    private void mostrarMensaje(String texto, boolean error) {
        lblTurno.setText(texto);
        lblTurno.getStyleClass().remove("juego-mensaje-error");
        if (error) {
            lblTurno.getStyleClass().add("juego-mensaje-error");
        }
    }

    private void quitarEstiloResultado() {
        lblTurno.getStyleClass().removeAll("juego-turno-gana", "juego-turno-pierde", "juego-turno-empate");
    }

    /** Titulo y detalle de fin de ronda. El saldo ya fue actualizado una sola vez por el modelo. */
    private void mostrarResultado() {
        int pago = juego.getSaldo() - saldoAntesDeApostar; // >0 gana, 0 empata, <0 pierde
        boolean blackjack = juego.esBlackJack(juego.getManoJugador());

        quitarEstiloResultado();
        String detalleFichas;
        String encabezado;
        if (pago > 0) {
            encabezado = blackjack ? "¡BLACKJACK!" : "¡GANASTE!";
            lblTurno.getStyleClass().add("juego-turno-gana");
            detalleFichas = "Ganas " + formato.format(pago) + " fichas.";
        } else if (pago == 0) {
            encabezado = "¡EMPATE!";
            lblTurno.getStyleClass().add("juego-turno-empate");
            detalleFichas = "No ganaste, pero no pierdes la apuesta.";
        } else {
            encabezado = "PERDISTE";
            lblTurno.getStyleClass().add("juego-turno-pierde");
            detalleFichas = "Pierdes " + formato.format(-pago) + " fichas.";
        }

        String extra = "";
        int puntos = juego.getPuntosJugador();
        if (puntos == 21 && !blackjack) {
            extra = " ¡Veintiuno!";
        }

        String mensaje = encabezado + "\n" + juego.getResultado() + "." + extra + "\n" + detalleFichas;
        if (!juego.haySaldoSuficiente()) {
            mensaje += "\nNo tienes saldo para otra ronda.";
        } else {
            mensaje += "\nAjusta tu apuesta para continuar.";
        }
        lblTurno.setText(mensaje);
        actualizarVista();
    }
}
