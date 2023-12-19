/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hja.p4;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario_local
 */

// SI al jugador anterior no le quedan cartas entonces levantar el 100%
public class Bot extends Player{
    
    private String tipo;
    final int N = 1, M=4;
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
        switch(this.tipo){
            case "mono":
                int contador = 0;
                boolean mentir = Math.random() > 0.5;
                for(Carta c: cartas){
                    if(c.getValor().equals(valor)) {cartas.add(c);removeCard(c); contador++;}
                    if(contador >= valorEntero) break;  
                }
                if(!mentir&&contador==0) mentir = true;
                if(mentir){
                    contador =0;
                    for(Carta c: cartas){
                        if(!c.getValor().equals(valor)) {cartas.add(c); removeCard(c);contador++;}
                        if(contador >= valorEntero) break;  
                    }
                    if(contador < valorEntero){
                        int i=0;
                        while(contador< valorEntero){
                            cartas.add(this.cartas.get(i));
                            i++;contador++;
                        }
                    }
                    System.out.println("Soy " + id + " y meto " + cartas);
                    return cartas;
                }
                else{
                    for(Carta c: cartas){
                        if(c.getValor().equals(valor)) {cartas.add(c);removeCard(c); contador++;}
                        if(contador >= valorEntero) break;  
                    }
                    System.out.println("Soy " + id + " y meto " + cartas);
                    return cartas;
                }
                
                
        }
        System.out.println("Soy " + id + " y meto " + cartas);
        return this.cartas;
    }
    @Override
    public String declarar(){
        int valorEntero = (int) (Math.floor(Math.random()*(0-12+1)+12));
        return cardValues.get(valorEntero);
    }
    
    @Override
    boolean contestar(String valor,int nCartas) {
        int sum =0;
        for(Carta c : cartas){
            if(c.getValor().equals(valor)) sum++;
        }
        if(nCartas > (4 - sum)) return true;
        if(Math.random() > 0.5){
            System.out.println("Soy " + id + " y decido que es mentira");
            return true;
        }
        else{
            System.out.println("Soy " + id + " y decido que es verdad");
            return false;
        }

    }
    
}
