import org.junit.Test;
import wsuv.autolab.Score;
import wsuv.autolab.Trace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * THIS TEST IS BROKEN:
 *  -- class @Score EXCEEDS sum of method @Score
 */
@Score(score=10)
public class WarmOrColdBrokenTestsA {

    @Test
    @Score(score=15)
    public void testConstruction() {
        WarmOrCold w = new WarmOrCold();
        assertNotNull(w);
    }

    @Test
    public void testColdGuess() {
        WarmOrCold w = new WarmOrCold();
        assertEquals("A guess of -1000 should be cold ;)",
                Temperature.WARM, w.warmOrCold(-1000));
    }
}
