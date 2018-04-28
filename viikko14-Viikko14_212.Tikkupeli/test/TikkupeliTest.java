
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("212.1 212.2 212.3 212.4 212.5 212.6 212.7 212.8")
public class TikkupeliTest {

    @Test
    public void eiTesteja() {
        assertTrue(1 + 1 == 2);
    }
}
