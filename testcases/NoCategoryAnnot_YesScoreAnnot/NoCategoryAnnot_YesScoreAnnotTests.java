import org.junit.Test;
import wsuv.autolab.Score;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Case 4: Explicitly declare score annotation for each methods.
 * no category annotation declare for any methods
 * This test should pass with full score.
 * Auto grader will assign default category to methods
 *
 */

public class NoCategoryAnnot_YesScoreAnnotTests {

    @Test
    @Score(score = 50)
    public void correctness1_true(){
        NoCategoryAnnot_YesScoreAnnot c = new NoCategoryAnnot_YesScoreAnnot();
        assertTrue(c.returnTrue());
    }

    @Test
    @Score(score = 50)
    public void correctness1_false(){
        NoCategoryAnnot_YesScoreAnnot c = new NoCategoryAnnot_YesScoreAnnot();
        assertFalse(c.returnFalse());
    }

}
