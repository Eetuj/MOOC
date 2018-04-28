
import java.util.*;

public class Main {

    private int tikut;
    private int valinta;
    Scanner scr = new Scanner(System.in);

    public int pelaaUudestaan() {
        System.out.println("Pelaa uudestaan (1=kyllä, 0=ei)?");
        int restart = Integer.parseInt(scr.nextLine());
        return restart;
    }

    public int tikkujenMaara() {
        System.out.println("Kuinka monta tikkua pöydällä on aluksi (10-100)?");
        while (true) {
            int tiikut = Integer.parseInt(scr.nextLine());
            if (tiikut < 10 || tiikut > 100) {
                System.out.println("Valitse luku väliltä 10-100");
            } else {
                System.out.println("Pöydällä on " + tiikut + " tikkua");
                tikut = tiikut;
                return tikut;
            }
        }

    }

    public int peliMuoto() {
        System.out.println("Vaihtoehdot:");
        System.out.println("Pelaa kaveria vastaan (1)");
        System.out.println("Pelaa tietokonetta vastaan (2)");
        System.out.println("Pelaa kouliintunutta tietokonetta vastaan (2)");
        System.out.println("Minkä vaihtoehdon valitset (1-3)?");

        int pelimuoto = Integer.parseInt(scr.nextLine());

        return pelimuoto;
    }

    public void pelivuoroAI() {
        switch (valinta) {
            case 1:
                valinta = 3;
                break;
            case 2:
                valinta = 2;
                break;
            case 3:
                valinta = 1;
                break;
        }
        tikut -= valinta;
        System.out.println("Tekoäly valitsee " + valinta);
        System.out.println("Pöydällä on " + tikut + " tikkua");
        pelivuoroPelaajayks();
    }

    public void pelivuoroPelaajayks() {
        System.out.println("Pelaaja 1: Kuinka monta tikkua nostat (1-3)?");
        valinta = Integer.parseInt(scr.nextLine());
        while (true) {
            if (valinta > 3 || valinta < 1) {
                System.out.println("Voit ottaa 1-3 tikkua");
                pelivuoroPelaajayks();
            } else if ((tikut - valinta) == 0) {
                System.out.println("Pelaaja 1, Hävisit :/");
                break;
            } else if (valinta >= 1 || valinta <= 3) {
                tikut -= valinta;
                System.out.println("Pöydällä on " + tikut + (" tikkua"));
                break;
            }
        }
    }

    public void pelivuoroPelaajakaks() {
        System.out.println("Pelaaja 2: Kuinka monta tikkua nostat (1-3)?");
        valinta = Integer.parseInt(scr.nextLine());
        while (true) {
            if (valinta > 3 || valinta < 1) {
                System.out.println("Voit ottaa 1-3 tikkua");
                pelivuoroPelaajakaks();
            } else if ((tikut - valinta) == 0) {
                System.out.println("Pelaaja 2, Hävisit :/");
                break;
            } else if (valinta >= 1 || valinta <= 3) {
                tikut -= valinta;
                System.out.println("Pöydällä on " + tikut + (" tikkua"));
                break;
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Tervetuloa tikkupeliin!");

        Main peli = new Main();
        peli.tikkujenMaara();

        while (true) {

            int vuorot = 0;

            if (peli.peliMuoto() == 1) {

                while (vuorot < peli.tikut) {
                    peli.pelivuoroPelaajayks();
                    vuorot++;
                    peli.pelivuoroPelaajakaks();
                    vuorot++;
                }
            }

            int restartti = peli.pelaaUudestaan();
            if (restartti != 0) {
                continue;
            } else {
                System.out.println("Kiitos!");
                break;
            }
        }
    }
}
