import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class Musiikkia1Test {

    Reflex.ClassRef<Object> klass;
    String klassName = "Aani";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("93.1")
    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", klass.isPublic());
    }

    @Points("93.1")
    @Test
    public void testaaKonstruktori() throws Throwable {
        newOlio(1.0, 5.0);
    }

    private Object newOlio(double taajuus, double kesto) throws Throwable {
        Reflex.MethodRef2<Object, Object, Double, Double> ctor = klass.constructor().taking(double.class, double.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(double taajuus, double kesto)", ctor.isPublic());
        return ctor.invoke(taajuus, kesto);
    }

    @Points("93.1")
    @Test
    public void onkoMetodiaGetTaajuus() throws Throwable {
        String metodi = "getTaajuus";
        double taajuus = hasMethod0(newOlio(1.5, 2.0), metodi);
        assertEquals("Kun kutsutaan Aani aani = new Aani(1.5, 2.0);\ndouble t = aani.getTaajuus();\nMuuttujan t arvon pitäisi olla 1.5.", 1.5, taajuus, 0.001);
    }

    @Points("93.1")
    @Test
    public void onkoMetodiaGetKesto() throws Throwable {
        String metodi = "getKesto";
        double kesto = hasMethod0(newOlio(1.5, 2.0), metodi);

        assertEquals("Kun kutsutaan Aani aani = new Aani(1.5, 2.0);\ndouble k = aani.getKesto();\nMuuttujan k arvon pitäisi olla 2.0.", 2.0, kesto, 0.001);
    }

    @Points("93.1")
    @Test
    public void onkoSiistiTulostus() throws Throwable {
        Object olio = newOlio(12.5, 6.2);

        String tulostus = olio.toString();

        assertTrue("Kun kutsutaan Aani aani = new Aani(12.5, 6.2);\nSystem.out.println(aani);\nTulostuksen pitäisi sisältää merkkijono 12.5. Nyt tulostus oli:\n" + tulostus, tulostus != null && tulostus.contains("12."));
        assertTrue("Kun kutsutaan Aani aani = new Aani(12.5, 6.2);\nSystem.out.println(aani);\nTulostuksen pitäisi sisältää merkkijono 6.2. Nyt tulostus oli:\n" + tulostus, tulostus != null && tulostus.contains("6."));
    }

    @Points("93.1")
    @Test
    public void eiTurhiaMuuttujia() throws Throwable {
        saniteettitarkastus();
    }

    @Points("93.1")
    @Test
    public void molemmatMetoditToimivat() throws Throwable {
        Object aani = newOlio(7.5, 3.0);

        double taajuus = hasMethod0(aani, "getTaajuus");
        assertEquals("Kun kutsutaan Aani aani = new Aani(7.5, 3.0);\ndouble t = aani.getTaajuus();\nMuuttujan t arvon pitäisi olla 7.5.", 7.5, taajuus, 0.001);

        String metodi = "getKesto";
        double kesto = hasMethod0(aani, metodi);

        assertEquals("Kun kutsutaan Aani aani = new Aani(7.5, 3.0);\ndouble k = aani.getKesto();\nMuuttujan k arvon pitäisi olla 3.0.", 3.0, kesto, 0.001);
    }

    private void saniteettitarkastus() throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta " + kentta(field.toString()), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + klassName + "-luokalle kuin lukujen määrän ja summan muistavat oliomuuttujat (keskiarvon voit laskea niiden avulla), poista ylimääräiset", var < 3);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }

    private double hasMethod0(Object olio, String name) throws Throwable {
        String paluu = "double";
        String muuttuja = ("" + klassName.charAt(0)).toLowerCase();

        assertTrue("tee luokalle " + klassName + " metodi public " + paluu + " " + name + "()",
                klass.method(olio, name).returning(double.class).takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi "
                + klassName + " " + muuttuja + " = new " + klassName + "(double taajuus, double kesto); " + muuttuja + "." + name + "();";

        return klass.method(olio, name).returning(double.class).takingNoParams().withNiceError(v).invoke();
    }
}
