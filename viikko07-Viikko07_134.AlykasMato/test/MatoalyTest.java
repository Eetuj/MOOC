
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Set;
import java.util.TreeSet;
import junit.framework.Assert;
import matopeli.Matopeli;
import org.junit.Test;

@Points("134")
public class MatoalyTest {

    @Test
    public void liikkuuMuuallekinKuinYlos() {

        Matopeli matopeli = new Matopeli();
        Matoaly matoaly = new Matoaly();

        for (int i = 0; i < 30; i++) {
            String suunta = null;
            try {
                suunta = matoaly.annaSiirto(matopeli);
            } catch (Throwable t) {
                if (t instanceof ArrayIndexOutOfBoundsException) {
                    Assert.fail("Haet tietoa taulukon ulkopuolelta. Virhe oli: " + t.getMessage());
                }

                Assert.fail("Virhe siirtoa haettaessa tekoälyltä: " + t.getMessage());
            }

            if (suunta == null) {
                Assert.fail("Siirron tulee olla joko YLOS, ALAS, VASEN tai OIKEA, nyt se oli null.");
            }

            try {
                matopeli.liiku(suunta);
            } catch (Throwable t) {
                Assert.fail("Virhe kun yritettiin antaa matopelille liikettä " + suunta + ", virhe oli: " + t.getMessage());
            }

            if (!suunta.equals("YLOS")) {
                return;
            }
        }

        Assert.fail("Käytit vain siirtoa YLOS. Ei vielä kovin fiksu mato.");
    }

    @Test
    public void kayttaaKaikkiaSiirtoja() {
        Set<String> siirrot = new TreeSet<String>();
        for (int i = 0; i < 10; i++) {
            Matopeli matopeli = new Matopeli();
            Matoaly matoaly = new Matoaly();

            int askeleita = 0;
            while (!matopeli.peliLoppu()) {
                String siirto = null;
                try {
                    siirto = matoaly.annaSiirto(matopeli);
                } catch (Throwable t) {
                    if (t instanceof ArrayIndexOutOfBoundsException) {
                        Assert.fail("Haet tietoa taulukon ulkopuolelta. Virhe oli: " + t.getMessage());
                    }

                    Assert.fail("Virhe matoalyssa: " + t.getMessage());
                }

                if (siirto == null) {
                    Assert.fail("Siirron tulee olla joko YLOS, ALAS, VASEN tai OIKEA. Nyt se oli null.");
                }

                siirrot.add(siirto);
                matopeli.liiku(siirto);

                askeleita++;
                if (askeleita > 300) {
                    break;
                }
            }
        }

        Assert.assertTrue("Käytäthän kaikkia mahdollisia siirtoja? Nyt käytössä oli: " + siirrot, siirrot.size() == 4);
    }

    @Test
    public void eiAiheutaVirhetilanteita() {
        liikkuuMuuallekinKuinYlos();
        kayttaaKaikkiaSiirtoja();
        for (int i = 0; i < 20; i++) {
            Matopeli matopeli = new Matopeli();
            Matoaly matoaly = new Matoaly();

            int askeleita = 0;
            while (!matopeli.peliLoppu()) {
                String siirto = null;
                try {
                    siirto = matoaly.annaSiirto(matopeli);
                } catch (Throwable t) {
                    if (t instanceof ArrayIndexOutOfBoundsException) {
                        Assert.fail("Haet tietoa taulukon ulkopuolelta. Virhe oli: " + t.getMessage());
                    }

                    Assert.fail("Virhe matoalyssa: " + t.getMessage());
                }

                if (siirto == null) {
                    Assert.fail("Siirron tulee olla joko YLOS, ALAS, VASEN tai OIKEA. Nyt se oli null.");
                }

                matopeli.liiku(siirto);

                askeleita++;
                if (askeleita > 300) {
                    break;
                }
            }
        }
    }

    @Test
    public void yrittaaKerataOmenoita() {
        eiAiheutaVirhetilanteita();

        int keratytYhteensa = 0;

        for (int i = 0; i < 20; i++) {
            int keratytOmenat = 0;
            Matopeli matopeli = new Matopeli();
            Matoaly matoaly = new Matoaly();

            int askeleita = 0;
            while (!matopeli.peliLoppu()) {
                String siirto = matoaly.annaSiirto(matopeli);
                if (siirto == null) {
                    Assert.fail("Siirron pitäisi olla joko YLOS, ALAS, VASEN tai OIKEA. Nyt se oli null.");
                }

                matopeli.liiku(siirto);

                askeleita++;
                if (askeleita > 300) {
                    break;
                }

                if (matopeli.madonPalat().size() > 3) {
                    keratytOmenat = matopeli.madonPalat().size() - 3;
                }
            }

            keratytYhteensa += keratytOmenat;
        }

        Assert.assertTrue("Keräsit kahdessakymmenessä korkeintaan 300 liikettä sisältäneessä pelissä yhteensä " + keratytYhteensa + " omenaa. Pystyt parempaan!", keratytYhteensa > 60);
    }
}
