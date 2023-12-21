/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hja.p4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author usuario_local
 */
public class Player {
    
    private Scanner scanner;
    protected String id;
    protected static List<String> cardValues = new ArrayList<>();
    protected Map<String, List<Carta>> cartas = new HashMap<>();
    public Player(String id){
        this.id = id;
        this.scanner = new Scanner(System.in);
        Collections.addAll(cardValues, "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A");
    }
    public void addCard(Carta c){
        if(cartas.containsKey(c.getValor())){
            cartas.get(c.getValor()).add(c);
        }else{
            List<Carta> cartasList = new ArrayList<>();
            cartasList.add(c);
            cartas.put(c.getValor(),  cartasList);
        }
    }
    public String declarar(){
        System.out.println("Mano actual del jugador: " + cartas);
        System.out.print("Valor de la Carta: Ej(J)");
        String valor = scanner.nextLine();
        if(!checkValor(valor)){
            System.out.println("Error: Declaracion incorrecta");
            return declarar();
        }
        return valor;
        
    }
    public List<Carta> jugar(String valor) {
        System.out.println("Mano actual del jugador: " + cartas);
        System.out.print("Elige Cartas: Ej(Ac Ad)");
        String cartas = scanner.nextLine();
        String[] arrayCartas = cartas.split(" ");
        List<Carta> listCartas = new ArrayList<Carta>();  
        if(!procesarJugada(arrayCartas)){
            System.out.println("Error: Cartas incorrectas");
            return jugar(valor);
        }
        for(String carta: arrayCartas){
            Carta c = new Carta(carta.charAt(0) + "", carta.charAt(1) + "");
            listCartas.add(c);
            removeCard(c);
        }  
        return listCartas;   
    }
    public void removeCard(Carta c) {
        Iterator<Map.Entry<String, List<Carta>>> iterator = cartas.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, List<Carta>> entry = iterator.next();
            List<Carta> lista = entry.getValue();

            int i = 0;
            while (i < lista.size()) {
                if (lista.get(i).isEqual(c)) {
                    lista.remove(i);
                    if (lista.isEmpty()) {
                        iterator.remove();  // Eliminar la entrada del mapa si la lista está vacía
                    }
                    break;  // Salir del bucle interno si la carta es encontrada y eliminada
                }
                i++;
            }
        }
    }

    private boolean procesarJugada(String[] arrayCartas) {
        for (String arrayCarta : arrayCartas) {
            if (arrayCarta.length() != 2) {
                System.out.println("Formato no valido");
                return false;
            }
            Carta c = new Carta(arrayCarta.charAt(0) + "", arrayCarta.charAt(1) + "");
            boolean encontrado = false;
            for (List<Carta> lista : cartas.values()) {
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).isEqual(c)) {
                        encontrado = true;
                        break; 
                    }
                }
                if (encontrado) {
                    break; 
                }
            }
            if(!encontrado){
                System.out.println("No tienes esas cartas pillin!");
                return false;
            }
        }
        return true;
    }
    public String getId(){
        return id;
    }
    private boolean checkValor(String valor){
        return cardValues.contains(valor);
        
    }
    public boolean isWin(){
        return cartas.isEmpty();
    }

    boolean contestar(String valor,int nCartas) {
        System.out.println("Quiere destapar la verdad? (responda con 0 -> No o 1-> Si)");
        String decision = scanner.nextLine();
        return decision.equals("1");
    }

    void addCards(List<Carta> mesa) {
        for(Carta c: mesa){
            addCard(c);
        }
    }
}
