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
        List<Carta> cartas = new ArrayList();
        int valorEntero = (int) (Math.floor(Math.random()*(N-M+1)+M));
        while(valorEntero > this.cartas.size()){
            valorEntero = (int) (Math.floor(Math.random()*(N-M+1)+M));
        }
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
                    contador =0;
                    for (List<Carta> lista : this.cartas.values()) {
                        for (int i = 0; i < lista.size(); i++) {
                            if(!lista.get(i).getValor().equals(valor)) {cartas.add(lista.get(i)); lista.remove(i);contador++;}              
                            if(contador >= valorEntero) break; 
                        }
                        
                        if(contador >= valorEntero) break; 
                    }
                    if(contador < valorEntero && this.cartas.size()>0){
                        for (List<Carta> lista : this.cartas.values()) {
                            for (int i = 0; i < lista.size(); i++) {
                                cartas.add(lista.get(i));
                                lista.remove(i);                             
                                removeCard(lista.get(i));
                                contador++;
                                if(contador >= valorEntero) break; 
                            }
                            if(contador >= valorEntero) break; 
                        }
                        
                    }
                    System.out.println("Soy " + id + " y meto " + cartas);
                    return cartas;
                }
                else{
                    for(Carta c: this.cartas.get(valor)){
                        if(c.getValor().equals(valor)) {cartas.add(c);removeCard(c); contador++;}
                        if(contador >= valorEntero) break;  
                    }
                    System.out.println("Soy " + id + " y meto " + cartas);
                    return cartas;
                }
            case "inteligente":
                Estadisticas stats = JuegoMentiroso.estadisticasMap.get(indexRightPlayer);
                if(stats.getNRondas()<10){
                    if(contador > 0){
                        cartas.add(this.cartas.get(valor).get(0));
                        removeCard(this.cartas.get(valor).get(0));
                        System.out.println("Soy " + id + " y meto " + cartas);
                        return cartas;
                    }else{
                        int random2 = (int) (Math.floor(Math.random()*(1-2+1)+2));
                        for (List<Carta> lista : this.cartas.values()) {
                            if(lista.size()==1 && lista.get(0).getValor()!=valor){
                                for(Carta c: lista){
                                    cartas.add(c);
                                    removeCard(c);
                                    if(cartas.size()>=random2) break;
                                }
                            }   
                            if(cartas.size()>random2) break;                   
                        }
                        for (List<Carta> lista : this.cartas.values()) {
                            if(lista.size()==2 && lista.get(0).getValor()!=valor){
                                for(Carta c: lista){
                                    cartas.add(c);
                                    removeCard(c);
                                    if(cartas.size()>=random2) break;
                                }
                            }   
                            if(cartas.size()>random2) break;
                        }
                        for (List<Carta> lista : this.cartas.values()) {
                            if(lista.size()==3 && lista.get(0).getValor()!=valor){
                                for(Carta c: lista){
                                    cartas.add(c);
                                    removeCard(c);
                                    if(cartas.size()>=random2) break;
                                }
                            }
                            if(cartas.size()>random2) break;
                        }
                        for (List<Carta> lista : this.cartas.values()) {
                             if(lista.size()==4 && lista.get(0).getValor()!=valor){
                                for(Carta c: lista){
                                    cartas.add(c);
                                    removeCard(c);
                                    if(cartas.size()>=random2) break;
                                }
                            }   
                            if(cartas.size()>random2) break;                  
                        }
                        System.out.println("Soy " + id + " y meto " + cartas);
                        return cartas;
                    }
                }else{
                    int suma = stats.getLevantar()+ stats.getNoLevantar();
                    int porcentajeLevantar = (stats.getLevantar()/suma)*100;
                    int random = (int) (Math.floor(Math.random()*(0-100+1)+100));//numero del 0 al 100 ambos incluidos
                    if(random >porcentajeLevantar || contador==0){
                        //Mentir
                        int random2 = (int) (Math.floor(Math.random()*(1-3+1)+3));
                        if(porcentajeLevantar >40){
                            random2 = (int) (Math.floor(Math.random()*(1-2+1)+2));
                        }
                        for (List<Carta> lista : this.cartas.values()) {
                            if(lista.size()==1 && lista.get(0).getValor()!=valor){
                                for(Carta c: lista){
                                    cartas.add(c);
                                    removeCard(c);
                                    if(cartas.size()>=random2) break;
                                }
                            }   
                            if(cartas.size()>random2) break;                   
                        }
                        for (List<Carta> lista : this.cartas.values()) {
                            if(lista.size()==2 && lista.get(0).getValor()!=valor){
                                for(Carta c: lista){
                                    cartas.add(c);
                                    removeCard(c);
                                    if(cartas.size()>=random2) break;
                                }
                            }   
                            if(cartas.size()>random2) break;
                        }
                        for (List<Carta> lista : this.cartas.values()) {
                            if(lista.size()==3 && lista.get(0).getValor()!=valor){
                                for(Carta c: lista){
                                    cartas.add(c);
                                    removeCard(c);
                                    if(cartas.size()>=random2) break;
                                }
                            }
                            if(cartas.size()>random2) break;
                        }
                        for (List<Carta> lista : this.cartas.values()) {
                             if(lista.size()==4 && lista.get(0).getValor()!=valor){
                                for(Carta c: lista){
                                    cartas.add(c);
                                    removeCard(c);
                                    if(cartas.size()>=random2) break;
                                }
                            }   
                            if(cartas.size()>random2) break;                  
                        }
                    }else{
                        //Decir la verdad
                        int random2 = (int) (Math.floor(Math.random()*(1-3+1)+3));
                        if(porcentajeLevantar >40){
                            cartas.add(this.cartas.get(valor).get(0));
                            removeCard(this.cartas.get(valor).get(0));
                        }else{
                            Iterator<Carta> iterator = this.cartas.get(valor).iterator();
                            while (iterator.hasNext()) {
                                Carta carta = iterator.next();
                                cartas.add(carta);
                                iterator.remove();
                            }
                            // Elimina la entrada del mapa
                            this.cartas.remove(valor);
                        }
                        System.out.println("Soy " + id + " y meto " + cartas);
                        return cartas;
                    }
                }
              
        }
        System.out.println("Soy " + id + " y meto " + cartas);
        return null;
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
    boolean contestar(String valor,int nCartas) {
        int sum =0;
        if(this.cartas.containsKey(valor)){
            sum = this.cartas.get(valor).size();
        }
        if(nCartas > (4 - sum)) return true;
        switch(this.tipo){
            case "inteligente":
                Estadisticas stats = JuegoMentiroso.estadisticasMap.get(indexLeftPlayer);
                if(stats.getNRondas()<30){
                    if(nCartas >= 2 && sum==2){
                        return true; //levanta
                    }else if(sum>0){
                        return false;
                    }else{
                        int random = (int) (Math.floor(Math.random()*(0-100+1)+100));
                        if(random > 80){
                            return false;
                        }
                        return true;
                    }
                }else{
                    int suma = stats.getMentiras()+ stats.getVerdades();
                    int porcentajeMentiras = (stats.getMentiras()/suma)*100;
                    int random = (int) (Math.floor(Math.random()*(0-100+1)+100));//numero del 0 al 100 ambos incluidos
                    if(random >porcentajeMentiras){
                        //No levanto
                        return false;
                    }else{
                        //levanto
                        return true;
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
