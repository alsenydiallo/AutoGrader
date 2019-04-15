import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NoCategoryAnnot_NoScoreAnnotTests {

    /**
     * Case 5: Missing both declaration for category annotation and score annotation
     * This test should pass with full score.
     * Auto grader will assign default category fot all methods missing category annotation
     * Auto grader will distribute point to all method missing score annotions with respect to total score for
     *  that category in the config file.
     */

    @Test
    public void correctness1_true(){
        NoCategoryAnnot_NoScoreAnnot c = new NoCategoryAnnot_NoScoreAnnot();
        assertTrue(c.returnTrue());
    }

    @Test
    public void correctness1_false(){
        NoCategoryAnnot_NoScoreAnnot c = new NoCategoryAnnot_NoScoreAnnot();
        assertFalse(c.returnFalse());
    }

}
