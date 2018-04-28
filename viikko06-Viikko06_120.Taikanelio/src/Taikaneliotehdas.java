
public class Taikaneliotehdas {

    public Taikanelio luoTaikanelio(int koko) {

        Taikanelio nelio = new Taikanelio(koko);
        Koordinaatti koordinaatit = new Koordinaatti();
        if (nelio.getLeveys() % 2 != 0) {
            int lisattavaLuku = 1;
            while (lisattavaLuku <= nelio.getKorkeus() * nelio.getKorkeus()) {
                if (nelio.annaArvo(koko / 2, 0) == 0) {
                    nelio.asetaArvo(koko / 2, 0, lisattavaLuku);
                    koordinaatit.asetaKoordinaatit(koko / 2, 0);
                    lisattavaLuku++;
                } else {        
                    koordinaatit = liikuNeliossa(koordinaatit.haeX(), koordinaatit.haeY(), nelio);
                    nelio.asetaArvo(koordinaatit.haeX(), koordinaatit.haeY(), lisattavaLuku);
                    lisattavaLuku++;
                }
            }
        }

        // toteuta taikaneliön luominen Siamese method -algoritmilla tänne
        return nelio;
    }

    public Koordinaatti liikuNeliossa(int x, int y, Taikanelio nelio) {
        int sarake = x + 1;
        int rivi = y - 1;
        while (true) {
            if (rivi < 0) {
                rivi = nelio.getKorkeus() - 1;
            } else if (sarake >= nelio.getLeveys()) {
                sarake = 0;
            } else if (nelio.annaArvo(sarake, rivi) != 0) {
                sarake = x;
                rivi = y + 1;
            } else {
                break;
            }
        }
        Koordinaatti uudetKoordinaatit = new Koordinaatti();
        uudetKoordinaatit.asetaKoordinaatit(sarake, rivi);

        return uudetKoordinaatit;
    }
}
