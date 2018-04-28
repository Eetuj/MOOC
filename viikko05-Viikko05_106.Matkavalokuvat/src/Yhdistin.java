/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */

/**
 *
 *
 *
 * @author eetukurkinen
 *
 */
import java.util.ArrayList;

import java.util.Collections;

import kuva.Fotari;

import kuva.Kuva;

public class Yhdistin {

    private String yhdistystapa;

    public Yhdistin(String yhdistystapa) {

        this.yhdistystapa = yhdistystapa;

    }

    public ArrayList<Kuva> lataaKuvat(ArrayList<String> tiedostot) {

        ArrayList<Kuva> kuvat = new ArrayList<>();

        for (String tiedosto : tiedostot) {

            kuvat.add(Fotari.lataa(tiedosto));

        }

        return kuvat;

    }

    public Kuva yhdistaKuvat(ArrayList<Kuva> kuvat) {

        int leveys = kuvat.get(0).getLeveys();

        int korkeus = kuvat.get(0).getKorkeus();

        Kuva kuva = new Kuva(leveys, korkeus);

        for (int y = 0; y < korkeus; y++) {

            for (int x = 0; x < leveys; x++) {

                asetaHaluttuPikseli(kuvat, kuva, x, y);

            }

        }

        return kuva;

    }

    public void asetaHaluttuPikseli(ArrayList<Kuva> kuvat, Kuva kuva, int x, int y) {

        ArrayList<Integer> punaiset = new ArrayList<>();

        ArrayList<Integer> vihreat = new ArrayList<>();

        ArrayList<Integer> siniset = new ArrayList<>();

        for (Kuva k : kuvat) {

            punaiset.add(k.punainen(x, y));

            vihreat.add(k.vihrea(x, y));

            siniset.add(k.sininen(x, y));

        }

        Collections.sort(punaiset);

        Collections.sort(vihreat);

        Collections.sort(siniset);

        // tummin
        int punainen = punaiset.get(0);

        int vihrea = vihreat.get(0);

        int sininen = siniset.get(0);

        if (this.yhdistystapa.equals("vaalein")) {

            punainen = punaiset.get(punaiset.size() - 1);

            vihrea = vihreat.get(vihreat.size() - 1);

            sininen = siniset.get(siniset.size() - 1);

        } else if (this.yhdistystapa.equals("mediaani")) { // mediaanit

            punainen = punaiset.get(punaiset.size() / 2);

            vihrea = vihreat.get(vihreat.size() / 2);

            sininen = siniset.get(siniset.size() / 2);

        }

        kuva.asetaVari(x, y, punainen, vihrea, sininen);

    }

}
