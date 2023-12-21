/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hja.p4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author usuario_local
 */

public class Bot extends Player{
    
    private String tipo;
    final int N = 1, M=4;
    private String indexLeftPlayer;
    private String indexRightPlayer;
    

    public Bot(String id, String tipo) {
        super(id);
        this.tipo = tipo;
    }
    
    @Override
    public List<Carta> jugar(String valor){
        System.out.println("Soy " + id + " y tengo estas " + this.cartas);
        List<Carta> cartas = new ArrayList();
        int contador = 0;
        int noCoinciden = 0;
        for (List<Carta> lista : this.cartas.values()) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getValor().equals(valor)) {
                    contador++;
                }else{
                    noCoinciden++;
                }
            }
        }
        System.out.println("cartas: " +contarCartasEnMapa());
        int valorEntero = (int) N + (int) (Math.random() * ((M - N) + 1));
        while(valorEntero > contarCartasEnMapa()){
            valorEntero = (int) N + (int) (Math.random() * ((M - N) + 1));
        }
        if(noCoinciden==0){
            for (List<Carta> lista : this.cartas.values()) {
                for (int i = 0; i < lista.size(); i++) {
                    cartas.add(lista.get(i));
                }
            }   
            this.cartas.clear();
            return cartas;
        }
        switch(this.tipo){
            case "mono":  
                boolean mentir = Math.random() > 0.5;
                if(!mentir&&contador==0) mentir = true;
                if(mentir){
                    contador = 0;

                    for (Iterator<List<Carta>> iterCartas = this.cartas.values().iterator(); iterCartas.hasNext();) {
                        List<Carta> lista = iterCartas.next();

                        for (Iterator<Carta> iter = lista.iterator(); iter.hasNext();) {
                            Carta carta = iter.next();

                            if (!carta.getValor().equals(valor)) {
                                cartas.add(carta);
                                iter.remove();
                                contador++;
                            }

                            if (contador >= valorEntero) {
                                break;
                            }
                        }

                        if (lista.isEmpty()) {
                            iterCartas.remove(); // Remover la lista si está vacía
                        }

                        if (contador >= valorEntero) {
                            break;
                        }
                    }
                    if(contador < valorEntero && this.cartas.size()>0){
                        for (Iterator<List<Carta>> iterCartas = this.cartas.values().iterator(); iterCartas.hasNext();) {
                            List<Carta> lista = iterCartas.next();

                            for (Iterator<Carta> iter = lista.iterator(); iter.hasNext();) {
                                Carta carta = iter.next();

                                cartas.add(carta);
                                iter.remove();
                                contador++;

                                if (contador >= valorEntero) {
                                    break;
                                }
                            }

                            // Verifica si la lista está vacía después de la eliminación de cartas
                            if (lista.isEmpty()) {
                                iterCartas.remove();
                            }

                            if (contador >= valorEntero) {
                                break;
                            }
                        }                        
                    }
                    System.out.println("Soy " + id + " y meto " + cartas);
                    return cartas;
                }
                else{
                    contador = 0;

                    List<Carta> lista = this.cartas.get(valor);

                    if (lista != null) {
                        for (Iterator<Carta> iter = lista.iterator(); iter.hasNext();) {
                            Carta carta = iter.next();

                            if (carta.getValor().equals(valor)) {
                                cartas.add(carta);
                                contador++;
                                iter.remove(); // Elimina la carta de la lista actual

                                if (contador >= valorEntero) {
                                    break;
                                }
                            }
                        }

                        // Verifica si la lista está vacía después de la eliminación de cartas
                        if (lista.isEmpty()) {
                            this.cartas.remove(valor);
                        }
                    }
                    System.out.println("Soy " + id + " y meto " + cartas);
                    return cartas;
                }
            case "inteligente":
                Estadisticas stats = JuegoMentiroso.estadisticasMap.get(indexRightPlayer);
                int random2;

                if (stats.getNRondas() < 10) {
                    if (contador > 0) {
                        // Caso: contador > 0
                        Carta carta = this.cartas.get(valor).get(0);
                        cartas.add(carta);
                        removeCard(carta);
                    } else {
                        // Caso: contador == 0
                        random2 = (int) N + (int) (Math.random() * ((1 - 2) + 1));
                        for (Iterator<List<Carta>> iter = this.cartas.values().iterator(); iter.hasNext();) {
                            List<Carta> lista = iter.next();
                            if(lista.size()==1){
                                processListWithIterator(cartas, lista.iterator(), lista, valor, random2);
                                if (lista.isEmpty()) {
                                    iter.remove(); // Elimina la lista del mapa si está vacía
                                }
                                if (cartas.size() >= random2) break;
                            }                          
                        }                     
                        for (Iterator<List<Carta>> iter = this.cartas.values().iterator(); iter.hasNext() && cartas.size() < random2;) {
                            List<Carta> lista = iter.next();
                            if(lista.size()==2){
                                processListWithIterator(cartas, lista.iterator(), lista, valor, random2);
                                if (lista.isEmpty()) {
                                    iter.remove(); // Elimina la lista del mapa si está vacía
                                }
                                if (cartas.size() >= random2) break;
                            }                          
                        }
                        for (Iterator<List<Carta>> iter = this.cartas.values().iterator(); iter.hasNext() && cartas.size() < random2;) {
                            List<Carta> lista = iter.next();
                            if(lista.size()==3){
                                processListWithIterator(cartas, lista.iterator(), lista, valor, random2);
                                if (lista.isEmpty()) {
                                    iter.remove(); // Elimina la lista del mapa si está vacía
                                }
                                if (cartas.size() >= random2) break;
                            }                          
                        }
                        for (Iterator<List<Carta>> iter = this.cartas.values().iterator(); iter.hasNext() && cartas.size() < random2;) {
                            List<Carta> lista = iter.next();
                            if(lista.size()==4){
                                processListWithIterator(cartas, lista.iterator(), lista, valor, random2);
                                if (lista.isEmpty()) {
                                    iter.remove(); // Elimina la lista del mapa si está vacía
                                }
                                if (cartas.size() >= random2) break;
                            }                          
                        }
                    }
                } else {
                    int suma = stats.getLevantar() + stats.getNoLevantar();
                    int porcentajeLevantar = (suma > 0) ? (stats.getLevantar() * 100) / suma : 0;
                    int random = (int) N + (int) (Math.random() * ((0 - 100) + 1));

                    if (random > porcentajeLevantar || contador == 0) {
                        // Caso: Mienta
                        random2 = (porcentajeLevantar > 40) ? (int) N + (int) (Math.random() * ((1 - 2) + 1))
                                                            : (int) N + (int) (Math.random() * ((1 - 3) + 1));;

                        for (Iterator<List<Carta>> iter = this.cartas.values().iterator(); iter.hasNext();) {
                            List<Carta> lista = iter.next();
                            if(lista.size()==1){
                                processListWithIterator(cartas, lista.iterator(), lista, valor, random2);
                                if (lista.isEmpty()) {
                                    iter.remove(); // Elimina la lista del mapa si está vacía
                                }
                                if (cartas.size() >= random2) break;
                            }                          
                        }                     
                        for (Iterator<List<Carta>> iter = this.cartas.values().iterator(); iter.hasNext() && cartas.size() < random2;) {
                            List<Carta> lista = iter.next();
                            if(lista.size()==2){
                                processListWithIterator(cartas, lista.iterator(), lista, valor, random2);
                                if (lista.isEmpty()) {
                                    iter.remove(); // Elimina la lista del mapa si está vacía
                                }
                                if (cartas.size() >= random2) break;
                            }                          
                        }
                        for (Iterator<List<Carta>> iter = this.cartas.values().iterator(); iter.hasNext() && cartas.size() < random2;) {
                            List<Carta> lista = iter.next();
                            if(lista.size()==3){
                                processListWithIterator(cartas, lista.iterator(), lista, valor, random2);
                                if (lista.isEmpty()) {
                                    iter.remove(); // Elimina la lista del mapa si está vacía
                                }
                                if (cartas.size() >= random2) break;
                            }                          
                        }
                        for (Iterator<List<Carta>> iter = this.cartas.values().iterator(); iter.hasNext() && cartas.size() < random2;) {
                            List<Carta> lista = iter.next();
                            if(lista.size()==4){
                                processListWithIterator(cartas, lista.iterator(), lista, valor, random2);
                                if (lista.isEmpty()) {
                                    iter.remove(); // Elimina la lista del mapa si está vacía
                                }
                                if (cartas.size() >= random2) break;
                            }                          
                        }
                    } else {
                        // Caso: Diga la verdad
                        random2 = (int) N + (int) (Math.random() * ((1 - 3) + 1));
                        if (porcentajeLevantar > 40) {
                            Carta carta = this.cartas.get(valor).get(0);
                            cartas.add(carta);
                            removeCard(carta);
                        } else {
                            List<Carta> lista = this.cartas.get(valor);
                            processListWithIterator(cartas, lista.iterator(), lista, valor, random2);
                            if (lista.isEmpty()) {
                                this.cartas.remove(valor); // Elimina la lista del mapa si está vacía
                            }
                        }
                    }
                }

                System.out.println("Soy " + id + " y meto " + cartas);
                return cartas;   
        }
        System.out.println("Soy " + id + " y meto " + cartas);
        return null;
    }
    private void processListWithIterator(List<Carta> cartas, Iterator<Carta> iterator, List<Carta> lista, String valor, int random2) {
        while (iterator.hasNext()) {
            Carta carta = iterator.next();
            cartas.add(carta);
            iterator.remove();
            if (cartas.size() >= random2 || lista.isEmpty()) {
                break;
            }
        }
    }

    @Override
    public String declarar(){
        int nCartas = 0;
        String valor="";
        for (List<Carta> lista : this.cartas.values()) {
            if(lista.size()> nCartas){
                nCartas = lista.size();
                valor = lista.get(0).getValor();
            }           
        }
        return valor;
    }
    
    @Override
    boolean contestar(String valor,int nCartas, int nCartasJugador,String turno) {
        int sum =0;
        if(nCartasJugador ==0) return true;
        if(this.cartas.containsKey(valor)){
            sum = this.cartas.get(valor).size();
        }
        if(nCartas > (4 - sum)) return true;
        switch(this.tipo){
            case "inteligente":
                if(nCartasJugador <5 && turno!=indexLeftPlayer){
                    int random = (int) N + (int) (Math.random() * ((0 - 100) + 1));
                    if(random > 80){
                        return false;
                    }
                    return true;
                }else{
                    Estadisticas stats = JuegoMentiroso.estadisticasMap.get(indexLeftPlayer);
                    if(stats.getNRondas()<30){
                        if(nCartas >= 2 && sum==2){
                            return true; //levanta
                        }else if(sum>0){
                            return false;
                        }else{
                            int random = (int) N + (int) (Math.random() * ((0 - 100) + 1));
                            if(random > 80){
                                return false;
                            }
                            return true;
                        }
                    }else{
                        int suma = stats.getMentiras()+ stats.getVerdades();
                        int porcentajeMentiras = (stats.getMentiras()/suma)*100;
                        int random = (int) N + (int) (Math.random() * ((0 - 100) + 1));//numero del 0 al 100 ambos incluidos
                        if(random >porcentajeMentiras){
                            //No levanto
                            return false;
                        }else{
                            //levanto
                            return true;
                        }
                    }
                }
            case "mono":
                if(Math.random() > 0.5){
                    System.out.println("Soy " + id + " y decido que es mentira");
                    return true;
                }
                else{
                    System.out.println("Soy " + id + " y decido que es verdad");
                    return false;
                }
        }
        return false;

    }

    void setIndexLeftPlayer(String i) {
        indexLeftPlayer = i;
    }

    void setIndexRightPlayer(String i){
        indexRightPlayer = i;
    }
    
}
