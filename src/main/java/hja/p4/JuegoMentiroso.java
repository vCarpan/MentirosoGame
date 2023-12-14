package hja.p4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JuegoMentiroso {
    static int N_JUGADORES = 3;
    private List<Carta> baraja;
    private List<Carta> mesa;
    private List<Player> players;

    public JuegoMentiroso() {
        this.baraja = crearBaraja();
        this.mesa = new ArrayList<>();
        this.players = new ArrayList<>();
        for(int i=0; i<N_JUGADORES; i++){
            this.players.add(new Player("J"+i));
        }
    }

    public Player jugar() {
        repartirCartasAleatoriamente();
        boolean fin = false;
        boolean levantar;
        boolean mentira;
        int ganador = 0;
        while(!fin){
            for(int i=0; i<N_JUGADORES; i++){
                Pair<String, List<Carta>> pair = players.get(i).declararYJugar();
                mesa.addAll(pair.getElement1());   
                int next = (i+1)%N_JUGADORES;
                levantar = players.get(next).contestar();
                if(levantar){
                    mentira = false;
                    for (Carta c : pair.getElement1()) {
                        if(c.getValor().equals(pair.getElement0())){
                            mentira = true;
                            break;
                        }
                    }
                    if(mentira){
                        players.get(i).addCards(mesa);
                    }else{
                        players.get(next).addCards(mesa);
                    }
                    mesa.clear();
                }     
                if(players.get(i).isWin()) {
                    fin = true;
                    ganador = i;
                    break;
                }
            }
        }
        return players.get(ganador);
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
        int i=0;
        while(i<baraja.size()){
            for(int j=0; j<N_JUGADORES && i<baraja.size(); j++,i++){
                players.get(j).addCard(baraja.get(i));
            }
        }
    }
}