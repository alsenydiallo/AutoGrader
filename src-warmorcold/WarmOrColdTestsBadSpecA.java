import org.junit.Test;
import wsuv.autolab.Trace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * THIS TEST IS BAD
 *  -- Checks against INCORRECT SPEC
 */
public class WarmOrColdTestsBadSpecA {

    @Test
    public void testConstruction() {
        WarmOrCold w = new WarmOrCold();
        assertNotNull(w);
    }

    /** Checks for incorrect behavior */
    @Test
    public void testColdGuess() {
        WarmOrCold w = new WarmOrCold();

        assertEquals("A guess of -1000 should be cold ;)",
                        -400, w.warmOrCold(-1000));
        // should be
        // assertEquals("A guess of -1000 should be cold ;)",
        //        Temperature.COLD, w.warmOrCold(-1000));
    }
}
