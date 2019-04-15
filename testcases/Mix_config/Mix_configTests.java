import org.junit.Test;
import wsuv.autolab.Category;
import wsuv.autolab.Score;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Case 7: Ones with category annotations have no score annotations and ones with score annotations
 *          has no category annotations
 * This test should pass with full score.
 *
 */

public class Mix_configTests {

    @Test
    @Score(score = 50)
    public void correctness1_true(){
        Mix_config c = new Mix_config();
        assertTrue(c.returnTrue());
    }

    @Test
    @Score(score = 50)
    public void correctness1_false(){
        Mix_config c = new Mix_config();
        assertFalse(c.returnFalse());
    }

    @Test
    @Category("Extra Credit 1")
    public void extraCredit1_true(){
        Mix_config c = new Mix_config();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Extra Credit 1")
    @Score(score = 25)
    public void extraCredit1_false(){
        Mix_config c = new Mix_config();
        assertFalse(c.returnFalse());
    }

    @Test
    @Category("Extra Credit 2")
    public void extraCredit2(){
        Mix_config c = new Mix_config();
        assertTrue(c.returnTrue());
    }

    @Test
    @Category("Extra Credit 3")
    @Score(score = 25)
    public void extraCredit3(){
        Mix_config c = new Mix_config();
        assertTrue(c.returnTrue());
    }
}
