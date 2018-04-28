
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.awt.Point;

import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;

import static org.powermock.api.easymock.PowerMock.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import robotti.Ohjain;

@PrepareForTest(Viivakimara.class)
public class ViivakimaraTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();
//

    @Test
    @Points("47.1")
    public void liikuMontaYksi() throws Throwable {
        testMode();

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void liikuMonta(int montako). Virhe: " + t.getMessage());
        }

        Ohjain.kaynnista();
        Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).invoke(1);
        Ohjain.sammuta();

        List<Point> sijainnit = getSijainnit();
        sijainnit = new ArrayList<Point>(new HashSet<Point>(sijainnit));
        Assert.assertTrue("Kun metodia liikuMonta kutsutaan parametrilla 1, robotin tulee liikkua yksi askel eteenpäin. Nyt robotti kävi " + sijainnit.size() + " sijainnissa (ml. alku).", sijainnit.size() == 2);

        sijainnit.removeAll(new ArrayList<Point>() {
            {
                add(new Point(1, 12));
                add(new Point(2, 12));
            }
        });

        Assert.assertTrue("Kun metodia liikuMonta kutsutaan parametrilla 1, robotin tulee liikkua yksi askel eteenpäin sen hetkisestä tilanteesta.", sijainnit.isEmpty());

    }

    @Test
    @Points("47.1")
    public void liikuMontaKolme() throws Throwable {
        testMode();

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void liikuMonta(int montako). Virhe: " + t.getMessage());
        }

        Ohjain.kaynnista();
        Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).invoke(3);
        Ohjain.sammuta();

        List<Point> sijainnit = getSijainnit();
        sijainnit = new ArrayList<Point>(new HashSet<Point>(sijainnit));
        Assert.assertTrue("Kun metodia liikuMonta kutsutaan parametrilla 3, robotin tulee liikkua sen hetkisestä tilanteesta kolme askelta eteenpäin.", sijainnit.size() == 4);

        sijainnit.removeAll(new ArrayList<Point>() {
            {
                add(new Point(1, 12));
                add(new Point(2, 12));
                add(new Point(3, 12));
                add(new Point(4, 12));
            }
        });

        Assert.assertTrue("Kun metodia liikuMonta kutsutaan parametrilla 3, robotin tulee liikkua sen hetkisestä tilanteesta kolme askelta eteenpäin.", sijainnit.isEmpty());

    }

    @Test
    @Points("47.2")
    public void piirraPieniNelio() throws Throwable {
        testMode();

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void piirraNelio(int sivunPituus). Virhe: " + t.getMessage());
        }

        Ohjain.kaynnista();
        Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).invoke(2);
        Ohjain.sammuta();

        List<Point> sijainnit = getSijainnit();
        sijainnit = new ArrayList<Point>(new HashSet<Point>(sijainnit));
        Assert.assertTrue("Kun metodia piirraNelio kutsutaan parametrilla 2, robotin tulee piirtää neliö, jonka sivujen pituus on yhteensä 8.", sijainnit.size() == 8);

        sijainnit.removeAll(new ArrayList<Point>() {
            {
                add(new Point(1, 12));
                add(new Point(2, 12));
                add(new Point(3, 12));
                add(new Point(3, 11));
                add(new Point(3, 10));
                add(new Point(2, 10));
                add(new Point(1, 10));
                add(new Point(1, 11));
                add(new Point(1, 12));
            }
        });

        Assert.assertTrue("Kun metodia piirraNelio kutsutaan parametrilla 2, tulee robotin piirtää neliö, jonka kunkin sivun pituus on 2. Piirrä neliö siten, että robotti kääntyy aina päädyssä oikealle.", sijainnit.isEmpty());
    }

    @Test
    @Points("47.2")
    public void piirraIsoNelio() throws Throwable {
        testMode();

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void piirraNelio(int sivunPituus). Virhe: " + t.getMessage());
        }

        Ohjain.kaynnista();
        Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).invoke(8);
        Ohjain.sammuta();

        List<Point> sijainnit = getSijainnit();
        sijainnit = new ArrayList<Point>(new HashSet<Point>(sijainnit));
        Assert.assertTrue("Kutsun piirraNelio(8) tulee piirtää neliö, jonka jokaisen sivun pituus on 8. Nyt sivujen pituudet olivat " + (sijainnit.size() / 4) + ".", sijainnit.size() == 32);

        for (int i = 0; i <= 8; i++) {
            sijainnit.remove(new Point(1, 12 - i));
            sijainnit.remove(new Point(9, 12 - i));
            sijainnit.remove(new Point(2 + i, 12));
            sijainnit.remove(new Point(2 + i, 4));
        }

        Assert.assertTrue("Kutsun piirraNelio(8) tulee piirtää neliö, jonka jokaisen sivun pituus on 8. Piirrä neliö siten, että robotti kääntyy aina päädyssä oikealle." + sijainnit, sijainnit.isEmpty());
    }

    @Test
    @Points("47.2")
    public void piirraNelioKayttaaMetodiaLiikuMonta() throws Throwable {
        testMode();

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void liikuMonta(int montako). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void piirraNelio(int sivunPituus). Virhe: " + t.getMessage());
        }

        mockStaticPartial(Viivakimara.class, "liikuMonta");
        reset(Viivakimara.class);

        Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).invoke(8);
        Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).invoke(8);
        Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).invoke(8);
        Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).invoke(8);

        replay(Viivakimara.class);

        try {
            Ohjain.kaynnista();
            Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).invoke(8);
            Ohjain.sammuta();
            verifyAll();
        } catch (AssertionError e) {
            fail("Käytä metodissa piirraNelio vain metodeja liikuMonta ja Ohjain.oikea(). Nyt ei mennyt oikein! Lisätietoja: " + e);
        }
    }

    @Test
    @Points("47.3")
    public void sisakkaisetNeliotPieni() throws Throwable {
        testMode();

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void liikuMonta(int montako). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void piirraNelio(int sivunPituus). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("sisakkaisetNeliot").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void sisakkaisetNeliot(int montako). Virhe: " + t.getMessage());
        }

        mockStaticPartial(Viivakimara.class, "piirraNelio");
        reset(Viivakimara.class);

        Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).invoke(1);

        replay(Viivakimara.class);

        try {
            Ohjain.kaynnista();
            Reflex.reflect(Viivakimara.class).staticMethod("sisakkaisetNeliot").returningVoid().taking(int.class).invoke(1);
            Ohjain.sammuta();

            verifyAll();
        } catch (AssertionError e) {
            fail("Käytä metodissa sisakkaisetNeliot vain metodia piirraNelio; kun kutsutaan sisakkaisetNeliot(1), tulee kutsua kerran piirraNelio(1). Nyt ei mennyt oikein! Lisätietoja: " + e);
        }
    }

    @Test
    @Points("47.3")
    public void sisakkaisetNeliotIso() throws Throwable {
        testMode();

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void liikuMonta(int montako). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void piirraNelio(int sivunPituus). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("sisakkaisetNeliot").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void sisakkaisetNeliot(int montako). Virhe: " + t.getMessage());
        }

        mockStaticPartial(Viivakimara.class, "piirraNelio");
        reset(Viivakimara.class);

        Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).invoke(3);
        Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).invoke(1);
        Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).invoke(2);

        replay(Viivakimara.class);

        try {
            Ohjain.kaynnista();
            Reflex.reflect(Viivakimara.class).staticMethod("sisakkaisetNeliot").returningVoid().taking(int.class).invoke(3);
            Ohjain.sammuta();

            verifyAll();
        } catch (AssertionError e) {
            fail("Käytä metodissa sisakkaisetNeliot vain metodia piirraNelio; kun kutsutaan sisakkaisetNeliot(3), tulee kutsua piirraNelio(3), piirraNelio(2) ja piirraNelio(1). Nyt ei mennyt oikein! Lisätietoja: " + e);
        }
    }

    @Test
    @Points("47.4")
    public void rajatutSisakkaisetNeliotIso() throws Throwable {
        testMode();

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void liikuMonta(int montako). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void piirraNelio(int sivunPituus). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("sisakkaisetNeliot").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void sisakkaisetNeliot(int montako). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("rajatutSisakkaisetNeliot").returningVoid().taking(int.class, int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void rajatutSisakkaisetNeliot(int suurin, int pienin). Virhe: " + t.getMessage());
        }

        mockStaticPartial(Viivakimara.class, "piirraNelio");
        reset(Viivakimara.class);

        Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).invoke(4);
        Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).invoke(5);

        replay(Viivakimara.class);

        try {
            Ohjain.kaynnista();
            Reflex.reflect(Viivakimara.class).staticMethod("rajatutSisakkaisetNeliot").returningVoid().taking(int.class, int.class).invoke(5, 4);
            Ohjain.sammuta();

            verifyAll();
        } catch (AssertionError e) {
            fail("Kun kutsutaan rajatutSisakkaisetNeliot(5, 4), tulee kutsua piirraNelio(5) ja piirraNelio(4). Nyt ei mennyt oikein! Lisätietoja: " + e);
        }
    }

    @Test
    @Points("47.4")
    public void sisakkaisetNeliotKayttaaRajattuja() throws Throwable {
        testMode();

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void liikuMonta(int montako). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void piirraNelio(int sivunPituus). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("sisakkaisetNeliot").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void sisakkaisetNeliot(int montako). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("rajatutSisakkaisetNeliot").returningVoid().taking(int.class, int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void rajatutSisakkaisetNeliot(int suurin, int pienin). Virhe: " + t.getMessage());
        }

        mockStaticPartial(Viivakimara.class, "rajatutSisakkaisetNeliot", int.class, int.class);
        reset(Viivakimara.class);

        Reflex.reflect(Viivakimara.class).staticMethod("rajatutSisakkaisetNeliot").returningVoid().taking(int.class, int.class).invoke(3, 1);

        replay(Viivakimara.class);

        try {
            Ohjain.kaynnista();
            Reflex.reflect(Viivakimara.class).staticMethod("sisakkaisetNeliot").returningVoid().taking(int.class).invoke(3);
            Ohjain.sammuta();
            verifyAll();
        } catch (AssertionError e) {
            fail("Muokkaa metodisi sisakkaisetNeliot(int montako) kutsumaan uutta rajatutSisakkaisetNeliot(int suurin, int pienin)-metodia. Esimerkiksi kutsu \"sisakkaisetNeliot(3)\" kutsuisi metodia rajatutSisakkaisetNeliot parametreilla 3, 1. Nyt ei mennyt oikein! Lisätietoja: " + e);
        }
    }

    @Test
    @Points("47.5")
    public void viivakimaraTest() throws Throwable {
        testMode();

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("liikuMonta").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void liikuMonta(int montako). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("piirraNelio").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void piirraNelio(int sivunPituus). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("sisakkaisetNeliot").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void sisakkaisetNeliot(int montako). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("rajatutSisakkaisetNeliot").returningVoid().taking(int.class, int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void rajatutSisakkaisetNeliot(int suurin, int pienin). Virhe: " + t.getMessage());
        }

        try {
            Reflex.reflect(Viivakimara.class).staticMethod("viivakimara").returningVoid().taking(int.class).requireExists();
        } catch (Throwable t) {
            fail("Toteuta metodi public static void viivakimara(int koko). Virhe: " + t.getMessage());
        }

        Ohjain.kaynnista();
        Reflex.reflect(Viivakimara.class).staticMethod("viivakimara").returningVoid().taking(int.class).invoke(10);
        Ohjain.sammuta();

        List<String> komennot = komennot();

        Assert.assertTrue("Kutsun viivakimara(10) tulee tehdä ainakin jonkinlainen viivakimara. Saat päättää sen muodon itse, mutta kimara ei koostu muutamasta lyhyestä viivasta!", komennot.size() > 24);
        reset();

        Ohjain.kaynnista();
        Reflex.reflect(Viivakimara.class).staticMethod("viivakimara").returningVoid().taking(int.class).invoke(5);
        Ohjain.sammuta();

        List<String> komennot2 = komennot();
        Assert.assertTrue("Kutsun viivakimara(5) tulee tehdä ainakin jonkinlainen viivakimara. Saat päättää sen muodon itse, mutta kimara ei koostu muutamasta lyhyestä viivasta!", komennot2.size() > 6);

        Assert.assertFalse("Kutsujen viivakimara(5) ja viivakimara(10) tulee luoda toisistaan erilaiset viivakimarat!", komennot.equals(komennot2));
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

        resetRobot();
    }

    private void resetRobot() {
        try {
            Method m = Ohjain.class.getDeclaredMethod("reset");
            m.setAccessible(true);
            m.invoke(null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
