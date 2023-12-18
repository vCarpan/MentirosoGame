/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hja.p4;

public class Carta {
    private String valor;
    private String palo;

    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
    }

    public Carta(String cartas) {
        this.valor = cartas.charAt(0)+ "";
        this.palo = cartas.charAt(1)+"";
    }

    @Override
    public String toString() {
        return valor + palo;
    }
    
    public boolean isEqual(Carta a){
        return this.valor.equals(a.getValor()) && this.palo.equals(a.getPalo());
    }

    public String getValor() {
        return valor;
    }
    
        public String getPalo() {
        return palo;
    }
}
