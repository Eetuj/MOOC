
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.awt.Point;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.*;
import robotti.Ohjain;

@Points("29.1")
public class A_KayttoliittymaTest {

    @Rule
    public MockStdio io = new MockStdio();
    
    @Test
    public void liikutaanYksiAskel() {
        testMode();
        String input = "liiku\nsammuta\n";
        io.setSysIn(input);
        try {
            Paaohjelma.main(null);
        } catch (NoSuchElementException e) {
            Assert.fail("Varmista että poistut toistolauseesta kun komentoa \"sammuta\" kutsutaan. Virhe: " + e.getMessage());
        }
        
        input = input.replaceAll("\n", " ");

        List<String> komennot = komennot();
        if (komennot == null || komennot.isEmpty()) {
            Assert.fail("Kun syötetään komennot " + input + ", robotin tulee liikkua yksi askel eteenpäin.");
        }

        List<Point> sijainnit = getSijainnit();
        sijainnit = new ArrayList<Point>(new HashSet<Point>(sijainnit));
        Assert.assertTrue("Kun syötetään komennot " + input + ", robotin tulee olla käynyt vain kahdessa sijainnissa (alku ja askel).", sijainnit.size() == 2);

        sijainnit.removeAll(new ArrayList<Point>() {
            {
                add(new Point(1, 1));
                add(new Point(2, 1));
            }
        });

        Assert.assertTrue("Kun syötetään komennot \"liiku\" ja \"sammuta\", robotin ei tule liikkua muualle kuin yhden askeleen sen aluksi osoittamaan suuntaan, eli oikealle.", sijainnit.isEmpty());
    }

    @Test
    public void liikutaanVasenJaYksiAskel() {
        testMode();
        String input = "vasen\nliiku\nsammuta\n";
        io.setSysIn(input);
        try {
            Paaohjelma.main(null);
        } catch (NoSuchElementException e) {
            Assert.fail("Varmista että poistut toistolauseesta kun komentoa \"sammuta\" kutsutaan. Virhe: " + e.getMessage());
        }
        input = input.replaceAll("\n", " ");

        List<Point> sijainnit = getSijainnit();
        Assert.assertTrue("Kun syötetään komennot " + input + ", robotin tulee olla käynyt vain kahdessa sijainnissa (alku ja askel).", sijainnit.size() == 2);

        sijainnit.removeAll(new ArrayList<Point>() {
            {
                add(new Point(1, 1));
                add(new Point(1, 2));
            }
        });

        List<String> komennot = komennot();
        if (komennot == null || komennot.isEmpty()) {
            Assert.fail("Kun syötetään komennot " + input + ", robotin tulee kääntyä vasemmalle ja liikkua yksi askel eteenpäin.");
        }

        Assert.assertTrue("Kun syötetään komennot " + input + ", robotin tulee kääntyä vasemmalle ja liikkua yksi askel eteenpäin.", sijainnit.isEmpty());
    }

    @Test
    public void liikutaanKaksiAskeltaJaVasenJaYksiAskel() {
        testMode();
        String input = "liiku\nliiku\nvasen\nliiku\nsammuta\n";
        io.setSysIn(input);
        try {
            Paaohjelma.main(null);
        } catch (NoSuchElementException e) {
            Assert.fail("Varmista että poistut toistolauseesta kun komentoa \"sammuta\" kutsutaan. Virhe: " + e.getMessage());
        }
        input = input.replaceAll("\n", " ");

        List<Point> sijainnit = getSijainnit();
        Assert.assertTrue("Kun syötetään komennot " + input + ", robotin tulee käydä neljässä sijainnissa (alku ja askeleet).", sijainnit.size() == 4);

        sijainnit.removeAll(new ArrayList<Point>() {
            {
                add(new Point(1, 1));
                add(new Point(2, 1));
                add(new Point(3, 1));
                add(new Point(3, 2));
            }
        });

        List<String> komennot = komennot();
        if (komennot == null || komennot.isEmpty()) {
            Assert.fail("Kun syötetään komennot " + input + ", robotin tulee liikkua ensin kaksi askelta, sitten kääntyä vasemmalle, ja liikkua vielä yksi askel.");
        }

        Assert.assertTrue("Kun syötetään komennot " + input + ", robotin tulee liikkua ensin kaksi askelta, sitten kääntyä vasemmalle, ja liikkua vielä yksi askel.", sijainnit.isEmpty());
    }

