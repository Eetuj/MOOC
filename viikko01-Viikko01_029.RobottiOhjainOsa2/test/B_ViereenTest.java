
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.awt.Point;
import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.*;
import robotti.Ohjain;

@Points("29.2")
public class B_ViereenTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void viereenTest() {
        testMode();
        Random arpa = new Random();
        int sijaintiX = 8;
        while (sijaintiX == 8) {
            sijaintiX = arpa.nextInt(6) + 2;
        }
        int sijaintiY = 8;
        while (sijaintiY == 8) {
            sijaintiY = arpa.nextInt(6) + 2;
        }

        setSijainti(sijaintiX, sijaintiY);
        String input = "viereen\nsammuta\n";
        io.setSysIn(input);
        try {
            Paaohjelma.main(null);
        } catch (NoSuchElementException e) {
            Assert.fail("Varmista että poistut toistolauseesta kun komentoa \"sammuta\" kutsutaan. Virhe: " + e.getMessage());
        }
        input = input.replaceAll("\n", " ");

        List<String> komennot = komennot();
        if (komennot == null || komennot.size() < 4) {
            Assert.fail("Komentojen " + input + " tulee ohjata robotti laatikon vasemmalle puolelle. Laatikko saa olla satunnaisesti asetettu.");
        }
        
        List<Point> robottiSijainnit = getSijainnit();
        if(robottiSijainnit.isEmpty()) {
            Assert.fail("Komentojen " + input + " tulee ohjata robotti laatikon vasemmalle puolelle. Laatikko saa olla satunnaisesti asetettu.");
        }
        
        Point viimeinenSijainti = robottiSijainnit.get(robottiSijainnit.size() - 1);
        
        Assert.assertTrue("Komentojen " + input + " tulee ohjata robotti laatikon vasemmalle puolelle. Laatikko saa olla satunnaisesti asetettu." + " laatikko x: " + Ohjain.laatikkoX() + " robotti x: " + Ohjain.robottiX(), viimeinenSijainti.x + 1 == Ohjain.laatikkoX() && Ohjain.laatikkoY() == viimeinenSijainti.y);
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

    private void setSijainti(int x, int y) {
        try {
            Method m = Ohjain.class.getDeclaredMethod("setLaatikkoX", int.class);
            m.setAccessible(true);
            m.invoke(null, x);

            m = Ohjain.class.getDeclaredMethod("setLaatikkoY", int.class);
            m.setAccessible(true);
            m.invoke(null, y);
        } catch (Throwable e) {
            e.printStackTrace();
        }
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
