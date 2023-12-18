/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hja.p4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author usuario_local
 */
public class Player {
    
    private List<Carta> cartas;
    private Scanner scanner;
    private String id;
    private static List<String> cardValues = new ArrayList<>();
    public Player(String id){
        this.id = id;
        this.cartas = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        Collections.addAll(cardValues, "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A");
    }
    public void addCard(Carta c){
        this.cartas.add(c);
    }
    public Pair<String, List<Carta>> declararYJugar() {
        System.out.println("Mano actual del jugador: " + cartas);
        System.out.print("---DECLARACION "+id+"---");
        System.out.print("Elige Cartas: Ej(Ac Ad)");
        String cartas = scanner.nextLine();
        String[] arrayCartas = cartas.split(" ");
        List<Carta> listCartas = new ArrayList<Carta>();
        for(String carta: arrayCartas){
            listCartas.add(new Carta(carta.charAt(0) + "", carta.charAt(1) + ""));
        }
        System.out.print("Valor de la Carta: Ej(J)");
        String valor = scanner.nextLine();
        if(!procesarDeclaracion(cartas,valor)){
            System.out.println("Error: Declaracion incorrecta");
            return declararYJugar();
        }
        return new Pair<String, List<Carta>>(valor,listCartas);
        
        
    }
    private boolean procesarDeclaracion(String cartas,String valor) {
        String[] arrayCartas = cartas.split(" ");
        for (String arrayCarta : arrayCartas) {
            if (arrayCarta.length() != 2) {
                System.out.println("Formato no valido");
                return false;
            }
            Carta c = new Carta(arrayCarta.charAt(0) + "", arrayCarta.charAt(1) + "");
            boolean encontrado = false;
            for(Carta aux: this.cartas){
                if(aux.isEqual(c)){
                    encontrado = true;
                    break;
                }
            }
            if(!encontrado){
                System.out.println("No tienes esas cartas pillin!");
                return false;
            }
        }
        if(!checkValor(valor)){
            System.out.println("Valor incorrecto");
            return false;
        }
        return true;
    }
    
    private boolean checkValor(String valor){
        return cardValues.contains(valor);
        
    }
    public boolean isWin(){
        return cartas.isEmpty();
    }

    boolean contestar() {
        System.out.println("Quiere destapar la verdad? (responda con 0 o 1)");
        String decision = scanner.nextLine();
        if(decision.equals("0")){
            return false;
        }
        else{
            return true;
        } 
    }

    void addCards(List<Carta> mesa) {
        cartas.addAll(mesa);
    }
}
