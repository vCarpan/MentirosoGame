package hja.p4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class JuegoMentiroso {

    private List<Carta> baraja;
    private List<Carta> jugador1;
    private List<Carta> jugador2;
    private List<Carta> jugador3;
    private List<Carta> mesa;
    private Scanner scanner;

    public JuegoMentiroso() {
        this.baraja = crearBaraja();
        this.jugador1 = new ArrayList<>();
        this.jugador2 = new ArrayList<>();
        this.jugador3 = new ArrayList<>();
        this.mesa = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void iniciarJuego() {
        repartirCartasAleatoriamente();

        while (!jugador1.isEmpty() && !jugador2.isEmpty() && !jugador3.isEmpty()) {
            declararYJugar(jugador1);
            declararYJugar(jugador2);
            declararYJugar(jugador3);
        }

        scanner.close();
    }

    private List<Carta> crearBaraja() {
        List<Carta> baraja = new ArrayList<>();
        String[] palos = {"h", "d", "c", "s"};
        String[] valores = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};

        for (String palo : palos) {
            for (String valor : valores) {
                baraja.add(new Carta(valor, palo));
            }
        }

        return baraja;
    }

    private void repartirCartasAleatoriamente() {
        Collections.shuffle(baraja);
        for (int i = 0; i < baraja.size(); i++) {
            if (i % 3 == 0) {
                jugador1.add(baraja.get(i));
            } else if (i % 3 == 1) {
                jugador2.add(baraja.get(i));
            } else {
                jugador3.add(baraja.get(i));
            }
        }
    }

    private void declararYJugar(List<Carta> jugador) {
        System.out.println("Mano actual del jugador: " + jugador);
        System.out.print("Declarar Cartas: ");
        String declaracion = scanner.nextLine();
        procesarDeclaracion(declaracion, jugador);

        // Jugar cartas después de la declaración
        System.out.print("Ingresa las cartas para jugar: ");
        String cartasJugadasInput = scanner.nextLine();
        String[] cartasJugadas = cartasJugadasInput.split(",");

        for (String cartaJugada : cartasJugadas) {
            Carta cartaSeleccionada = null;
            for (Carta carta : jugador) {
                if (carta.toString().equalsIgnoreCase(cartaJugada)) {
                    cartaSeleccionada = carta;
                    break;
                }
            }

            if (cartaSeleccionada != null) {
                System.out.println("¡Carta válida!");

                // Retira la carta jugada de la mano del jugador
                jugador.remove(cartaSeleccionada);

                // Agrega la carta jugada a la mesa
                mesa.add(cartaSeleccionada);
            } else {
                System.out.println("¡Carta no válida! Intenta de nuevo.");
                declararYJugar(jugador); // Intentar de nuevo en caso de carta no válida
                return; // Salir del método para evitar procesar el turno siguiente
            }
        }
    }

    private void procesarDeclaracion(String declaracion, List<Carta> jugador) {
        String[] partes = declaracion.split(" ");
        int cantidadDeclarada = Integer.parseInt(partes[0]);

        if (cantidadDeclarada > 0 && cantidadDeclarada <= jugador.size()) {
            for (int i = 0; i < cantidadDeclarada; i++) {
                String valorDeclarado = partes[1];
                for (Carta carta : jugador) {
                    if (carta.getValor().equalsIgnoreCase(valorDeclarado)) {
                        mesa.add(carta);
                        jugador.remove(carta);
                        break;
                    }
                }
            }
        } else {
            System.out.println("Declaración no válida. Intenta de nuevo.");
            declararYJugar(jugador); // Intentar de nuevo en caso de declaración no válida
        }
    }
}