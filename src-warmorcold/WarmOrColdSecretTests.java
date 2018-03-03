import org.junit.Test;
import wsuv.autolab.Hint;
import wsuv.autolab.Score;
import wsuv.autolab.Secret;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Secret
@Hint(hint = "Multipass")
public class WarmOrColdSecretTests {

    @Test
    @Secret
    public void testConstruction() {
        WarmOrCold w = new WarmOrCold();
        assertNotNull(w);
    }

    @Test(timeout = 1000)
    @Secret
    @Score(score = 5.0)
    public void testColdGuessExact() {
        WarmOrCold w = new WarmOrCold();
        assertEquals("A guess of 99 should be warm! ;)",
                Temperature.WARM, w.warmOrCold(99));
        assertEquals("A guess of 1000 should be cold! ;)",
                Temperature.COLD, w.warmOrCold(1000));
        assertEquals("A guess of 100 should be exact! ;)",
                Temperature.EXACTLY_RIGHT, w.warmOrCold(100));
    }
}
