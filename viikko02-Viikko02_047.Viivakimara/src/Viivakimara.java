
import robotti.Ohjain;

public class Viivakimara {

    public static void main(String[] args) {

        Ohjain.kaynnista();

        // testaa metodejasi täällä
        viivakimara(5);

        Ohjain.sammuta();

    }

    public static void liikuMonta(int maara) {

        int i = 0;

        while (maara > i) {

            Ohjain.liiku();

            i++;

        }

    }

    public static void piirraNelio(int sivu) {

        liikuMonta(sivu);

        Ohjain.oikea();

        liikuMonta(sivu);

        Ohjain.oikea();

        liikuMonta(sivu);

        Ohjain.oikea();

        liikuMonta(sivu);

        Ohjain.oikea();

    }

    public static void sisakkaisetNeliot(int montako) {

        rajatutSisakkaisetNeliot(montako, 1);

    }

    public static void rajatutSisakkaisetNeliot(int suurin, int pienin) {

        for (int i = pienin; i <= suurin; i++) {

            piirraNelio(i);

        }

    }

    public static void viivakimara(int koko) {

        rajatutSisakkaisetNeliot(koko, koko - 2);

        liikuMonta(koko);

        Ohjain.oikea();

        liikuMonta(koko);

        Ohjain.oikea();

        rajatutSisakkaisetNeliot(koko - 1, koko - 2);

    }

}