    @Test
    public void liikutaanKolmeAskeltaVasenJaAskelJaVasenJaLiiku() {
        testMode();
        String input = "liiku\nliiku\nliiku\nvasen\nliiku\nvasen\nliiku\nsammuta\n";
        io.setSysIn(input);
        try {
            Paaohjelma.main(null);
        } catch (NoSuchElementException e) {
            Assert.fail("Varmista että poistut toistolauseesta kun komentoa \"sammuta\" kutsutaan. Virhe: " + e.getMessage());
        }
        input = input.replaceAll("\n", " ");

        List<Point> sijainnit = getSijainnit();
        Assert.assertTrue("Kun syötetään komennot " + input + ", robotin tulee käydä kuudessa sijainnissa (alku ja askeleet).", sijainnit.size() == 6);
        sijainnit.removeAll(new ArrayList<Point>() {
            {
                add(new Point(1, 1));
                add(new Point(2, 1));
                add(new Point(3, 1));
                add(new Point(4, 1));
                add(new Point(4, 2));
                add(new Point(3, 2));
            }
        });

        List<String> komennot = komennot();
        if (komennot == null || komennot.isEmpty()) {
            Assert.fail("Kun syötetään komennot " + input + ", robotin tulee liikkua ensin kolme askelta, sitten kääntyä vasemmalle, liikkua yksi askel, kääntyä vasemmalle, ja liikkua vielä yksi askel.");
        }

        Assert.assertTrue("Kun syötetään komennot " + input + ", robotin tulee liikkua ensin kolme askelta, sitten kääntyä vasemmalle, liikkua yksi askel, kääntyä vasemmalle, ja liikkua vielä yksi askel.", sijainnit.isEmpty());
    }

    @Test
    public void liikutaanKolmeAskeltaVasenJaAskelJaOikeaJaLiiku() {
        testMode();
        String input = "liiku\nliiku\nliiku\nvasen\nliiku\noikea\nliiku\nsammuta\n";
        io.setSysIn(input);
        try {
            Paaohjelma.main(null);
        } catch (NoSuchElementException e) {
            Assert.fail("Varmista että poistut toistolauseesta kun komentoa \"sammuta\" kutsutaan. Virhe: " + e.getMessage());
        }
        input = input.replaceAll("\n", " ");

        List<Point> sijainnit = getSijainnit();
        Assert.assertTrue("Kun syötetään komennot " + input + ", robotin tulee käydä kuudessa sijainnissa (alku ja askeleet).", sijainnit.size() == 6);

        sijainnit.removeAll(new ArrayList<Point>() {
            {
                add(new Point(1, 1));
                add(new Point(2, 1));
                add(new Point(3, 1));
                add(new Point(4, 1));
                add(new Point(4, 2));
                add(new Point(5, 2));
            }
        });

        List<String> komennot = komennot();
        if (komennot == null || komennot.isEmpty()) {
            Assert.fail("Kun syötetään komennot " + input + ", robotin tulee liikkua ensin kolme askelta, sitten kääntyä vasemmalle, liikkua yksi askel, kääntyä oikealle, ja liikkua vielä yksi askel.");
        }

        Assert.assertTrue("Kun syötetään komennot " + input + ", robotin tulee liikkua ensin kolme askelta, sitten kääntyä vasemmalle, liikkua yksi askel, kääntyä oikealle, ja liikkua vielä yksi askel.", sijainnit.isEmpty());
    }

    @Test
    public void liikutaanMontaAskelta() {
        testMode();
        String input = "liikuMonta\n3\nsammuta\n";
        io.setSysIn(input);
        try {
            Paaohjelma.main(null);
        } catch (NoSuchElementException e) {
            Assert.fail("Varmista että poistut toistolauseesta kun komentoa \"sammuta\" kutsutaan. Virhe: " + e.getMessage());
        }
        input = input.replaceAll("\n", " ");

        List<Point> sijainnit = getSijainnit();
        Assert.assertTrue("Kun syötetään komennot " + input + ", robotin tulee käydä neljässä sijainnissa (alku ja askeleet).", sijainnit.size() == 4);

        sijainnit.removeAll(new ArrayList<Point>() {
            {
                add(new Point(1, 1));
                add(new Point(2, 1));
                add(new Point(3, 1));
                add(new Point(4, 1));
            }
        });

        List<String> komennot = komennot();
        if (komennot == null || komennot.isEmpty()) {
            Assert.fail("Kun syötetään komennot " + input + ", robotin tulee liikkua kolme askelta sen alussa osoittamaan suuntaan.");
        }

        Assert.assertTrue("Kun syötetään komennot " + input + ", robotin tulee liikkua kolme askelta sen alussa osoittamaan suuntaan.", sijainnit.isEmpty());
    }

    private List<String> komennot() {
        try {
            Method m = Ohjain.class.getDeclaredMethod("getKomennot");
            m.setAccessible(true);
            return (List<String>) m.invoke(null);
        } catch (Throwable e) {
        }

        return null;
    }

    private List<Point> getSijainnit() {
        try {
            Method m = Ohjain.class.getDeclaredMethod("getSijainnit");
            m.setAccessible(true);
            return (List<Point>) m.invoke(null);
        } catch (Throwable e) {
        }
        return null;
    }

    private void testMode() {
        try {
            Method m = Ohjain.class.getDeclaredMethod("testMode");
            m.setAccessible(true);
            m.invoke(null);
        } catch (Throwable e) {
            throw new Error("Jotain meni pieleen!", e);
        }

        setAlaArvoSijaintia();
    }

    private void setAlaArvoSijaintia() {
        try {
            Method m = Ohjain.class.getDeclaredMethod("alaArvoSijaintia", boolean.class);
            m.setAccessible(true);
            m.invoke(null, true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
