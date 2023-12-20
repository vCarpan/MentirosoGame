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
        for(int i=0; i<N_JUGADORES -1; i++){
            this.players.add(new Player("J"+i));
        }
        this.players.add(new Bot("B1","mono"));
    }

    public Player jugar() {
        repartirCartasAleatoriamente();
        boolean fin = false;
        boolean levantar;
        boolean mentira;
        String declarado = "";
        boolean finTurno = true;
        int ganador = 0;
        while(!fin){
            for(int i=0; i<N_JUGADORES; i++){
                if(finTurno){
                    finTurno = false;
                    System.out.println("---DECLARACION "+players.get(i).getId()+"---");
                    declarado = players.get(i).declarar();
                }else{
                    System.out.println("---TURNO "+players.get(i).getId()+"---");
                }
                List<Carta> cartas= players.get(i).jugar(declarado);
                System.out.println("---Voy con "+cartas.size()+" "+declarado);
                mesa.addAll(cartas);   
                int next = (i+1)%N_JUGADORES;
                levantar = players.get(next).contestar(declarado,cartas.size());
                if(levantar){
                    mentira = false;
                    for (Carta c : cartas) {
                        if(!c.getValor().equals(declarado)){
                            mentira = true;
                            break;
                        }
                    }
                    if(mentira){
                        System.out.println("---El jugador "+players.get(i).getId()+" se lleva las cartas");
                        players.get(i).addCards(mesa);
                    }else{
                        if(players.get(i).isWin()) {
                            fin = true;
                            ganador = i;
                            return players.get(ganador);
                        }
                        i= (i+1)%N_JUGADORES;
                        System.out.println("---El jugador "+players.get(next).getId()+" se lleva las cartas");
                        players.get(next).addCards(mesa);
                    }
                    mesa.clear();
                    finTurno = true;
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