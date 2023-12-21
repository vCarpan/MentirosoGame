/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hja.p4;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author penap
 */
public class Main {

    public static void main(String[] args) {
        
        Player p;
        Map<String, Integer> victorias = new HashMap<>();
        for(int i = 0; i < 100; i++){
            JuegoMentiroso juego = new JuegoMentiroso();
            System.out.println("##########################JUEGO Numero: " + i);
            p = juego.jugar();
            if(victorias.containsKey(p.getId())){
                victorias.put(p.getId(), victorias.get(p.getId())+1);
            }
            else{
                victorias.put(p.getId(), 1);
            }
        }
            
        
        System.out.println("victorias: " + victorias.toString());
    }
}
