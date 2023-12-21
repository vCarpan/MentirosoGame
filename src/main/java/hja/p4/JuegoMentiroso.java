package hja.p4;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JuegoMentiroso {
    static int N_JUGADORES = 3;
    private List<Carta> baraja;
    private List<Carta> mesa;
    private List<Player> players;
    private static final String STATS_FILE_PATH = "estadisticas.json";
    static Map<String, Estadisticas> estadisticasMap = new HashMap<>();

    public JuegoMentiroso() {
        this.baraja = crearBaraja();
        this.mesa = new ArrayList<>();
        this.players = new ArrayList<>();
        for(int i=0; i<N_JUGADORES -2; i++){
            this.players.add(new Player("J"+i));
        }
        this.players.add(new Bot("B0","mono"));
        Bot bot = new Bot("B1","inteligente");
        bot.setIndexLeftPlayer(players.get(players.size()-1).getId());
        bot.setIndexRightPlayer(players.get(0).getId());
        this.players.add(bot);
        
        cargarEstadisticasDesdeArchivo();
        for (Player player : players) {
            String playerId = player.getId();
            if (!estadisticasMap.containsKey(playerId)) {
                Estadisticas stats = new Estadisticas();
                estadisticasMap.put(playerId, stats);
            }
        }
        
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
                estadisticasMap.get(players.get(i).getId()).incrementarRondas();     
                List<Carta> cartas= players.get(i).jugar(declarado);
                System.out.println("---Voy con "+cartas.size()+" "+declarado);
                mesa.addAll(cartas);   
                int next = (i+1)%N_JUGADORES;
                levantar = players.get(next).contestar(declarado,cartas.size());
                if(levantar){
                    mentira = false;
                    estadisticasMap.get(players.get(next).getId()).incrementarLevantar();
                    for (Carta c : cartas) {
                        if(!c.getValor().equals(declarado)){
                            mentira = true;
                            break;
                        }
                    }
                    if(mentira){
                        estadisticasMap.get(players.get(i).getId()).incrementarMentiras();                   
                        System.out.println("---El jugador "+players.get(i).getId()+" se lleva las cartas");
                        players.get(i).addCards(mesa);
                    }else{
                        estadisticasMap.get(players.get(i).getId()).incrementarVerdades();  
                        if(players.get(i).isWin()) {
                            fin = true;
                            ganador = i;
                            guardarEstadisticasEnArchivo();
                            return players.get(ganador);
                        }
                        i= (i+1)%N_JUGADORES;
                        System.out.println("---El jugador "+players.get(next).getId()+" se lleva las cartas");
                        players.get(next).addCards(mesa);
                    }
                    mesa.clear();
                    finTurno = true;
                }else{
                    estadisticasMap.get(players.get(next).getId()).incrementarNoLevantar();
                }
                if(players.get(i).isWin()) {
                    fin = true;
                    ganador = i;
                    guardarEstadisticasEnArchivo();
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
    private void cargarEstadisticasDesdeArchivo() {
        try (FileReader fileReader = new FileReader(STATS_FILE_PATH)) {
            StringBuilder content = new StringBuilder();
            int character;

            while ((character = fileReader.read()) != -1) {
                content.append((char) character);
            }

            if (content.length() > 0) {
                JSONArray jsonArray = new JSONArray(content.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    String playerId = jsonData.getString("id");
                    Estadisticas stats = new Estadisticas();
                    stats.setNRondas(jsonData.getInt("nRondas"));
                    stats.setMentiras(jsonData.getInt("mentiras"));
                    stats.setVerdades(jsonData.getInt("verdades"));
                    stats.setLevantar(jsonData.getInt("levantar"));
                    stats.setNoLevantar(jsonData.getInt("noLevantar"));
                    estadisticasMap.put(playerId, stats);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarEstadisticasEnArchivo() {
        try (FileWriter fileWriter = new FileWriter(STATS_FILE_PATH)) {
            JSONArray jsonArray = new JSONArray();

            for (Player player : players) {
                String playerId = player.getId();
                Estadisticas stats = estadisticasMap.get(playerId);
                JSONObject jsonData = new JSONObject();
                jsonData.put("id", playerId);
                jsonData.put("nRondas", stats.getNRondas());
                jsonData.put("mentiras", stats.getMentiras());
                jsonData.put("verdades", stats.getVerdades());
                jsonData.put("levantar", stats.getLevantar());
                jsonData.put("noLevantar", stats.getNoLevantar());
                jsonArray.put(jsonData);
            }

            fileWriter.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
}