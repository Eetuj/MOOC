
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.Random;
import kuva.Kuva;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class YhdistinTest {

    private final String klass = "Yhdistin";

    @Test
    @Points("106.1")
    public void luokkaYhdistinOlemassa() {
        try {
            Reflex.reflect(klass).requirePublic();
        } catch (Throwable t) {
            fail("Olethan luonut luokan Yhdistin?");
        }
    }

    @Test
    @Points("106.1")
    public void luokanYhdistinKonstruktoriSaaMerkkijononParametrina() {
        luokkaYhdistinOlemassa();
        try {
            Reflex.reflect(klass).requirePublic().ctor().taking(String.class).requirePublic();
        } catch (Throwable t) {
            fail("Olethan luonut luokalle Yhdistin konstruktorin, jolle voidaan antaa parametrina merkkijono?");
        }
    }

    @Test
    @Points("106.1")
    public void lataaKuvatMetodi() {
        luokanYhdistinKonstruktoriSaaMerkkijononParametrina();
        try {
            Reflex.reflect(klass).method("lataaKuvat").returning(ArrayList.class).taking(ArrayList.class);
        } catch (Throwable t) {
            fail("Olethan luonut luokalle Yhdistin metodin \"lataaKuvat\", joka palauttaa ArrayList<Kuva>-tyyppisen listan, ja joka saa parametrina ArrayList<String>-listan?");
        }
    }

    @Test
    @Points("106.1")
    public void lataaKuvatMetodiTest() {
        lataaKuvatMetodi();

        ArrayList<String> kuvat = new ArrayList<>();
        kuvat.add("ilta-small.jpg");
        kuvat.add("ilta-small-2.jpg");

        ArrayList<Kuva> vastaus = null;
        try {
            Object yhdistin = luoYhdistin("vaalein");
            vastaus = Reflex.reflect(klass).method("lataaKuvat").returning(ArrayList.class).taking(ArrayList.class).invokeOn(yhdistin, kuvat);
        } catch (Throwable t) {
            fail("Virhe: " + t.getMessage());
        }

        assertNotNull("Metodin lataaKuvat tulee palauttaa ArrayList<Kuva> tyyppinen lista, johon luetaan kuvat parametrina annetusta merkkijonolistasta.", vastaus);
        assertTrue("Metodin lataaKuvat tulee lukea kaikki annetut kuvat listalle. Nyt luettavia kuvia oli 2, mutta vastauksessa kuvia oli vain " + vastaus.size(), vastaus.size() == 2);
    }

    @Test
    @Points("106.1")
    public void lataaKuvatMetodiTest2() {
        lataaKuvatMetodi();

        ArrayList<String> kuvat = new ArrayList<>();
        kuvat.add("ilta-small.jpg");
        kuvat.add("kukka-small.jpg");
        kuvat.add("ilta-small.jpg");

        ArrayList<Kuva> vastaus = null;
        try {
            Object yhdistin = luoYhdistin("vaalein");
            vastaus = Reflex.reflect(klass).method("lataaKuvat").returning(ArrayList.class).taking(ArrayList.class).invokeOn(yhdistin, kuvat);
        } catch (Throwable t) {
            fail("Virhe: " + t.getMessage());
        }

        assertNotNull("Metodin lataaKuvat tulee palauttaa ArrayList<Kuva> tyyppinen lista, johon luetaan kuvat parametrina annetusta merkkijonolistasta.", vastaus);
        assertTrue("Metodin lataaKuvat tulee lukea kaikki annetut kuvat listalle. Nyt luettavia kuvia oli 3, mutta vastauksessa kuvia oli vain " + vastaus.size(), vastaus.size() == 3);

        boolean kukkaFound = false;
        boolean iltaFound = false;
        for (Kuva kuva : vastaus) {
            if (kuva.getKorkeus() == 57) {
                kukkaFound = true;
            }

            if (kuva.getKorkeus() == 67) {
                iltaFound = true;
            }
        }

        assertTrue("Metodin lataaKuvat tulee lukea kaikki annetut kuvat listalle. Nyt osa kuvista jäi lukematta.", iltaFound && kukkaFound);
    }

    @Test
    @Points("106.2")
    public void yhdistaKuvatMetodiOlemassa() {
        lataaKuvatMetodiTest2();
        try {
            Reflex.reflect(klass).method("yhdistaKuvat").returning(Kuva.class).taking(ArrayList.class).requirePublic();
        } catch (Throwable t) {
            fail("Olethan luonut luokalle Yhdistin metodin \"yhdistaKuvat\", joka palauttaa Kuva-tyyppisen olion, ja joka saa parametrina ArrayList<Kuva>-listan?");
        }
    }

    @Test
    @Points("106.2")
    public void yhdistaKuvatMetodiPalauttaaKuvan() {
        yhdistaKuvatMetodiOlemassa();

        Object yhdistin = luoYhdistin("vaalein");
        ArrayList<Kuva> kuvat = lataaKuvat(yhdistin, "ilta-small.jpg", "ilta-small.jpg");
        Kuva kuva = null;
        try {
            kuva = Reflex.reflect(klass).method("yhdistaKuvat").returning(Kuva.class).taking(ArrayList.class).invokeOn(yhdistin, kuvat);
        } catch (Throwable t) {
            fail("Virhe: " + t.getMessage());
        }

        assertNotNull("Metodin yhdistaKuvat tulee palauttaa Kuva-tyyppinen olio, johon yhdistetään kuvat parametrina annetusta kuvalistasta.", kuva);
    }

    @Test
    @Points("106.2")
    public void palautettuKuvaOikeanKokoinen() {
        yhdistaKuvatMetodiPalauttaaKuvan();

        Kuva alkup = lataaKuvat(luoYhdistin("vaalein"), "ilta-small.jpg").get(0);
        Kuva yhdistetty = yhdistaKuvat("vaalein", "ilta-small.jpg", "ilta-small.jpg");

        assertTrue("Kun kuvia yhdistetään, yhdistetyn kuvan leveyden ja korkeuden tulee olla sama kuin yhdistettävillä kuvilla.", alkup.getLeveys() == yhdistetty.getLeveys() && alkup.getKorkeus() == yhdistetty.getKorkeus());
    }

    @Test
    @Points("106.2")
    public void palautettuKuvaSisaltaaOikeanDatan() {
        palautettuKuvaOikeanKokoinen();

        Kuva alkup = lataaKuvat(luoYhdistin("vaalein"), "ilta-small.jpg").get(0);
        Kuva kuva = yhdistaKuvat("vaalein", "ilta-small.jpg", "ilta-small.jpg");
        assertTrue("Kun kuvia yhdistetään, yhdistetyn kuvan leveyden ja korkeuden tulee olla sama kuin yhdistettävillä kuvilla.", 100 == kuva.getLeveys() && 67 == kuva.getKorkeus());
    
        int leveys = kuva.getLeveys();
        int korkeus = kuva.getKorkeus();
        
        Random rnd = new Random();
        
        for (int i = 0; i < 10; i++) {
            int testX = rnd.nextInt(leveys);
            int testY = rnd.nextInt(korkeus);
            
            assertTrue("Yhdistathan kuvien väriarvot oikein, nyt punainen väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.punainen(testX, testY) == kuva.punainen(testX, testY));
            assertTrue("Yhdistathan kuvien väriarvot oikein, nyt vihreä väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.vihrea(testX, testY) == kuva.vihrea(testX, testY));
            assertTrue("Yhdistathan kuvien väriarvot oikein, nyt sininen väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.sininen(testX, testY) == kuva.sininen(testX, testY));
        }
    }
    
    @Test
    @Points("106.2")
    public void vaaleimmanYhdistysToimii() {
        palautettuKuvaOikeanKokoinen();

        Kuva kuva = yhdistaKuvat("vaalein", "ilta-small.jpg", "white.png", "black.png");
        Kuva alkup = lataaKuvat(luoYhdistin("vaalein"), "white.png").get(0);
        assertTrue("Kun kuvia yhdistetään, yhdistetyn kuvan leveyden ja korkeuden tulee olla sama kuin yhdistettävillä kuvilla.", 100 == kuva.getLeveys() && 67 == kuva.getKorkeus());
    
        int leveys = kuva.getLeveys();
        int korkeus = kuva.getKorkeus();
        
        Random rnd = new Random();
        
        for (int i = 0; i < 10; i++) {
            int testX = rnd.nextInt(leveys);
            int testY = rnd.nextInt(korkeus);
            
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on vaalein, nyt punainen väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.punainen(testX, testY) == kuva.punainen(testX, testY));
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on vaalein, nyt vihreä väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.vihrea(testX, testY) == kuva.vihrea(testX, testY));
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on vaalein, nyt sininen väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.sininen(testX, testY) == kuva.sininen(testX, testY));
        }
    }
    
    
    @Test
    @Points("106.3")
    public void tummimmanYhdistysToimii() {
        palautettuKuvaOikeanKokoinen();

        Kuva kuva = yhdistaKuvat("tummin", "black.png", "ilta-small.jpg", "white.png");
        Kuva alkup = lataaKuvat(luoYhdistin("tummin"), "black.png").get(0);
        assertTrue("Kun kuvia yhdistetään, yhdistetyn kuvan leveyden ja korkeuden tulee olla sama kuin yhdistettävillä kuvilla.", 100 == kuva.getLeveys() && 67 == kuva.getKorkeus());
    
        int leveys = kuva.getLeveys();
        int korkeus = kuva.getKorkeus();
        
        Random rnd = new Random();
        
        for (int i = 0; i < 10; i++) {
            int testX = rnd.nextInt(leveys);
            int testY = rnd.nextInt(korkeus);
            
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on tummin, nyt punainen väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.punainen(testX, testY) == kuva.punainen(testX, testY));
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on tummin, nyt vihreä väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.vihrea(testX, testY) == kuva.vihrea(testX, testY));
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on tummin, nyt sininen väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.sininen(testX, testY) == kuva.sininen(testX, testY));
        }
    }
    
    
    @Test
    @Points("106.4")
    public void mediaaniToimiiOsaYksi() {
        palautettuKuvaOikeanKokoinen();

        Kuva kuva = yhdistaKuvat("mediaani", "black.png", "black.png", "white.png");
        Kuva alkup = lataaKuvat(luoYhdistin("mediaani"), "black.png").get(0);
        assertTrue("Kun kuvia yhdistetään, yhdistetyn kuvan leveyden ja korkeuden tulee olla sama kuin yhdistettävillä kuvilla.", 100 == kuva.getLeveys() && 67 == kuva.getKorkeus());
    
        int leveys = kuva.getLeveys();
        int korkeus = kuva.getKorkeus();
        
        Random rnd = new Random();
        
        for (int i = 0; i < 10; i++) {
            int testX = rnd.nextInt(leveys);
            int testY = rnd.nextInt(korkeus);
            
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on mediaani, nyt punainen väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.punainen(testX, testY) == kuva.punainen(testX, testY));
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on mediaani, nyt vihreä väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.vihrea(testX, testY) == kuva.vihrea(testX, testY));
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on mediaani, nyt sininen väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.sininen(testX, testY) == kuva.sininen(testX, testY));
        }
    }
    
    @Test
    @Points("106.4")
    public void mediaaniToimiiOsaKaksi() {
        palautettuKuvaOikeanKokoinen();

        Kuva kuva = yhdistaKuvat("mediaani", "white.png", "white.png", "black.png", "black.png", "white.png");
        Kuva alkup = lataaKuvat(luoYhdistin("mediaani"), "white.png").get(0);
        assertTrue("Kun kuvia yhdistetään, yhdistetyn kuvan leveyden ja korkeuden tulee olla sama kuin yhdistettävillä kuvilla.", 100 == kuva.getLeveys() && 67 == kuva.getKorkeus());
    
        int leveys = kuva.getLeveys();
        int korkeus = kuva.getKorkeus();
        
        Random rnd = new Random();
        
        for (int i = 0; i < 10; i++) {
            int testX = rnd.nextInt(leveys);
            int testY = rnd.nextInt(korkeus);
            
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on mediaani, nyt punainen väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.punainen(testX, testY) == kuva.punainen(testX, testY));
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on mediaani, nyt vihreä väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.vihrea(testX, testY) == kuva.vihrea(testX, testY));
            assertTrue("Yhdistathan kuvien väriarvot oikein kun yhdistimen tyyppi on mediaani, nyt sininen väri ei ollut oikein asetettuna yhdistetyssä kuvassa.", alkup.sininen(testX, testY) == kuva.sininen(testX, testY));
        }
    }

    private Object luoYhdistin(String arvo) {
        try {
            return Reflex.reflect(klass).requirePublic().ctor().taking(String.class).invoke(arvo);
        } catch (Throwable t) {

        }

        return null;
    }

    private ArrayList<Kuva> lataaKuvat(Object yhdistin, String... tiedostot) {
        ArrayList<String> kuvatiedostot = new ArrayList<>();
        for (String tiedosto : tiedostot) {
            kuvatiedostot.add(tiedosto);
        }
        
        ArrayList<Kuva> vastaus = null;
        try {
            vastaus = Reflex.reflect(klass).method("lataaKuvat").returning(ArrayList.class).taking(ArrayList.class).invokeOn(yhdistin, kuvatiedostot);
        } catch (Throwable t) {
            fail("Olethan luonut luokalle Yhdistin metodin \"lataaKuvat\", joka palauttaa ArrayList<Kuva>-tyyppisen listan, ja joka saa parametrina ArrayList<String>-listan? Virhe oli: " + t.getMessage());
        }

        if (vastaus == null) {
            fail("Olethan luonut luokalle Yhdistin metodin \"lataaKuvat\", joka palauttaa ArrayList<Kuva>-tyyppisen listan, ja joka saa parametrina ArrayList<String>-listan? Nyt vastauksena ei saatu toimivaa listaa.");
        }

        return vastaus;
    }

    private Kuva yhdistaKuvat(String yhdistinTyyppi, String... kuvapolut) {
        Object yhdistin = luoYhdistin(yhdistinTyyppi);
        ArrayList<Kuva> kuvat = lataaKuvat(yhdistin, kuvapolut);

        try {
            return Reflex.reflect(klass).method("yhdistaKuvat").returning(Kuva.class).taking(ArrayList.class).withNiceError().invokeOn(yhdistin, kuvat);
        } catch (AssertionError niceError) {
            throw niceError;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
