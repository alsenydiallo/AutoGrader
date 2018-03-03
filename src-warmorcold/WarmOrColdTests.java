import org.junit.Test;
import wsuv.autolab.Hint;
import wsuv.autolab.Score;
import wsuv.autolab.Trace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WarmOrColdTests {

    @Test
    public void testConstruction() {
        WarmOrCold w = new WarmOrCold();
        assertNotNull(w);
    }

    @Test
    public void testColdGuess() {
        WarmOrCold w = new WarmOrCold();
        assertEquals("A guess of -1000 should be cold ;)",
                Temperature.COLD, w.warmOrCold(-1000));
    }
}
