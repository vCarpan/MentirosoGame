/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hja.p4;

/**
 *
 * @author penap
 */
public class Main {

    public static void main(String[] args) {
        JuegoMentiroso juego = new JuegoMentiroso();
        Player p = juego.jugar();
        System.out.println("Felicidades ha ganado al mentiroso, recuerde que en la vida no hay que mentir." +p.getId());
    }
}
