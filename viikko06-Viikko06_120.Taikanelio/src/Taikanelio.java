import java.util.ArrayList;
import java.util.Collections;

public class Taikanelio {

    private int[][] nelio;

    // valmis konstruktori
    public Taikanelio(int koko) {
        if (koko < 2) {
            koko = 2;
        }

        this.nelio = new int[koko][koko];
    }

    public ArrayList<Integer> rivienSummat() {
        ArrayList<Integer> rivit = new ArrayList<>();
        int summa = 0;
        int rivi = 0;
        while (rivi < this.nelio.length) {
            for (int i = 0; i < this.nelio.length; i++) {
                summa += this.nelio[rivi][i];
            }
            rivit.add(summa);
            summa = 0;
            rivi++;
        }
        return rivit;
    }

    public ArrayList<Integer> sarakkeidenSummat() {
        ArrayList<Integer> sarakkeet = new ArrayList<>();
        int summa = 0;
        int sarake = 0;
        while (sarake < this.nelio.length) {
            for (int[] nelio1 : this.nelio) {
                summa += nelio1[sarake];
            }
            sarakkeet.add(summa);
            summa = 0;
            sarake++;
        }
        return sarakkeet;
    }

    public ArrayList<Integer> lavistajienSummat() {
        ArrayList<Integer> lavistajat = new ArrayList<>();
        int summa = 0;
        int rivi = 0;
        while (rivi < this.nelio.length) {
            for (int i = 0; i < this.nelio.length; i++) {
                if (i == rivi) {
                    summa += this.nelio[rivi][i];
                }
            }
            rivi++;
        }
        lavistajat.add(summa);
        summa=0;
        int sarake = 0;
        while (rivi > 0) {
            summa += this.nelio[rivi - 1][sarake];
            sarake++;
            rivi--;
        }
        lavistajat.add(summa);
        summa=0;
        return lavistajat;
    }

    // valmiit apumetodit -- älä koske näihin
    public boolean onTaikanelio() {
        return summatSamat() && kaikkiNumerotEri();
    }

    public ArrayList<Integer> annaKaikkiNumerot() {
        ArrayList<Integer> numerot = new ArrayList<>();
        for (int y = 0; y < nelio.length; y++) {
            for (int x = 0; x < nelio[y].length; x++) {
                numerot.add(nelio[y][x]);
            }
        }

        return numerot;
    }

    public boolean kaikkiNumerotEri() {
        ArrayList<Integer> numerot = annaKaikkiNumerot();

        Collections.sort(numerot);
        for (int i = 1; i < numerot.size(); i++) {
            if (numerot.get(i - 1) == numerot.get(i)) {
                return false;
            }
        }

        return true;
    }

    public boolean summatSamat() {
        ArrayList<Integer> summat = new ArrayList<>();
        summat.addAll(rivienSummat());
        summat.addAll(sarakkeidenSummat());
        summat.addAll(lavistajienSummat());

        if (summat.size() < 3) {
            return false;
        }

        for (int i = 1; i < summat.size(); i++) {
            if (summat.get(i - 1) != summat.get(i)) {
                return false;
            }
        }

        return true;
    }

    public int annaArvo(int x, int y) {
        if (x < 0 || y < 0 || x >= getLeveys() || y >= getKorkeus()) {
            return - 1;
        }

        return this.nelio[y][x];
    }

    public void asetaArvo(int x, int y, int arvo) {
        if (x < 0 || y < 0 || x >= getLeveys() || y >= getKorkeus()) {
            return;
        }

        this.nelio[y][x] = arvo;
    }

    public int getLeveys() {
        return this.nelio.length;
    }

    public int getKorkeus() {
        return this.nelio.length;
    }

    @Override
    public String toString() {
        String palautus = "";
        for (int y = 0; y < nelio.length; y++) {
            for (int x = 0; x < nelio[y].length; x++) {
                palautus += nelio[y][x] + "\t";
            }

            palautus += "\n";
        }

        return palautus;
    }
}
