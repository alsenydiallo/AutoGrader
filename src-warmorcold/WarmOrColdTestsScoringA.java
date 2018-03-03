import org.junit.Test;
import wsuv.autolab.Score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WarmOrColdTestsScoringA {

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
