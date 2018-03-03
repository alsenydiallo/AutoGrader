import org.junit.Test;
import wsuv.autolab.Score;
import wsuv.autolab.Trace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * THIS TEST IS BROKEN:
 *  -- class @Score EQUALS sum of method @Score, NO POINTS REMAIN for other methods
 */
@Score(score=10)
public class WarmOrColdBrokenTestsB {

    @Test
    @Score(score=5)
    @Trace
    public void testConstruction() {
        WarmOrCold w = new WarmOrCold();
        assertNotNull(w);
    }

    @Test
    @Score(score=5)
    @Trace
    public void testConstruction2() {
        WarmOrCold w = new WarmOrCold();
        assertNotNull(w);
    }

    @Test
    @Trace
    public void testColdGuess() {
        WarmOrCold w = new WarmOrCold();
        assertEquals("A guess of -1000 should be cold ;)",
                Temperature.COLD, w.warmOrCold(-1000));
    }
}
