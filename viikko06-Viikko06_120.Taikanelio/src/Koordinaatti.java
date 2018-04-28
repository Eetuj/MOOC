/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author EetujK
 */
public class Koordinaatti {

    private int x;
    private int y;

    public Koordinaatti() {
    }

    public void asetaKoordinaatit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int haeX() {
        return x;
    }

    public int haeY() {
        return y;
    }

}
