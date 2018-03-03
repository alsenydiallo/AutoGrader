import org.junit.Test;
import wsuv.autolab.Category;
import wsuv.autolab.Score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class WarmOrColdCategoryTests {

    @Test
    @Score(score = 20)
    @Category(name = "Extra Credit")
    public void testConstruction() {
        WarmOrCold w = new WarmOrCold();
        assertNotNull(w);
    }

    @Test
//    @Score(score = 15)
    @Category(name = "Extra Credit") // bug to be fixed
    public void testColdGuessExact() {
        WarmOrCold w = new WarmOrCold();
        assertEquals("A guess of 99 should be warm! ;)",
                Temperature.WARM, w.warmOrCold(99));
    }

    @Test
//    @Score(score = 50)
    @Category(name = "Correctness")
    public void testColdGuess1() {
        WarmOrCold w = new WarmOrCold();
        assertEquals("A guess of -1000 should be cold ;)",
                Temperature.WARM, w.warmOrCold(-1000));
    }

    @Test
    @Score(score = 70)
    @Category(name = "Correctness")
    public void testColdGuess2() {
        WarmOrCold w = new WarmOrCold();
        assertEquals("A guess of -1000 should be cold ;)",
                Temperature.COLD, w.warmOrCold(-1000));
    }
}
