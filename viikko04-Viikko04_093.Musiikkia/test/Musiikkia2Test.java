import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;
import tktl.Soitin;
import tktl.TmpSound;

public class Musiikkia2Test {

    @Rule
    public MockStdio io = new MockStdio();
    
    
    @Points("93.2")
    @Test
    public void pyydetaanKappalettaVainKerran() {
        enableTestMode();
        io.setSysIn("CCC");

        try {
            Paaohjelma.main(new String[]{""});
        } catch (Throwable t) {
            if(t.getMessage().contains("o line fou")) {
                fail("Pyydäthän soitettavan kappaleen vain kerran.");
            }
        }
        
        assertTrue("Pyydä käyttäjältä soitettavaa kappaletta! Nyt kirjoitit: " + io.getSysOut(), io.getSysOut() != null && !io.getSysOut().isEmpty() && io.getSysOut().contains("oitettava"));
    }

    @Points("93.2")
    @Test
    public void pyydaKappale() {
        enableTestMode();
        io.setSysIn("CCC");

        try {
            Paaohjelma.main(new String[]{""});
        } catch (Throwable t) {
            if(t.getMessage().contains("o line fou")) {
                fail("Pyydäthän soitettavan kappaleen vain kerran.");
            }
        }
        
        assertTrue("Pyydä käyttäjältä soitettavaa kappaletta! Nyt kirjoitit: " + io.getSysOut(), io.getSysOut() != null && !io.getSysOut().isEmpty() && io.getSysOut().contains("oitettava"));
    }

    @Points("93.2")
    @Test
    public void soitetaanC() {
        testaaNuotti("C");
    }

    @Points("93.2")
    @Test
    public void soitetaanD() {
        testaaNuotti("D");
    }

    @Points("93.2")
    @Test
    public void soitetaanE() {
        testaaNuotti("E");
    }

    @Points("93.2")
    @Test
    public void soitetaanF() {
        testaaNuotti("F");
    }

    @Points("93.2")
    @Test
    public void soitetaanG() {
        testaaNuotti("G");
    }

    @Points("93.2")
    @Test
    public void soitetaanA() {
        testaaNuotti("A");
    }

    @Points("93.2")
    @Test
    public void soitetaanH() {
        testaaNuotti("H");
    }

    @Points("93.2")
    @Test
    public void soitetaanTyhja() {
        testaaNuotti(" ");
    }

    @Points("93.2")
    @Test
    public void soitetaanKolmeNuottia() {
        enableTestMode();
        io.setSysIn("HAE");

        Paaohjelma.main(new String[]{""});
        assertTrue("Pyydä käyttäjältä soitettavaa kappaletta! Nyt kirjoitit: " + io.getSysOut(), io.getSysOut() != null && !io.getSysOut().isEmpty() && io.getSysOut().contains("oitettava"));

        List<TmpSound> sounds = aanet();
        assertNotNull("Kun käyttäjä syöttää \"HAE\", äänilistalle tulee lisätä kolme ääntä.\nNyt ääniä ei annettu soittimelle.", sounds);
        assertTrue("Kun käyttäjä syöttää \"HAE\", äänilistalle tulee lisätä kolme ääntä.\nNyt soittimelle annettiin " + sounds.size() + " ääntä.", !sounds.isEmpty() && sounds.size() == 3);

        TmpSound h = sounds.get(0);
        assertEquals("Kun käyttäjä syöttää \"HAE\", ensimmäisen soitettavan äänen pitäisi olla \"H\", jonka taajuus on 493.883. Nyt ensimmäisen soitettavan äänen taajuus oli " + h.pitch, 493.883, h.pitch, 0.01);
        assertEquals("Kun käyttäjä syöttää \"HAE\", ensimmäisen soitettavan äänen pitäisi olla \"H\", jonka kesto on 0.5. Nyt ensimmäisen soitettavan äänen kesto oli " + h.duration, 0.5, h.duration, 0.01);

        TmpSound a = sounds.get(1);
        assertEquals("Kun käyttäjä syöttää \"HAE\", toisen soitettavan äänen pitäisi olla \"A\", jonka taajuus on 440.000. Nyt toisen soitettavan äänen taajuus oli " + a.pitch, 440.000, a.pitch, 0.01);
        assertEquals("Kun käyttäjä syöttää \"HAE\", toisen soitettavan äänen pitäisi olla \"A\", jonka kesto on 0.5. Nyt toisen soitettavan äänen kesto oli " + a.duration, 0.5, a.duration, 0.01);

        TmpSound e = sounds.get(2);
        assertEquals("Kun käyttäjä syöttää \"HAE\", kolmannen soitettavan äänen pitäisi olla \"E\", jonka taajuus on 329.628. Nyt kolmannen soitettavan äänen taajuus oli " + e.pitch, 329.628, e.pitch, 0.01);
        assertEquals("Kun käyttäjä syöttää \"HAE\", kolmannen soitettavan äänen pitäisi olla \"E\", jonka kesto on 0.5. Nyt kolmannen soitettavan äänen kesto oli " + e.duration, 0.5, e.duration, 0.01);
    }

