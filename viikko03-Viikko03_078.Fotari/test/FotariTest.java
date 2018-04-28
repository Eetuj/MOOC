
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import junit.framework.Assert;
import kuva.Fotari;
import kuva.domain.Pikseli;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;
import static org.powermock.api.easymock.PowerMock.mockStaticPartial;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verifyAll;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

@PrepareForTest(Main.class)
public class FotariTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    @Test
    @Points("78.1")
    public void metodiTummennaOnOlemassa() {
        Reflex.reflect(Main.class).staticMethod("tummenna").returningVoid().takingNoParams().requireExists();

    }

    @Test
    @Points("78.1")
    public void metodiTummennaTummentaaKuvaaAskelta() throws Throwable {
        metodiTummennaOnOlemassa();
        testMode();
        avaaTestiKuva1();

        List<Pikseli> pikselit = new ArrayList<Pikseli>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(Fotari.kuvanLeveys());
            int y = random.nextInt(Fotari.kuvanKorkeus());
            pikselit.add(new Pikseli(x, y, Fotari.punainen(x, y), Fotari.vihrea(x, y), Fotari.sininen(x, y)));
        }

        Reflex.reflect(Main.class).staticMethod("tummenna").returningVoid().takingNoParams().invoke();

        for (Pikseli pikseli : pikselit) {
            int punainen = Fotari.punainen(pikseli.getX(), pikseli.getY());
            int vihrea = Fotari.vihrea(pikseli.getX(), pikseli.getY());
            int sininen = Fotari.sininen(pikseli.getX(), pikseli.getY());
            Assert.assertEquals("Varmista, että punaisen arvo vähenee tummennettaessa 30 askeleella jokaisessa pikselissä, ja että punaisen arvoksi ei koskaan aseteta pienempää kuin 0.", Math.max(0, pikseli.getRed() - 30), punainen);
            Assert.assertEquals("Varmista, että vihreän arvo vähenee tummennettaessa 30 askeleella jokaisessa pikselissä, ja että vihreän arvoksi ei koskaan aseteta pienempää kuin 0.", Math.max(0, pikseli.getGreen() - 30), vihrea);
            Assert.assertEquals("Varmista, että sinisen arvo vähenee tummennettaessa 30 askeleella jokaisessa pikselissä, ja että sinisen arvoksi ei koskaan aseteta pienempää kuin 0.", Math.max(0, pikseli.getBlue() - 30), sininen);
        }

        Reflex.reflect(Main.class).staticMethod("tummenna").returningVoid().takingNoParams().invoke();

        for (Pikseli pikseli : pikselit) {
            int punainen = Fotari.punainen(pikseli.getX(), pikseli.getY());
            int vihrea = Fotari.vihrea(pikseli.getX(), pikseli.getY());
            int sininen = Fotari.sininen(pikseli.getX(), pikseli.getY());
            Assert.assertEquals("Varmista, että punaisen arvo vähenee kaksi kertaa tummennettaessa 60 askeleella jokaisessa pikselissä, ja että punaisen arvoksi ei koskaan aseteta pienempää kuin 0.", Math.max(0, pikseli.getRed() - 60), punainen);
            Assert.assertEquals("Varmista, että vihreän arvo vähenee kaksi kertaa tummennettaessa 60 askeleella jokaisessa pikselissä, ja että vihreän arvoksi ei koskaan aseteta pienempää kuin 0.", Math.max(0, pikseli.getGreen() - 60), vihrea);
            Assert.assertEquals("Varmista, että sinisen arvo vähenee kaksi kertaa tummennettaessa 60 askeleella jokaisessa pikselissä, ja että sinisen arvoksi ei koskaan aseteta pienempää kuin 0.", Math.max(0, pikseli.getBlue() - 60), sininen);
        }
    }

    @Test
    @Points("78.1")
    public void metodiTummennaKutsuttuKunKomentoRiviltaAnnetaanTummenna() throws Throwable {
        metodiTummennaOnOlemassa();
        testMode();

        String input = "puu-small.jpg\ntummenna\nlopeta\n";
        MockInOut io = new MockInOut(input);

        mockStaticPartial(Main.class, "tummenna");
        reset(Main.class);
        Reflex.reflect(Main.class).staticMethod("tummenna").returningVoid().takingNoParams().invoke();
        replay(Main.class);

        try {
            Main.main(new String[]{});
            verifyAll();
        } catch (AssertionError e) {
            fail("Kun ohjelmalle annetaan komennot\n" + input + "\n, ohjelman tulee avata kuva puu-small.jpg, kutsua tummenna-metodia, ja poistua toistolauseesta. Nyt ei mennyt oikein! Lisätietoja: " + e);
        }
    }

    @Test
    @Points("78.2")
    public void metodiNegatiiviOlemassa() {
        Reflex.reflect(Main.class).staticMethod("negatiivi").returningVoid().takingNoParams().requireExists();

    }

    @Test
    @Points("78.2")
    public void metodiNegatiiviKaantaaKuvanVarit() throws Throwable {
        metodiNegatiiviOlemassa();
        testMode();
        avaaTestiKuva2();

        List<Pikseli> pikselit = new ArrayList<Pikseli>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(Fotari.kuvanLeveys());
            int y = random.nextInt(Fotari.kuvanKorkeus());
            pikselit.add(new Pikseli(x, y, Fotari.punainen(x, y), Fotari.vihrea(x, y), Fotari.sininen(x, y)));
        }

        Reflex.reflect(Main.class).staticMethod("negatiivi").returningVoid().takingNoParams().invoke();

        for (Pikseli pikseli : pikselit) {
            int punainen = Fotari.punainen(pikseli.getX(), pikseli.getY());
            int vihrea = Fotari.vihrea(pikseli.getX(), pikseli.getY());
            int sininen = Fotari.sininen(pikseli.getX(), pikseli.getY());
            Assert.assertEquals("Varmista, että negatiivia luotaessa punaisen arvoksi asetetaan \"255 - vanhaPunainen\" jokaisessa pikselissä.", 255 - pikseli.getRed(), punainen);
            Assert.assertEquals("Varmista, että negatiivia luotaessa vihreän arvoksi asetetaan \"255 - vanhaVihreä\" jokaisessa pikselissä.", 255 - pikseli.getGreen(), vihrea);
            Assert.assertEquals("Varmista, että negatiivia luotaessa sinisen arvoksi asetetaan \"255 - vanhaSininen\" jokaisessa pikselissä.", 255 - pikseli.getBlue(), sininen);
        }
    }

    @Test
    @Points("78.2")
    public void metodiNegatiiviKutsuttuKunKomentoRiviltaAnnetaanNegatiivi() throws Throwable {
        metodiTummennaOnOlemassa();
        testMode();

        String input = "puu-small.jpg\nnegatiivi\nlopeta\n";
        MockInOut io = new MockInOut(input);

        mockStaticPartial(Main.class, "negatiivi");
        reset(Main.class);
        Reflex.reflect(Main.class).staticMethod("negatiivi").returningVoid().takingNoParams().invoke();
        replay(Main.class);

        try {
            Main.main(new String[]{});
            verifyAll();
        } catch (AssertionError e) {
            fail("Kun ohjelmalle annetaan komennot\n" + input + "\n, ohjelman tulee avata kuva puu-small.jpg, kutsua negatiivi-metodia, ja poistua toistolauseesta. Nyt ei mennyt oikein! Lisätietoja: " + e);
        }
    }

    @Test
    @Points("78.3")
    public void metodiPeilaaOnOlemassa() {
        Reflex.reflect(Main.class).staticMethod("peilaa").returningVoid().takingNoParams().requireExists();

    }

    @Test
    @Points("78.3")
    public void metodiPeilaaKopioiOikeallaOlevanSisallonVasemmalle() throws Throwable {
        metodiPeilaaOnOlemassa();
        testMode();
        avaaTestiKuva2();

        Reflex.reflect(Main.class).staticMethod("peilaa").returningVoid().takingNoParams().invoke();

        Set<Integer> punaiset = new HashSet<>();
        Set<Integer> vihreat = new HashSet<>();
        Set<Integer> siniset = new HashSet<>();

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int x = 0;
            while (x >= Fotari.kuvanLeveys() / 2) {
                x = random.nextInt(Fotari.kuvanLeveys());
            }

            int y = random.nextInt(Fotari.kuvanKorkeus());

            int punainenVas = Fotari.punainen(x, y);
            int punainenOik = Fotari.punainen(Fotari.kuvanLeveys() - x - 1, y);
            int vihreaVas = Fotari.vihrea(x, y);
            int vihreaOik = Fotari.vihrea(Fotari.kuvanLeveys() - x - 1, y);
            int sininenVas = Fotari.sininen(x, y);
            int sininenOik = Fotari.sininen(Fotari.kuvanLeveys() - x - 1, y);

            punaiset.add(punainenVas);
            vihreat.add(vihreaVas);
            siniset.add(sininenVas);

            Assert.assertEquals("Kun komento peilaa on suoritettu, pikselin kohdassa x tulee olla sama kuin pikselin kohdassa (kuvan leveys - x - 1). Nyt punainen väri ei osunut kohdalleen.", punainenOik, punainenVas);
            Assert.assertEquals("Kun komento peilaa on suoritettu, pikselin kohdassa x tulee olla sama kuin pikselin kohdassa (kuvan leveys - x - 1). Nyt vihreä väri ei osunut kohdalleen.", vihreaOik, vihreaVas);
            Assert.assertEquals("Kun komento peilaa on suoritettu, pikselin kohdassa x tulee olla sama kuin pikselin kohdassa (kuvan leveys - x - 1). Nyt sininen väri ei osunut kohdalleen.", sininenOik, sininenVas);
        }

        Assert.assertTrue("Varmista, että peilaus toimii kuten tarkoitettu. Ethän muuta alkuperäistä kuvaa tunnistamattomaksi.", punaiset.size() > 10);
        Assert.assertTrue("Varmista, että peilaus toimii kuten tarkoitettu. Ethän muuta alkuperäistä kuvaa tunnistamattomaksi.", vihreat.size() > 10);
        Assert.assertTrue("Varmista, että peilaus toimii kuten tarkoitettu. Ethän muuta alkuperäistä kuvaa tunnistamattomaksi.", siniset.size() > 10);
    }

    @Test
    @Points("78.3")
    public void metodiPeilaaKutsuttuKunKomentoRiviltaAnnetaanPeilaa() throws Throwable {
        metodiPeilaaOnOlemassa();
        testMode();

        String input = "puu-small.jpg\npeilaa\nlopeta\n";
        MockInOut io = new MockInOut(input);

        mockStaticPartial(Main.class, "peilaa");
        reset(Main.class);
        Reflex.reflect(Main.class).staticMethod("peilaa").returningVoid().takingNoParams().invoke();
        replay(Main.class);

        try {
            Main.main(new String[]{});
            verifyAll();
        } catch (AssertionError e) {
            fail("Kun ohjelmalle annetaan komennot\n" + input + "\n, ohjelman tulee avata kuva puu-small.jpg, kutsua peilaa-metodia, ja poistua toistolauseesta. Nyt ei mennyt oikein! Lisätietoja: " + e);
        }
    }

    @Test
    @Points("78.4")
    public void metodiAndyWarholOlemassa() {
        Reflex.reflect(Main.class).staticMethod("andywarhol").returningVoid().takingNoParams().requireExists();
    }

    private void avaaTestiKuva1() {
        resetFotari();
        try {
            Reflex.reflect(Fotari.class).staticMethod("avaa").returningVoid().taking(String.class).invoke("puu-small.jpg");
        } catch (Throwable e) {
        }
    }

    private void avaaTestiKuva2() {
        resetFotari();
        try {
            Reflex.reflect(Fotari.class).staticMethod("avaa").returningVoid().taking(String.class).invoke("ilta-small.jpg");
        } catch (Throwable e) {
        }
    }

    private List<String> komennot() {
        try {
            Method m = Fotari.class.getDeclaredMethod("getKomennot");
            m.setAccessible(true);
            return (List<String>) m.invoke(null);
        } catch (Throwable e) {
        }

        return null;
    }

    private void testMode() {
        try {
            Method m = Fotari.class.getDeclaredMethod("testMode");
            m.setAccessible(true);
            m.invoke(null);
        } catch (Throwable e) {
            throw new Error("Jotain meni pieleen!", e);
        }

        resetFotari();
    }

    private void resetFotari() {
        try {
            Method m = Fotari.class.getDeclaredMethod("reset");
            m.setAccessible(true);
            m.invoke(null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private int ka(List<Integer> arvot) {
        if (arvot == null || arvot.isEmpty()) {
            return 0;
        }

        int summa = 0;
        for (Integer arvo : arvot) {
            summa += arvo;
        }

        return summa / arvot.size();
    }
}
