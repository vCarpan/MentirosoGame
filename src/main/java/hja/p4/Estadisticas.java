/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hja.p4;

public class Estadisticas  {
    private int mentiras;
    private int verdades;
    private int levantar;
    private int noLevantar;
    private int nPartidas;

    public Estadisticas() {
        this.nPartidas = 0;
        this.mentiras = 0;
        this.verdades = 0;
        this.levantar = 0;
        this.noLevantar = 0;
    }
    public int getNPartidas(){
        return nPartidas;
    }
    public int getMentiras() {
        return mentiras;
    }

    public int getVerdades() {
        return verdades;
    }

    public int getLevantar() {
        return levantar;
    }

    public int getNoLevantar() {
        return noLevantar;
    }

    public void incrementarMentiras() {
        this.mentiras++;
    }

    public void incrementarVerdades() {
        this.verdades++;
    }

    public void incrementarLevantar() {
        this.levantar++;
    }

    public void incrementarNoLevantar() {
        this.noLevantar++;
    }

    void setMentiras(int aInt) {
        mentiras = aInt;
    }
    void setNPartidas(int aInt){
        nPartidas = aInt;
    }
    void setVerdades(int aInt) {
        verdades = aInt;
    }

    void setLevantar(int aInt) {
        levantar = aInt;
    }

    void setNoLevantar(int aInt) {
        noLevantar = aInt;
    }
}