    @Points("93.3")
    @Test
    public void onEasterEgg() {
        enableTestMode();
        io.setSysIn("TKO-ALY");

        Paaohjelma.main(new String[]{""});
        assertTrue("Pyydä käyttäjältä soitettavaa kappaletta! Nyt kirjoitit: " + io.getSysOut(), io.getSysOut() != null && !io.getSysOut().isEmpty() && io.getSysOut().contains("oitettava"));

        List<TmpSound> sounds = aanet();
        Set<Double> pitches = new HashSet<Double>();
        double kesto = 0;
        for (TmpSound sound : sounds) {
            kesto += sound.duration;
            pitches.add(sound.pitch);
        }

        assertNotNull("Kun käyttäjä syöttää \"TKO-ALY\", äänilistalle tulee jotain ääniä. Saat itse päättää mitä.\nNyt ääniä ei annettu soittimelle.", sounds);
        assertTrue("Kun käyttäjä syöttää \"TKO-ALY\", äänilistalle tulee jotain ääniä. Saat itse päättää mitä.\nNyt ääniä ei annettu soittimelle.", !sounds.isEmpty());
        assertTrue("Kun käyttäjä syöttää \"TKO-ALY\", äänilistalle tulee jotain ääniä. Saat itse päättää mitä.\nNyt ääniä ei annettu soittimelle tai soitettava musiikki oli hyvin lyhyt.", kesto > 1 );
        assertFalse("Tee yllätyskappaleestasi pikkasen pidempi :).", kesto < 3);
        assertFalse("Käytä yllätyskappaleessasi useampaa taajuutta.", pitches.size() < 3);

    }

    private void testaaNuotti(String nuotti) {
        enableTestMode();
        io.setSysIn(nuotti);

        double kesto = 0.5;
        double taajuus = 0.0;
        if (nuotti.equals("C")) {
            taajuus = 261.626;
        } else if (nuotti.equals("D")) {
            taajuus = 293.665;
        } else if (nuotti.equals("E")) {
            taajuus = 329.628;
        } else if (nuotti.equals("F")) {
            taajuus = 349.228;
        } else if (nuotti.equals("G")) {
            taajuus = 391.995;
        } else if (nuotti.equals("A")) {
            taajuus = 440.000;
        } else if (nuotti.equals("H")) {
            taajuus = 493.883;
        } else if (nuotti.equals(" ")) {
            taajuus = 0;
            kesto = 0.1;
        }

        
        try {
            Paaohjelma.main(new String[]{""});
        } catch (Throwable t) {
            if(t.getMessage().contains("o line fou")) {
                fail("Pyydäthän soitettavan kappaleen vain kerran.");
            }
        }
        
        assertTrue("Pyydä käyttäjältä soitettavaa kappaletta! Nyt kirjoitit: " + io.getSysOut(), io.getSysOut() != null && !io.getSysOut().isEmpty() && io.getSysOut().contains("oitettava"));

        List<TmpSound> sounds = aanet();
        assertNotNull("Kun käyttäjä syöttää nuotin " + nuotti + ", äänilistalle tulee lisätä yksi Aani. Äänen taajuuden tulee olla " + taajuus + " ja keston " + kesto + ".\nNyt ääniä ei annettu soittimelle.", sounds);
        assertTrue("Kun käyttäjä syöttää nuotin " + nuotti + ", äänilistalle tulee lisätä yksi Aani. Äänen taajuuden tulee olla " + taajuus + " ja keston " + kesto + ".\nNyt soittimelle annettiin " + sounds.size() + " ääntä.", !sounds.isEmpty() && sounds.size() == 1);

        assertEquals("Kun käyttäjä syöttää nuotin " + nuotti + ", äänilistalle tulee lisätä yksi Aani. Äänen taajuuden tulee olla " + taajuus + " ja keston " + kesto + ",\nNyt kesto oli " + sounds.get(0).duration, kesto, sounds.get(0).duration, 0.01);
        assertEquals("Kun käyttäjä syöttää nuotin " + nuotti + ", äänilistalle tulee lisätä yksi Aani. Äänen taajuuden tulee olla " + taajuus + " ja keston " + kesto + ",\nNyt taajuus oli " + sounds.get(0).pitch, taajuus, sounds.get(0).pitch, 0.01);
    }

    private List<TmpSound> aanet() {
        try {
            Method m = Soitin.class.getDeclaredMethod("getSounds");
            m.setAccessible(true);
            return (List<TmpSound>) m.invoke(null);
        } catch (Throwable e) {
        }

        return null;
    }

    private void enableTestMode() {
        try {
            Reflex.reflect(Soitin.class).staticMethod("setTestMode").returningVoid().taking(boolean.class).invoke(Boolean.TRUE);
        } catch (Throwable e) {
        }
    }
}
